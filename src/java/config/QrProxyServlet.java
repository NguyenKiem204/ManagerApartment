package config;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet Proxy để tải và chỉnh sửa mã QR mà không lộ URL gốc.
 */
@WebServlet("/qr-proxy")
public class QrProxyServlet extends HttpServlet {
    // Bộ nhớ tạm để ánh xạ transactionId -> URL QR
    private static final HashMap<String, String> transactionMap = new HashMap<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String transactionId = request.getParameter("transactionId");

        if (transactionId == null || !transactionMap.containsKey(transactionId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Giao dịch không hợp lệ");
            return;
        }

        String qrUrl = transactionMap.get(transactionId); // Lấy URL QR từ Server (không gửi trực tiếp)
        
        try {
            // Kết nối và tải ảnh từ SePay
            URL url = new URL(qrUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            BufferedImage qrImage;
            try (InputStream inputStream = connection.getInputStream()) {
                qrImage = ImageIO.read(inputStream);
            }

            if (qrImage == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể tải ảnh QR");
                return;
            }

            // Tuỳ chọn: Chỉnh sửa ảnh (ví dụ: thêm logo)
            BufferedImage modifiedImage = addLogoToQR(qrImage);

            // Gửi ảnh về client
            response.setContentType("image/png");
            ImageIO.write(modifiedImage, "png", response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý ảnh QR");
        }
    }

    /**
     * Lưu transactionId -> URL QR
     */
    public static void registerTransaction(String transactionId, String qrUrl) {
        transactionMap.put(transactionId, qrUrl);
    }

    /**
     * Tuỳ chọn: Thêm logo vào giữa QR Code
     */
    private BufferedImage addLogoToQR(BufferedImage qrImage) throws IOException {
        // Tải logo từ thư mục resources hoặc URL
        URL logoUrl = new URL("https://example.com/logo.png"); // Thay bằng đường dẫn logo của bạn
        BufferedImage logo = ImageIO.read(logoUrl);

        if (logo == null) return qrImage;

        // Kích thước logo (20% so với QR code)
        int logoSize = qrImage.getWidth() / 5;
        Image scaledLogo = logo.getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);

        // Vẽ logo lên QR
        BufferedImage newImage = new BufferedImage(qrImage.getWidth(), qrImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(qrImage, 0, 0, null);

        // Vị trí logo ở giữa
        int x = (qrImage.getWidth() - logoSize) / 2;
        int y = (qrImage.getHeight() - logoSize) / 2;
        g2d.drawImage(scaledLogo, x, y, null);

        g2d.dispose();
        return newImage;
    }
}
