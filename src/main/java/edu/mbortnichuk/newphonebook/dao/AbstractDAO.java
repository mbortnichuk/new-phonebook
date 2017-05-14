package edu.mbortnichuk.newphonebook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Mariana on 07-May-17.
 */
public abstract class AbstractDAO<T> implements DAO<T>{

    protected Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "pass");

            return connection;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    protected void closeConnection(Connection connection) {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
