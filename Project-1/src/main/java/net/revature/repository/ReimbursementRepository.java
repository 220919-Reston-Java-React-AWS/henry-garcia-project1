package net.revature.repository;

import io.javalin.Javalin;
import net.revature.model.Reimbursement;
import net.revature.model.User;
import net.revature.service.AuthService;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementRepository {
//    HttpSession httpSession = ctx.req.getSession();
//    User user = (User) httpSession.getAttribute("user");
//    HttpSession session = ctx.req.getSession();
//    session.setAttribute("user", user);

    User u = new User();
    public Reimbursement addTicket(Reimbursement re) throws SQLException { // Create in CRUD

        try (Connection con = ConnectionFactory.createConnection()) {
            String sql = "insert into ReimbursementTable (reimbursement_amount, description, ticket_status, submitter_id) values (?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int empId = u.getId();

            pstmt.setInt(1, re.getAmount());
            pstmt.setString(2, re.getDesc());
            pstmt.setString(3, "Pending");
            pstmt.setInt(4, re.getEmpid());
//            pstmt.setInt(5, re.getManid());


            int numberOfRecordsAdded = pstmt.executeUpdate();


            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);


            return new Reimbursement(id, re.getAmount(), re.getDesc(), "Pending", empId, re.getManid());
        }
    }

    public List<Reimbursement> getAllReimbursements() throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {

            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT * FROM ReimbursementTable WHERE ticket_status = 'Pending'";

            Statement stmt = connectionObject.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // ResultSet represents the temporary table of query results

            // Iterating through the ResultSet
            while (rs.next()) {
                int id = rs.getInt("request_id");
                int amount = rs.getInt("reimbursement_amount");
                String desc = rs.getString("description");
                String ticketStatus = rs.getString("ticket_status");
                int empid = rs.getInt("submitter_id");
                int manid = rs.getInt("manager_id");

                Reimbursement reimbursement = new Reimbursement(id, amount, desc, ticketStatus, empid, manid);

                reimbursements.add(reimbursement); // add reimbursement to List
            }

            return reimbursements;
        }
    }
//HttpSession httpSession = ctx.req.getSession();
//            User user = (User) httpSession.getAttribute("user");
    //HttpSession session = ctx.req.getSession();
//                session.setAttribute("user", user);
    public List<Reimbursement> getAllReimbursementsForEmployee(int empId) throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {

            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT * FROM ReimbursementTable WHERE submitter_id = ?";

            PreparedStatement pstmt = connectionObject.prepareStatement(sql);

            pstmt.setInt(1, empId);

            ResultSet rs = pstmt.executeQuery();
            // ResultSet represents the temporary table of query results

            // Iterating through the ResultSet
            while (rs.next()) {
                int id = rs.getInt("request_id");
                int amount = rs.getInt("reimbursement_amount");
                String desc = rs.getString("description");
                String ticketStatus = rs.getString("ticket_status");
                int empid = rs.getInt("submitter_id");
                int manid = rs.getInt("manager_id");

                Reimbursement reimbursement = new Reimbursement(id, amount, desc, ticketStatus, empid, manid);

                reimbursements.add(reimbursement); // add assignment to List
            }

            return reimbursements;
        }
    }

    public boolean processTicket(int ticketId, String ticketStatus, int manid) throws SQLException {

        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String SQL = "UPDATE ReimbursementTable SET ticket_status = ?, manager_id = ? WHERE request_id = ?";

            PreparedStatement pstmt = connectionObj.prepareStatement(SQL);
//            manId = u.getId();


            pstmt.setString(1, ticketStatus);
            pstmt.setInt(2, manid);
            pstmt.setInt(3, ticketId);

//            pstmt.executeQuery();

            int numberOfRecordsUpdated = pstmt.executeUpdate();
//            System.out.println(numberOfRecordsUpdated); // Test to see if executeUpdate is returning 1.



            return numberOfRecordsUpdated == 1;


        }
    }


    public Reimbursement getReimbursementById(int id) throws SQLException {
        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String sql = "SELECT * FROM ReimbursementTable WHERE request_id = ?";

            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery(); // 0 rows or 1 row

            if (rs.next()) {
                int ticketid = rs.getInt("request_id");
                int amount = rs.getInt("reimbursement_amount");
                String desc = rs.getString("description");
                String ticketStatus = rs.getString("ticket_status");
                int empid = rs.getInt("submitter_id");
                int manid = rs.getInt("manager_id");

                return new Reimbursement(ticketid, amount, desc, ticketStatus, empid, manid);
            } else {
                return null;
            }
        }
    }

//    public Reimbursement processTicket(int ticketId, String ticketStatus, int manId) throws SQLException {
//
//        try (Connection connectionObj = ConnectionFactory.createConnection()) {
//            String sql = "UPDATE ReimbursementTable SET ticket_status = ?, manager_id = ? WHERE submitter_id = ?";
//
//            PreparedStatement pstmt = connectionObj.prepareStatement(sql);
//            int manId = u.getId();
//
//            pstmt.setString(1, getTicketStatus());
//            pstmt.setInt(2, reimbursement.getManid());
//            pstmt.setInt(3, reimbursement.getEmpid());
//
//            int numberOfRecordsUpdated = pstmt.executeUpdate();
//
////            return numberOfRecordsUpdated == 1;
//            return new Reimbursement();
//        }
//    }
}