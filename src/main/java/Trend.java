import java.sql.*;
import java.util.Arrays;
import java.util.Map;

public class Trend {
    double[][] hourTrend;
    Connection connection;
    Statement st;
    ResultSet rs;

    Trend() {

        getHistoricalDataFromDB();
    }

    private void getHistoricalDataFromDB() {
        try {
            connection = JDBConnector.setUpConnectionToDB();
            st = connection.createStatement();
            rs = st.executeQuery("select count(*) from bybit_btcusd60.trend60");
            rs.next();
            hourTrend = new double[rs.getInt(1)][6];

            rs = st.executeQuery("select * from bybit_btcusd60.trend60");


            int i = 0;
            while (rs.next()) {
                hourTrend[i][0] = rs.getInt(1);
                hourTrend[i][1] = rs.getDouble(2);
                hourTrend[i][2] = rs.getDouble(3);
                hourTrend[i][3] = rs.getDouble(4);
                hourTrend[i][4] = rs.getDouble(5);
                hourTrend[i++][5] = rs.getDouble(6);
            }

            System.out.println("Таблица BTC-USD " + Arrays.toString(hourTrend[hourTrend.length - 1]));

        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных");
            e.printStackTrace();
        }
    }

    void uploadResults(double initialDepo, double finalDepo, int ordersCount,Map<Integer,Integer> trades, double[] takeProfitAt) {
        String insertValues = String.format("VALUES (%d, %d, " +
                        "%d, " +
                        "%d, %d, %d, %d, " +
                        "null, null, null, " +
                        "%.4f, %.4f, %.4f, %.4f, %.4f, null, null, null)",
                (int)initialDepo, (int)finalDepo,
                ordersCount,
                trades.get(1),trades.get(2),trades.get(3),trades.get(4),
                takeProfitAt[0], takeProfitAt[1], takeProfitAt[2], takeProfitAt[3], takeProfitAt[4]);
        try {
            st.executeUpdate(
                    "INSERT INTO bybit_btcusd60.results" +
                            " (start_depo, final_depo, trades, rebuy_1, rebuy_2, rebuy_3, rebuy_4, rebuy_5, rebuy_6, rebuy_7, percentage_0, percentage_1, percentage_2, percentage_3, percentage_4, percentage_5, percentage_6, comments) " +
                            insertValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void closeConnectionToDB() {
        try {
            connection.close();
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
