package controller.owner;

import dao.PaymentTransactionDAO;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "QRProxyServlet", urlPatterns = {"/qrproxy"})
public class QRProxyServlet extends HttpServlet {

    private static final String SECRET_KEY = "mmmmmmmmmmmmmmmm"; // Key phải đủ 16/24/32 ký tự

    /**
     * Giải mã transactionId
     */
    private static String decrypt(String strToDecrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getUrlDecoder().decode(strToDecrypt)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String encryptedTransactionId = request.getParameter("data");
        String amount = request.getParameter("amount");

        // Kiểm tra dữ liệu đầu vào
        if (encryptedTransactionId == null || amount == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin mã giao dịch hoặc số tiền");
            return;
        }

        // Giải mã transactionId
        String transactionId = decrypt(encryptedTransactionId);
        if (transactionId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã giao dịch không hợp lệ");
            return;
        }

        // Kiểm tra transactionId có hợp lệ không
        PaymentTransactionDAO transactionDAO = new PaymentTransactionDAO();
        if (!transactionDAO.isValidTransaction(transactionId)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Giao dịch không hợp lệ hoặc đã hết hạn");
            return;
        }

        // Tạo URL QR Code
        String qrUrl = String.format(
            "https://qr.sepay.vn/img?bank=MBBank&acc=686868922004&template=compact&amount=%s&des=%s",
            amount, transactionId
        );

        // Lấy dữ liệu ảnh QR từ URL và trả về client
        HttpURLConnection connection = (HttpURLConnection) new URL(qrUrl).openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream()) {
            response.setContentType("image/png");
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
        }
    }
}
