package net.revature.controller;

import net.revature.exception.TicketAlreadyProcessedException;
import net.revature.exception.TicketNotFoundException;
import net.revature.model.Reimbursement;
import net.revature.model.User;
import net.revature.service.ReimbursementService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class ReimbursementController {

    private ReimbursementService reimbursementService = new ReimbursementService();

    public void mapEndpoints(Javalin app) {



        app.get("/reimbursements", (ctx) -> {
            // We must be logged in as a manager
            HttpSession httpSession = ctx.req.getSession();

            // retrieve the user object from user attribute
            User user = (User) httpSession.getAttribute("user");

            if (user != null) { // Check if logged in
                if (user.getRoleId() == 2) { // if they are a manager
                    List<Reimbursement> reimbursements = reimbursementService.getAllReimbursements();

                    ctx.json(reimbursements);
                } else if (user.getRoleId() == 1) { // if they are an employee
                    int empId = user.getId();

                    List<Reimbursement> reimbursements = reimbursementService.getAllReimbursementsForEmployee(empId);

                    ctx.json(reimbursements);
                } else {
                    ctx.result("You are logged in, but you're not a manager or employee!");
                    ctx.status(401);
                }
            } else {
                ctx.result("You are not logged in!");
                ctx.status(401);
            }
        });

        app.get("/reimbursements/pending", (ctx) -> {
            // We must be logged in as a manager
            HttpSession httpSession = ctx.req.getSession();

            // retrieve the user object from user attribute
            User user = (User) httpSession.getAttribute("user");

            if (user != null) { // Check if logged in
                if (user.getRoleId() == 2) { // if they are a manager

                    List<Reimbursement> reimbursements = reimbursementService.getAllPendingReimbursements();

                    ctx.json(reimbursements);
                } else if (user.getRoleId() == 1) { // if they are an employee

                    ctx.result("Only a manager is able to view all pending reimbursements!");
                    ctx.status(401);

                } else {
                    ctx.result("You are logged in, but you're not a manager or employee!");
                    ctx.status(401);
                }
            } else {
                ctx.result("You are not logged in!");
                ctx.status(401);
            }
        });

        app.post("/reimbursements/request", (ctx) -> {
            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");


            if (user != null) {
                if (user.getRoleId() == 1) { // User must be an employee to request reimbursements.

                    Reimbursement re = ctx.bodyAsClass(Reimbursement.class);
                    re.setEmpid(user.getId());

                    try{
                        Reimbursement r = reimbursementService.requestTicket(re);
                        ctx.json(r);
                    } catch (SQLException e){
                        ctx.result(e.getMessage());
                        ctx.status(400);
                    }
//                    if(ctx.status() != 400){
//                        ctx.result("Request successfully submitted!");
//                    }
                }
                else {
                    ctx.result("You must be logged in as an employee to request a reimbursement!");
                    ctx.status(401);
                }
            }
        });

            app.patch("/reimbursements/{ticketId}", (ctx) -> {

            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");

            if (user != null) { // Check if logged in
                // Check if user is a manager
                if (user.getRoleId() == 2) {
                    int manId = user.getId();
                    int ticketId = Integer.parseInt(ctx.pathParam("ticketId"));

                    Reimbursement a = ctx.bodyAsClass(Reimbursement.class); // JSON: { grade: 85 }
                    String ticketStatus = a.getTicketStatus();

                    try {
//                        boolean b = reimbursementService.processTicket(ticketId, ticketStatus, manId);
                        reimbursementService.processTicket(ticketId, ticketStatus, manId);

//                        ctx.json(b);

                        ctx.result("Ticket successfully processed!");
//                        System.out.println(ticketId);
//                        System.out.println(ticketStatus);
//                        System.out.println(manId);
                    } catch (TicketAlreadyProcessedException | IllegalArgumentException | SQLException e) {
                        ctx.result(e.getMessage());
                        ctx.status(400);
                    } catch (TicketNotFoundException e) {
                        ctx.result(e.getMessage());
                        ctx.status(404); // 404 NOT FOUND
                    }

                } else {
                    ctx.result("You are logged in, but you are not a manager!");
                    ctx.status(401);
                }
            } else {
                ctx.result("You are not logged in!");
                ctx.status(401);
            }

        });

    }

}
