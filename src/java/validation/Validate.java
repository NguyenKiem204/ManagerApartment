/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            LocalDate dob = LocalDate.parse(dobParam, formatter);
            if (dob.isAfter(LocalDate.now())) {
                return "Date of Birth cannot be in the future.";
            }
        } catch (DateTimeParseException e) {
            return "Invalid Date of Birth format. Please use the format dd/MM/yyyy.";
        }
        return null;
    }

    public static boolean isValidTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }

        // Loại bỏ các ký tự đặc biệt không cho phép
        String forbiddenChars = "<>{}/\\|\"';&";
        for (char c : forbiddenChars.toCharArray()) {
            if (title.indexOf(c) != -1) {
                return false;
            }
        }

        return true;
    }
    
    public static boolean isValidLocation(String title) {
        
        // Loại bỏ các ký tự đặc biệt không cho phép
        String forbiddenChars = "<>{}/\\|\"';&";
        for (char c : forbiddenChars.toCharArray()) {
            if (title.indexOf(c) != -1) {
                return false;
            }
        }

        return true;
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
        if (deadline_raw == null || deadline_raw.trim().isEmpty()) {
            return true; // Nếu chuỗi null hoặc rỗng, trả về false ngay
        }

        deadline_raw = deadline_raw.trim(); // Loại bỏ khoảng trắng đầu/cuối

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        try {
            LocalDate deadline = LocalDate.parse(deadline_raw, formatter);
            return !deadline.isBefore(LocalDate.now()); // Trả về true nếu deadline >= hôm nay
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false; // Trả về false nếu format sai
        }
    }
    
    public static boolean validateBoughtOn(String boughtOn_raw) {
        if (boughtOn_raw == null || boughtOn_raw.trim().isEmpty()) {
            return true; // Nếu chuỗi null hoặc rỗng, trả về false ngay
        }

        boughtOn_raw = boughtOn_raw.trim(); // Loại bỏ khoảng trắng đầu/cuối

        try {
            Date boughtOn = Date.valueOf(boughtOn_raw);
            Date today = new Date(System.currentTimeMillis());
            return !boughtOn.after(today); // Trả về true nếu deadline >= hôm nay
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false; // Trả về false nếu format sai
        }
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
     public static boolean validateReguationsName(String ruleName) {
        if (ruleName == null || ruleName.trim().isEmpty()) {
            return false; // Không được để trống
        }
        return true;
    }

    public static String normalizeSearchString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ""; // Nếu chuỗi null hoặc rỗng, trả về rỗng
        }

        // Xóa khoảng trắng thừa và tách các từ
        String[] words = input.trim().replaceAll("\\s+", " ").split(" ");

        StringBuilder normalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                // Viết hoa chữ cái đầu, các chữ sau viết thường
                normalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return normalized.toString().trim(); // Xóa khoảng trắng cuối cùng
    }

    public static String validateRuleName(String ruleName) throws Exception {
        if (ruleName == null || ruleName.isBlank()) {
            throw new Exception("Rule name cannot be empty.");
        }
        String regex = "[a-zA-Z0-9 ]+"; // chỉ chữ cái, số và dấu cách
        if (!Pattern.matches(regex, ruleName)) {
            throw new Exception("Rule name cannot contains special characters.");
        }
        return ruleName.trim();
    }

    public static String validateRuleDescription(String ruleDescription) throws Exception {
        if (ruleDescription == null || ruleDescription.isBlank()) {
            throw new Exception("Description cannot be empty.");
        }
        System.out.println("raw description:");
        System.out.println(ruleDescription);
        String description = Jsoup.parse(ruleDescription).text().trim(); // bỏ thẻ html của ckeditor
        System.out.println("nomarlized description:");
        System.out.println(description);
        String regex = "[\\p{L}\\d\\s.,:;?!()\\-_\n]+";
        /**
         * \p{L}: các chữ cái của bất kì ngôn ngữ nào 
         * \d: 0-9 
         * \s: khoảng trắng
         * .,:;?!()-_\n các kí tự được cho phép
         */
        if (!Pattern.matches(regex, description)) {
            throw new Exception("Description cannot contains special characters.");
        }
        return description;
    }

}
