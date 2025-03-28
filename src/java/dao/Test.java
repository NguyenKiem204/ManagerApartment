/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.oracle.wls.shaded.org.apache.bcel.generic.AALOAD;
import java.io.InputStream;
import java.security.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Apartment;
import model.ApartmentAssets;
import model.Comment;
import model.Feedback;
import model.Image;
import model.ImageFeedback;
import model.ManagerFeedback;
import model.Notification;
import model.Request;
import model.Resident;
import model.Role;
import model.RoleEnum;
import model.Staff;
import model.TypeRequest;

/**
 *
 * @author nkiem
 */
public class Test {

    public static void main(String[] args) {
//        DBContext.testConnection();
        StaffDAO staffDAO = new StaffDAO();
        ResidentDAO residentDAO = new ResidentDAO();
        RoleDAO roleDAO = new RoleDAO();
        ImageDAO imageDAO = new ImageDAO();
        ApartmentDAO apartmentDAO = new ApartmentDAO();
        //FeedbackDAO feedbackDAO = new FeedbackDAO();
        RequestDAO requestDAO = new RequestDAO();
        TypeRequestDAO typeRequestDAO = new TypeRequestDAO();
        StatusRequestDAO statusRequestDAO = new StatusRequestDAO();
        ManagerFeedbackDAO managerFeedbackDAO = new ManagerFeedbackDAO();
        NotificationDAO notificationDAO = new NotificationDAO();
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        CommentDAO commentDAO = new CommentDAO();

        List<Request> list = requestDAO.getAllRequestsBySearchOrFilterOrSortOfResident(null, 0, 0, 0, 1, 5, 3);
        for (Request request : list) {
            System.out.println(request.toString());
        }
//        Map<String, Integer> requestCounts = requestDAO.getRequestCountLast7Days();
//
//for (Map.Entry<String, Integer> entry : requestCounts.entrySet()) {
//    System.out.println(entry.getKey() + " : " + entry.getValue());
        ApartmentAssetsDAO apartmentAssetsDAO = new ApartmentAssetsDAO();
        AssetCategoryDAO assetCategoryDAO = new AssetCategoryDAO();

//        ApartmentAssetsDAO apartmentAssetsDAO = new ApartmentAssetsDAO();
//        AssetCategoryDAO assetCategoryDAO = new AssetCategoryDAO();
//        List<ApartmentAssets> list = apartmentAssetsDAO.selectFirstPage();
//        for (ApartmentAssets apartmentAssets : list) {
//            System.out.println(apartmentAssets.toString());
//        }
        // List<Comment> list = commentDAO.getCommentsByNewsId(22);
        // for (Comment comment : list) {
        //     System.out.println(comment);
        // }
//        List<Double> list = invoiceDAO.getRevenueByMonth(2025);
//        for (Double double1 : list) {
//            System.out.println(double1);
//        }
        // List<String> list = apartmentDAO.getApartmentNames("a");
        // for (String string : list) {
        //     System.out.println(string);
        // }
//        ManagerFeedback managerFeedback = managerFeedbackDAO.selectById(21);
//        managerFeedback.setStaffResponse("toi da update response. HEHE");
//        managerFeedbackDAO.update(managerFeedback);
//        ManagerFeedback t = new ManagerFeedback(LocalDate.now(), 16, 4.3, 60, 40, "niceeeeeeeeeeeeeeee", "need to improve moreeeee", null, "skill trainning", LocalDate.of(2025, 3, 12), LocalDateTime.now(), staffDAO.selectById(4));
//        managerFeedbackDAO.insert(t);
//        List<Request> list = requestDAO.selectFirstPageOfStaff(4);
//        for (Request request : list) {
//            System.out.println(request.toString());
//        }
//        ManagerFeedback t = new ManagerFeedback(LocalDate.now(), 10, 4.3, 82, 18, "Good luck", "Can cai thien nhieu", null, "training more", LocalDate.now().plusDays(3), LocalDate.now(), staffDAO.getStaffByID(2));
//        managerFeedbackDAO.insert(t);
        // Ví dụ: thêm các đối tượng ImageFeedback vào danh sách
        // Giả sử bạn đã có InputStream từ file và kích thước của ảnh (size)
//        InputStream imgStream1 = null;  // Thay bằng InputStream thực tế
//        long size1 = 1024L;
//
//        InputStream imgStream2 = null;  // Thay bằng InputStream thực tế
//        long size2 = 2048L;
        // Thêm đối tượng vào danh sách
//        imageFeedbackList.add(new ImageFeedback(imgStream1, size1));
//        imageFeedbackList.add(new ImageFeedback(imgStream2, size2));
//        List<Request> list = requestDAO.getAllRequestsBySearchOrFilterOrSort("nguye", 0, null, 0, 0);
//        for (Request request : list) {
//            System.out.println(request.toString());
//        }
//        
//        requestDAO.updateStatus(2, 2);
//        requestDAO.updateStatus(1, 1);
//        Role role = new Role("OWNER", "Some Permission");
//        Image image = new Image("./djhdjdhf/lo");
//        roleDAO.insert(role);
//Apartment apartment = new Apartment("606", "6", "ACTIVE", "RENT");
//apartmentDAO.insert(apartment);
        //Resident resident = new Resident("Nguyễn Kiểm", "kiem@12345", "0331477047", "3984136534764", "nkiem347@gmail.com",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
        //residentDAO.insert(resident);
//        Resident resident = new Resident("Nguyễn Van B", "123", "0328674333", "3984136534677", "b@gmail.com", LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(6).build());
//        residentDAO.insert(resident);
//        Resident resident = new Resident("Nguyễn Kiểm", "kiem@12345", "0331477047", "3984136534764", "nkiem347@gmail.com",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
//        residentDAO.insert(resident);

        //Resident resident = new Resident("Nguyễn Kiểm", "kiem@12345", "0331477047", "3984136534764", "nkiem347@gmail.com",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
        //residentDAO.insert(resident);
        // Resident resident1 = new Resident("Nguyễn MH", "123", "0328674349", "3984136534776", "mh@gmail.com",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(6).build());
        //residentDAO.insert(resident1);
<<<<<<< HEAD

        Resident resident = new Resident("Nguyễn Kiểm", "kiem@12345", "0331477047", "3984136534764", "nkiem347@gmail.com",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
        residentDAO.insert(resident);
        Resident resident1 = new Resident("Nguyễn MH", "123", "0328674344", "3984136534766", "mh@gmail.com", LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
        residentDAO.insert(resident1);
        Resident resident2 = new Resident("Nguyễn Thị B", "123", "0333479047", "3984136534064", "b@gmail.com", LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
        residentDAO.insert(resident2);


=======

>>>>>>> a1fcf3c0204f8e528f5c144d1d8dbb0e569a6043
//        Resident resident = new Resident("Nguyễn Kiểm", "kiem@12345", "0331477047", "3984136534764", "nkiem347@gmail.com",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
//        residentDAO.insert(resident);
//        Resident resident1 = new Resident("Nguyễn MH", "123", "0328674344", "3984136534766", "mh@gmail.com", LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
//        residentDAO.insert(resident1);
//        Resident resident2 = new Resident("Nguyễn Thị B", "123", "0333479047", "3984136534064", "b@gmail.com", LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
//        residentDAO.insert(resident2);

<<<<<<< HEAD
=======

//        Resident resident = new Resident("Nguyễn Kiểm", "kiem@12345", "0331477047", "3984136534764", "nkiem347@gmail.com",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
//        residentDAO.insert(resident);
//        Resident resident1 = new Resident("Nguyễn MH", "123", "0328674344", "3984136534766", "mh@gmail.com", LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
//        residentDAO.insert(resident1);
//        Resident resident2 = new Resident("Nguyễn Thị B", "123", "0333479047", "3984136534064", "b@gmail.com", LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
//        residentDAO.insert(resident2);

>>>>>>> a1fcf3c0204f8e528f5c144d1d8dbb0e569a6043

       //Resident resident = new Resident("Nguyễn Kiểm", "kiem@12345", "0331477047", "3984136534764", "nkiem347@gmail.com",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
       //residentDAO.insert(resident);

//      Resident resident1 = new Resident("Nguyễn MH", "123", "0328674349", "3984136534776", "mh@gmail.com",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(6).build());
//       residentDAO.insert(resident1);

//        Resident resident = new Resident("Nguyễn Kiểm", "kiem@12345", "0331477047", "3984136534764", "nkiem347@gmail.com",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
//        residentDAO.insert(resident);

//      Resident resident = new Resident("Nguyễn MH", "123", "0328674344", "3984136534766", "nkiem347@gmail.com",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
//       residentDAO.insert(resident);

//        Resident resident = new Resident("Nguyễn Kiểm", "12345", "0333477047", "3284136534764", "cuongnmhe182472@fpt.edu.vn",  LocalDate.of(2004, 12, 3), "Female", "ACTIVE", new Image().builder().imageID(2).build(), new Role().builder().roleID(7).build());
//        residentDAO.insert(resident);



<<<<<<< HEAD

//        imageDAO.insert(image);
//       Staff staff = new Staff("Nguyễn Văn Kiểm", "123", "0931654542", "6634983361194", "a@gmail.com",  LocalDate.of(2000, 6, 27), "Male", "Active", new Image().builder().imageID(1).build(), new Role().builder().roleID(3).build());
//        staffDAO.insert(staff);
//        Staff staff = new Staff("Nguyễn Thị Mèo", "123", "0984249518", "6134983361194", "meo@gmail.com", LocalDate.of(2003, 6, 27), "Male", "Active", new Image().builder().imageID(1).build(), new Role().builder().roleID(2).build());
//        staffDAO.insert(staff);
=======
//        imageDAO.insert(image);
//       Staff staff = new Staff("Nguyễn Văn Kiểm", "123", "0931654542", "6634983361194", "a@gmail.com",  LocalDate.of(2000, 6, 27), "Male", "Active", new Image().builder().imageID(1).build(), new Role().builder().roleID(3).build());
//        staffDAO.insert(staff);
////        Staff staff = new Staff("Nguyễn Thị Mèo", "123", "0984249518", "6134983361194", "meo@gmail.com", LocalDate.of(2003, 6, 27), "Male", "Active", new Image().builder().imageID(1).build(), new Role().builder().roleID(2).build());
////        staffDAO.insert(staff);
>>>>>>> a1fcf3c0204f8e528f5c144d1d8dbb0e569a6043
//        Staff staff1 = new Staff("Nguyễn Kiểm Admin", "kiem@12345", "0336750744", "044567203453", "kiemnvhe186025@fpt.edu.vn",  LocalDate.of(2000, 6, 27), "Male", "Active", new Image().builder().imageID(1).build(), new Role().builder().roleID(1).build());
//         staffDAO.insert(staff1); 
//        Staff staff = new Staff("Nguyễn Văn Kiểm", "kiem@12345", "0931654542", "6634983361194", "nkien349@gmail.com",  LocalDate.of(2000, 6, 27), "Male", "Active", new Image().builder().imageID(1).build(), new Role().builder().roleID(3).build());
//        staffDAO.insert(staff);
        Staff staff = new Staff("Nguyễn Văn a", "123", "0353215889", "001204040218", "aya@gmail.com",  LocalDate.of(2000, 6, 27), "Male", "Active", new Image().builder().imageID(1).build(), new Role().builder().roleID(4).build());
        staffDAO.insert(staff);
//        Staff staff = new Staff("Nguyễn Văn a", "123", "0353515789", "001215040218", "a@gmail.com",  LocalDate.of(2000, 6, 27), "Male", "Active", new Image().builder().imageID(1).build(), new Role().builder().roleID(3).build());
//        staffDAO.insert(staff);

        // Staff staff = new Staff("Nguyễn Kiểm Admin", "kiem@12345", "0336750744", "044567203453", "kiemnvhe186025@fpt.edu.vn",  LocalDate.of(2000, 6, 27), "Male", "Active", new Image().builder().imageID(1).build(), new Role().builder().roleID(1).build());
        // staffDAO.insert(staff); 


//         Staff staff = new Staff("Nguyễn Kiểm Admin", "kiem@12345", "0336750744", "044567203453", "kiemnvhe186025@fpt.edu.vn",  LocalDate.of(2000, 6, 27), "Male", "Active", new Image().builder().imageID(1).build(), new Role().builder().roleID(1).build());
//         staffDAO.insert(staff); 


<<<<<<< HEAD

=======
>>>>>>> a1fcf3c0204f8e528f5c144d1d8dbb0e569a6043
//        Feedback fb = new Feedback("Account not good", "Good", LocalDate.of(2025, 01, 10), 4, 2, 1);
//        feedbackDAO.insert(fb);
//         List list = feedbackDAO.getAllFeedbacksSortedByStaff();
//         for (Object object : list) {
//             System.out.println(object.toString());
//        }
//        Request rq = new Request("Lam lai hanh lang cho tang 8", "Hanh lang bi hu hai", "hong", LocalDate.now(), 3, 2, 1);
//        System.out.println(requestDAO.insert(rq));
//        requestDAO.insert(rq);
//        List<Request> listrq = requestDAO.selectAll();
//        for (Request request : listrq) {
//            System.out.println(request.toString());
    }
//        System.out.println(residentDAO.getResidentDetailByID(3));
//        List<Staff> list = staffDAO.selectAll();
//        for (Staff staff : list) {
//            System.out.println(staff);
//        }
//
//    }
}
