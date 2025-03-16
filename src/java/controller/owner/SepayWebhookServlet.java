package controller.owner;

import at.favre.lib.bytes.BinaryToTextEncoding.Hex;
import config.SepayConfig;
import dao.InvoiceDAO;
import dao.PaymentTransactionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/**
 *
 * @author nguye
 */
@WebServlet(name = "SepayWebhookServlet", urlPatterns = {"/sepaywebhook"})
public class SepayWebhookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private static final Logger LOGGER = Logger.getLogger(SepayWebhookServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Read JSON from request
            String jsonBuffer = "";
            try (BufferedReader reader = request.getReader()) {
                jsonBuffer = reader.lines().collect(Collectors.joining());
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error reading data from request", e);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Error reading data from request");
                return;
            }

            // Parse JSON
            JSONObject jsonData = new JSONObject(jsonBuffer);

            // Get data from webhook
            String gateway = jsonData.optString("gateway", "");
            String accountNumber = jsonData.optString("accountNumber", "");
            String content = jsonData.optString("content", "");
            String transferType = jsonData.optString("transferType", "");
            double transferAmount = jsonData.optDouble("transferAmount", 0.0);

            // Check bank account
            if (!"MBBank".equals(gateway) || !"686868922004".equals(accountNumber)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("Webhook ignored");
                return;
            }

            // Process only "in" transfers
            if (!"in".equals(transferType)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("Webhook ignored");
                return;
            }

            PaymentTransactionDAO transactionDAO = new PaymentTransactionDAO();
            InvoiceDAO invoiceDAO = new InvoiceDAO();

            // Extract transactionId from content
            String transactionId = extractTransactionId(content);
            if (transactionId == null || transactionId.isEmpty()) {
                LOGGER.log(Level.WARNING, "Could not extract transaction ID from webhook");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Transaction ID not found");
                return;
            }

            // Check if the transaction exists in the database
            if (!transactionDAO.doesTransactionExist(transactionId)) {
                LOGGER.log(Level.WARNING, "Transaction ID not found in database: {0}", transactionId);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("Transaction ID not found in database");
                return;
            }

            // Get invoice ID from transaction ID
            Integer invoiceId = transactionDAO.getInvoiceIdByTransactionId(transactionId);
            if (invoiceId <= 0) {
                LOGGER.log(Level.WARNING, "Invoice not found for transaction ID: " + transactionId);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("Invoice not found for transaction ID: " + transactionId);
                return;
            }

            // Update transaction and invoice status
            transactionDAO.updateTransactionStatus(transactionId, "completed");
            invoiceDAO.updateInvoiceStatus(invoiceId);

            // Trả về thông tin thành công kèm mã hóa đơn và mã giao dịch
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    "{\"status\":\"success\", \"message\":\"Webhook processed successfully\", "
                    + "\"invoiceId\":\"" + invoiceId + "\", \"transactionId\":\"" + transactionId + "\"}"
            );

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing webhook", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("System error");
        }
    }

    private String extractTransactionId(String content) {
        if (content != null && !content.isEmpty()) {
            // Loại bỏ dấu cách và ký tự đặc biệt không cần thiết
            String cleanedContent = content.replaceAll("[^a-zA-Z0-9]", " ").trim();

            // Tách các từ trong nội dung giao dịch
            String[] words = cleanedContent.split("\\s+");

            for (String word : words) {
                if (word.length() == 32 && word.matches("[a-zA-Z0-9]+")) {
                    return word; // Nếu tìm thấy chuỗi 32 ký tự hợp lệ, trả về luôn
                }
            }
        }

        // Ghi log cảnh báo nếu không tìm thấy transactionId
        LOGGER.log(Level.WARNING, "Không tìm thấy transactionId trong content: {0}", content);

        return null; // Trả về null nếu không có transactionId hợp lệ
    }

    @Override
    public String getServletInfo() {
        return "Webhook handler for Sepay payment system";
    }
}
