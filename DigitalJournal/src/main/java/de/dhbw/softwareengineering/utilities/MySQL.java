package de.dhbw.softwareengineering.utilities;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class MySQL {

    private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;

    private Connection connection;

    private static MySQL instance;

    private MySQL(){

        FileConfiguration mysqlConfiguration = new FileConfiguration(new File("./confs/mysql.conf"));

        mysqlConfiguration.setDefaultValue("user", "root");
        mysqlConfiguration.setDefaultValue("database", "digital_journal");
        mysqlConfiguration.setDefaultValue("password", "asdf");
        mysqlConfiguration.setDefaultValue("port", "3306");
        mysqlConfiguration.setDefaultValue("hostname", "localhost");

        this.user = mysqlConfiguration.getString("user");
        this.database = mysqlConfiguration.getString("database");
        this.password = mysqlConfiguration.getString("password");
        this.port = mysqlConfiguration.getString("port");
        this.hostname = mysqlConfiguration.getString("hostname");

        try {
            mysqlConfiguration.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MySQL getInstance(){
        if(instance == null){
            instance = new  MySQL();
        }
        return instance;
    }

    public Connection openConnection() {
        closeConnection();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public boolean checkConnection() {
        try {
            return connection != null && connection.isValid(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return Connection (save but slower)
     */
    public Connection getConnection() {

        if(!checkConnection())
            return openConnection();

        return connection;
    }

    /**
     * @return Connection (faster but may be invalid so be careful)
     */
    public Connection getConnectionUnsafe() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("Error closing the MySQL Connection!");
                e.printStackTrace();
            }
        }
    }

    public void closeResources(ResultSet rs, PreparedStatement st){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
