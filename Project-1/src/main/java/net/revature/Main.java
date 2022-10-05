package net.revature;

import io.javalin.Javalin;
import net.revature.controller.AuthController;
import net.revature.controller.ReimbursementController;
import net.revature.repository.UserRepository;
import net.revature.repository.ReimbursementRepository;
import net.revature.model.User;
import net.revature.model.Reimbursement;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        UserRepository nu = new UserRepository();
//        try {
//            User newUser = nu.addUser(new User(12,"Jane","Doe","jane_doe","test1",56));
//            System.out.println(newUser);
//        } catch (SQLException e){
//            e.printStackTrace();
//        }

        Javalin app = Javalin.create();

        AuthController ac = new AuthController();
        ac.mapEndpoints(app);

        ReimbursementController reimbursementController = new ReimbursementController();
        reimbursementController.mapEndpoints(app);

        app.get("/", (ctx) -> {
            ctx.status(200);
            ctx.result("Hello World and All Who Inhabit it!");
        });


        app.start(5000);

    }
}