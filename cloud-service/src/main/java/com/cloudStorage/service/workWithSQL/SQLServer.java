package com.cloudStorage.service.workWithSQL;

import java.sql.SQLException;

public interface SQLServer {
    void start() throws SQLException, ClassNotFoundException;
    void stop() throws SQLException;
}
