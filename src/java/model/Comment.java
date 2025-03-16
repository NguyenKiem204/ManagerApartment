/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author nkiem
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {

    int commentId;
    int newsId;
    int userId;
    String userType; // 'Resident' hoặc 'Staff'
    String content;
    LocalDateTime commentDate;
    String userName;
    String userAvatar;

    public Comment(int commentId, int newsId, int userId, String userType, String content, LocalDateTime commentDate) {
        this.commentId = commentId;
        this.newsId = newsId;
        this.userId = userId;
        this.userType = userType;
        this.content = content;
        this.commentDate = commentDate;
    }

    public String getLastName() {
        if (userName == null || userName.trim().isEmpty()) {
            return "";
        }

        String[] words = userName.trim().split("\\s+");
        return words[words.length - 1];
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return commentDate.format(formatter);
    }

    public String getTimeAgo() {
        LocalDateTime now = LocalDateTime.now();
        long minutes = java.time.Duration.between(commentDate, now).toMinutes();

        if (minutes < 1) {
            return "just now";
        }
        if (minutes < 60) {
            return minutes + " minute" + (minutes > 1 ? "s" : "") + " ago";
        }

        long hours = minutes / 60;
        if (hours < 24) {
            return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        }

        long days = hours / 24;
        if (days < 30) {
            return days + " day" + (days > 1 ? "s" : "") + " ago";
        }
        return getFormattedDate();
    }
}
