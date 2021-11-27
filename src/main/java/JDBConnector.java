import java.sql.*;

public class JDBConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/bybit_btcusd60";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection setUpConnectionToDB() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
