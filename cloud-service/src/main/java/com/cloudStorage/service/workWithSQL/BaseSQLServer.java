package com.cloudStorage.service.workWithSQL;

import java.sql.*;

public class BaseSQLServer implements SQLServer {
    public static Connection connection;
    //private static final String URL_SQL = "jdbc:sqlite:/Users/safrolov/Documents/JavaProgramming/01_readyProjects/cloud-storageNew/cloud-service/src/main/resources/bd_user.db";
    private static final String URL_SQL = "jdbc:sqlite://Users/safrolov/Documents/JavaProgramming/cloudStorage/cloud-service/src/main/resources/com.cloud.service/bd_user.db";

    @Override
    public void start() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(URL_SQL);
    }

    @Override
    public void stop() throws SQLException {
        connection.close();
    }

    public boolean checkUser(String user, String pass) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement
                ("SELECT Login, Password FROM login_table where Login = ? AND Password = ?");
        preparedStatement.setString(1,user);
        preparedStatement.setString(2,pass);
        ResultSet rs = preparedStatement.executeQuery();
        preparedStatement.addBatch();
        if(rs.isBeforeFirst()){
            return true;
        } else {
            return false;
        }
    }
}
