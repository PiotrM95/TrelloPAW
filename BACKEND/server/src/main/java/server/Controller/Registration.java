package server.Controller;

import server.dbQuerys;
import server.model.NewUser;

import java.sql.SQLException;

public class Registration {
    NewUser newUser;

    public void registry() throws SQLException {
        if(okPassword()) {
            if(!checkIfUserExist(newUser.getLogin())) {
                // rejestracja udana
                dbQuerys.setNewUser(newUser.getLogin(), newUser.getPassword());
            } else {
                // rejestracja nieudana
            }

        }
    }

    private boolean okPassword() {
        if (newUser.getPassword().equals(newUser.getRepeatedPasswrod())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIfUserExist (String login) throws SQLException {
        return dbQuerys.checkExistUser(login);
    }
}