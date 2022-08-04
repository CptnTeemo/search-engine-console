package dbconnection;

import java.sql.*;

public class DBConnection {
    private static Connection connection;

    private static String dbName = "search_engine";
    private static String dbUser = "root";
    private static String dbPass = "testtest";

    private static StringBuilder insertQuery = new StringBuilder();

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName + "?allowPublicKeyRetrieval=true&useSSL=false" +
                                "&user=" + dbUser + "&password=" + dbPass);
//                createTestTable();
//                createIndexTable();
//                createPageTable();
//                createLemmaTable();
//                createFieldTable();
//                createSiteTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void createTestTable() throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS test_table");
        connection.createStatement().execute("CREATE TABLE test_table(" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "lemma VARCHAR(255) NOT NULL, " +
                "frequency INT NOT NULL, " +
                "site_id INT NOT NULL)");
    }

    public static void createPageTable() throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS page");
        connection.createStatement().execute("CREATE TABLE page(" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "path TEXT NOT NULL, " +
                "code INT NOT NULL, " +
                "content MEDIUMTEXT NOT NULL, " +
                "site_id INT NOT NULL)");
    }

    public static void createFieldTable() throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS field");
        connection.createStatement().execute("CREATE TABLE field(" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "selector VARCHAR(255) NOT NULL, " +
                "weight FLOAT NOT NULL)");
        String sql = "INSERT INTO field(name, selector, weight) " +
                "VALUES ('title', 'title', '1.0'), ('body', 'body', '0.8')";
        DBConnection.getConnection().createStatement().execute(sql);
    }

    public static void createLemmaTable() throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS lemma");
        connection.createStatement().execute("CREATE TABLE lemma(" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "lemma VARCHAR(255) NOT NULL, " +
                "frequency INT NOT NULL, " +
                "site_id INT NOT NULL)");
    }

    public static void createIndexTable() throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS index_table");
        connection.createStatement().execute("CREATE TABLE index_table(" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "page_id INT NOT NULL, " +
                "lemma_id INT NOT NULL, " +
                "rank_rate FLOAT NOT NULL)");
    }

    public static void createSiteTable() throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS site_table");
        connection.createStatement().execute("CREATE TABLE site_table(" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "status ENUM('INDEXING', 'INDEXED', 'FAILED') NOT NULL, " +
                "status_time DATETIME NOT NULL, " +
                "last_error TEXT, " +
                "url VARCHAR(255) NOT NULL, " +
                "name VARCHAR(255) NOT NULL)");
    }

//    public static void executeMultiQueryPage() throws SQLException {
//        String sql = "INSERT INTO page(path, code, content) " +
//                "VALUES" + insertQuery.toString();
//        DBConnection.getConnection().createStatement().execute(sql);
//    }
//
//    public static void pageWriter(String path, int code, String content) throws SQLException {
//        insertQuery.append((insertQuery.length() == 0 ? "" : ",") +
//                "('" + path + "', '" + code + "', '" + content + "')");
//        if (insertQuery.length() > 1_000_000) {
//            executeMultiQueryPage();
//            insertQuery = null;
//            insertQuery = new StringBuilder();
//        }
//    }
//
//    public static void executeMultiQueryLemma() throws SQLException {
//        String sql = "INSERT INTO lemma(lemma, `frequency`) " +
//                "VALUES" + insertQuery.toString() +
//                "ON DUPLICATE KEY UPDATE `frequency`=`frequency` + 1";
//        DBConnection.getConnection().createStatement().execute(sql);
//    }
//
//    public static void lemmaWriter(String lemma, int frequency) throws SQLException {
//        insertQuery.append((insertQuery.length() == 0 ? "" : ",") +
//                "('" + lemma + "', '" + frequency + "')");
//        if (insertQuery.length() > 1_000_000) {
//            executeMultiQueryPage();
//            insertQuery = null;
//            insertQuery = new StringBuilder();
//        }
//
//    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }

}