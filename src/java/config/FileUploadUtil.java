package config;

import java.io.*;
import java.nio.file.*;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpServletRequest;

public class FileUploadUtil {

    public static String uploadAvatarImage(HttpServletRequest request, String avatarName) {
        String uploadDir = request.getServletContext().getRealPath("/assets/images/avatar/");
        System.out.println("Before cleaning: " + uploadDir);

        String cleanUploadDir = uploadDir.replaceAll("[/\\\\]build", "");
        System.out.println("After cleaning: " + cleanUploadDir);

        Part filePart;
        String imgURL = null;

        try {
            filePart = request.getPart("imgURL");
            if (filePart != null && filePart.getSize() > 0) {
                File uploadFolder = new File(cleanUploadDir);
                if (!uploadFolder.exists()) {
                    boolean created = uploadFolder.mkdirs();
                    if (!created) {
                        System.out.println("Failed to create upload directory: " + cleanUploadDir);
                        return null;
                    }
                }
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = avatarName + fileExtension;
                File file = new File(uploadFolder, newFileName);
                if (file.exists()) {
                    boolean deleted = file.delete();
                    if (!deleted) {
                        System.out.println("Failed to delete existing file: " + file.getAbsolutePath());
                        return null;
                    }
                }
                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("File successfully uploaded to: " + file.getAbsolutePath());
                } catch (IOException e) {
                    System.out.println("Error saving file: " + e.getMessage());
                    e.printStackTrace();
                    return null;
                }
                imgURL = "./assets/images/avatar/" + newFileName;
            } else {
                System.out.println("No file uploaded or file is empty.");
            }
        } catch (Exception e) {
            System.out.println("Error during file upload: " + e.getMessage());
            e.printStackTrace();
        }

        return imgURL;
    }
}
