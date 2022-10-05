package net.revature.controller;

import io.javalin.Javalin;
import net.revature.exception.InvalidLoginException;
import net.revature.model.User;
import net.revature.service.AuthService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class AuthController {

    private AuthService authService = new AuthService();


    //    HttpSession httpSession = ctx.req.getSession();
    //    User user = (User) httpSession.getAttribute("user");
    //    HttpSession session = ctx.req.getSession();
    //    session.setAttribute("user", user);


    public void mapEndpoints(Javalin app){
        app.post("/login", (ctx) -> {
            User credentials = ctx.bodyAsClass(User.class);

            try {
                User user = authService.login(credentials.getUsername(), credentials.getPassword());


                HttpSession session = ctx.req.getSession();
                session.setAttribute("user", user);
            } catch (InvalidLoginException | SQLException e) {
                ctx.status(400); // 400 Bad Request
                ctx.result(e.getMessage());
            }

//            if(ctx.status() != 400){
//                    HttpSession httpSession = ctx.req.getSession();
//                    User user = (User) httpSession.getAttribute("user");
//                    HttpSession session = ctx.req.getSession();
//                    session.setAttribute("user", user);
//                ctx.result("Login Successful!");
//            }

        });

        app.post("/register", (ctx) ->{

            User user = ctx.bodyAsClass(User.class);

            try{
                User u = authService.register(user);
                ctx.json(u);
            } catch (IllegalArgumentException | SQLException e){
                ctx.result(e.getMessage());
                ctx.status(400);
            }

            if(ctx.status() != 400){
                ctx.result("Employee Registration Successful!");
            }

        });

        app.post("/logout", (ctx) -> {
            ctx.req.getSession().invalidate(); // Invalidate an active HttpSession
            ctx.result("You Have Logged Out!");
        });

    }
}
