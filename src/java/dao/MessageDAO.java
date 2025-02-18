package dao;

import model.Message;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageDAO implements DAOInterface<Message, Integer> {

    @Override
    public int insert(Message message) {
        int row = 0;
        String sqlInsert = "INSERT INTO [Message] (SenderEmail, ReceiverEmail, MessageText, Timestamp, Status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, message.getSenderEmail());
            ps.setString(2, message.getReceiverEmail());
            ps.setString(3, message.getMessageText());
            ps.setTimestamp(4, Timestamp.valueOf(message.getTimestamp()));
            ps.setString(5, message.getStatus());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Message message) {
        int row = 0;
        String sql = "UPDATE [Message] SET SenderEmail = ?, ReceiverEmail = ?, MessageText = ?, Timestamp = ?, Status = ? WHERE MessageID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, message.getSenderEmail());
            ps.setString(2, message.getReceiverEmail());
            ps.setString(3, message.getMessageText());
            ps.setTimestamp(4, Timestamp.valueOf(message.getTimestamp()));
            ps.setString(5, message.getStatus());
            ps.setInt(6, message.getMessageId());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int delete(Message message) {
        int row = 0;
        String sql = "DELETE FROM [Message] WHERE MessageID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, message.getMessageId());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public List<Message> selectAll() {
        List<Message> list = new ArrayList<>();
        String sql = "SELECT * FROM [Message]";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("MessageID"),
                        rs.getString("SenderEmail"),
                        rs.getString("ReceiverEmail"),
                        rs.getString("MessageText"),
                        rs.getTimestamp("Timestamp").toLocalDateTime(),
                        rs.getString("Status")
                );
                list.add(message);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Message selectById(Integer id) {
        Message message = null;
        String sql = "SELECT * FROM [Message] WHERE MessageID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    message = new Message(
                            rs.getInt("MessageID"),
                            rs.getString("SenderEmail"),
                            rs.getString("ReceiverEmail"),
                            rs.getString("MessageText"),
                            rs.getTimestamp("Timestamp").toLocalDateTime(),
                            rs.getString("Status")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    public int sendMessage(String senderEmail, String receiverEmail, String messageText) {
        String sql = "INSERT INTO [Message] (SenderEmail, ReceiverEmail, MessageText, Timestamp, Status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, senderEmail);
            ps.setString(2, receiverEmail);
            ps.setString(3, messageText);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(5, "UNREAD");
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public List<Message> getMessagesBetween(String email1, String email2) {
        List<Message> list = new ArrayList<>();
        String sql = "SELECT * FROM [Message] WHERE (SenderEmail = ? AND ReceiverEmail = ?) OR (SenderEmail = ? AND ReceiverEmail = ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email1);
            ps.setString(2, email2);
            ps.setString(3, email2);
            ps.setString(4, email1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("MessageID"),
                        rs.getString("SenderEmail"),
                        rs.getString("ReceiverEmail"),
                        rs.getString("MessageText"),
                        rs.getTimestamp("Timestamp").toLocalDateTime(),
                        rs.getString("Status")
                );
                list.add(message);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int markMessagesAsRead(String senderEmail, String receiverEmail) {
        String sql = "UPDATE [Message] SET Status = 'READ' WHERE SenderEmail = ? AND ReceiverEmail = ? AND Status = 'UNREAD'";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, senderEmail);
            ps.setString(2, receiverEmail);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    // Phương thức bổ sung: Lấy danh sách tin nhắn theo người gửi
    public List<Message> getMessagesBySender(String senderEmail) {
        List<Message> list = new ArrayList<>();
        String sql = "SELECT * FROM [Message] WHERE SenderEmail = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, senderEmail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("MessageID"),
                        rs.getString("SenderEmail"),
                        rs.getString("ReceiverEmail"),
                        rs.getString("MessageText"),
                        rs.getTimestamp("Timestamp").toLocalDateTime(),
                        rs.getString("Status")
                );
                list.add(message);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // Phương thức bổ sung: Lấy danh sách tin nhắn theo người nhận
    public List<Message> getMessagesByReceiver(String receiverEmail) {
        List<Message> list = new ArrayList<>();
        String sql = "SELECT * FROM [Message] WHERE ReceiverEmail = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, receiverEmail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("MessageID"),
                        rs.getString("SenderEmail"),
                        rs.getString("ReceiverEmail"),
                        rs.getString("MessageText"),
                        rs.getTimestamp("Timestamp").toLocalDateTime(),
                        rs.getString("Status")
                );
                list.add(message);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
