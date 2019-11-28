package server;

import server.model.User;

import java.sql.SQLException;

public class Login {
    User user;

    public boolean logingUser() throws SQLException {
        if(!dbQuerys.isLogged(user.getLogin())) {
            if(dbQuerys.getRightPassword(user.getLogin()).equals(user.getPassword())) {
                return true;
            }
        }

        return false;
    }
}
