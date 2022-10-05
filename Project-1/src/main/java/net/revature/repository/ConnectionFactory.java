package net.revature.repository;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection createConnection() throws SQLException {

        Driver postgresDriver = new Driver();
        DriverManager.registerDriver(postgresDriver);


        String url = "jdbc:postgresql://127.0.0.1:5432/postgres?currentschema=public";



        String username = System.getenv("database_username"); // System.getenv is used to read the value of an environment variable
        String password = System.getenv("database_password"); // System.getenv is used to read the value of an environment variable

        Connection connectionObject = DriverManager.getConnection(url, username, password);

        return connectionObject;
    }

}
