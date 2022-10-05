package net.revature.service;

import net.revature.exception.InvalidLoginException;
import net.revature.model.User;
import net.revature.repository.UserRepository;

import java.sql.SQLException;

public class AuthService {

    private UserRepository userRepo = new UserRepository();

    public User login(String username, String password) throws SQLException, InvalidLoginException {

        User user = userRepo.getUserByUsernameAndPassword(username, password);


        if (user == null) { // did not successfully log in
            throw new InvalidLoginException("Invalid username and/or password!");
        }

        return user;
    }

    public User register(User user) throws SQLException {
        if(user.getUsername().contains(" ") || user.getPassword().contains(" ")){
            throw new IllegalArgumentException("Spaces are not allowed in usernames and passwords!");
        }
        else {

            return userRepo.addUser(user);

        }

    }
}
