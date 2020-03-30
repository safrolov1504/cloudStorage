package com.cloudStorage.service.workingWithMessage;

import com.cloudStorage.service.workWithSQL.BaseSQLServer;

import java.sql.SQLException;

public class SignIn {
    public static boolean checkUser(String user, String pass) {
        BaseSQLServer sqlServer = new BaseSQLServer();

        boolean flag = false;
        try {
            sqlServer.start();
            flag = sqlServer.checkUser(user,pass);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                sqlServer.stop();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       return flag;
    }

}
