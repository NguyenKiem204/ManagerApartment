/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nkiem
 */
public class Validate {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern USER_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{5,20}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");

    public static String normalizeString(String input) {
        if (input == null) {
            return null;
        }
        String normalized = input.trim();
        normalized = normalized.replaceAll("\\s+", " ");
        normalized = normalized.replaceAll("[^a-zA-Z0-9\\s\\p{L}]", "");

        return normalized;
    }
public static String trim(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }
        String normalized = input.trim();
        normalized = normalized.replaceAll("\\s+", " ");
        return normalized;
    }
    public static String validateUserID(String userID) {
        if (userID == null || userID.isBlank()) {
            return "User ID cannot be empty.";
        }
        Matcher matcher = USER_ID_PATTERN.matcher(userID);
        if (!matcher.matches()) {
            return "User ID must be 5-20 characters long and can contain letters, numbers, and underscores.";
        }
        return null;
    }

    public static String validateImageURL(String imageURL) {
        if (imageURL == null || imageURL.isBlank()) {
            return "Image URL cannot be empty.";
        }
        if (!imageURL.matches("^(http|https)://.*(\\.jpg|\\.jpeg|\\.png|\\.gif)$")) {
            return "Invalid image URL format. Must start with http/https and end with a valid image extension.";
        }
        return null;
    }

    public static String validateFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return "Full Name cannot be empty.";
        }
        if (fullName.length() < 3) {
            return "Full Name must be at least 3 characters long.";
        }
        return null;
    }

    public static String validateEmail(String email) {
        if (email == null || email.isBlank()) {
            return "Email cannot be empty.";
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            return "Invalid email format.";
        }
        return null;
    }

    public static String validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return "Phone number cannot be empty.";
        }
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        if (!matcher.matches()) {
            return "Phone number must be a 10-digit number.";
        }
        return null;
    }

    public static String validateDob(String dobParam) {
        if (dobParam == null || dobParam.isBlank()) {
            return "Date of Birth cannot be empty.";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate dob = LocalDate.parse(dobParam, formatter);
            if (dob.isAfter(LocalDate.now())) {
                return "Date of Birth cannot be in the future.";
            }
        } catch (DateTimeParseException e) {
            return "Invalid Date of Birth format. Please use the format dd-MM-yyyy.";
        }
        return null;
    }
    public static boolean isValidTitle(String title) {
        return title != null ;
    }
    
    // Kiểm tra input có rỗng hoặc chỉ chứa khoảng trắng không
    public static boolean isEmptyOrWhitespace(String input) {
        return input == null || input.trim().isEmpty();
    }
    
    // Kiểm tra input có chứa ký tự đặc biệt không
    public static boolean containsSpecialCharacters(String input) {
        return input != null && Pattern.compile("[^a-zA-Z0-9\\s]").matcher(input).find();
    }
    
    // Validate deadline: không được chọn ngày trong quá khứ
    public static boolean validateDeadline(String deadline_raw) {
        LocalDate deadline = null;
        try {
            deadline = LocalDate.parse(deadline_raw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deadline == null || !deadline.isBefore(java.time.LocalDate.now());
    }
    
    // Kiểm tra xem người dùng đã chọn monthyear chưa
    public static boolean isValidMonthYear(String monthYear) {
        return monthYear != null && !monthYear.trim().isEmpty();
    }
    public static String escapeSQL(String input) {
    return input.replace("'", "''")
                .replace("\"", "\\\"")
                .replace("\\", "\\\\")
                .replace(";", "\\;")
                .replace("--", "—")
                .replace("/*", "/ *")
                .replace("*/", "* /");
}

}
