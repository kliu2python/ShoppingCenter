package pool;

import util.ConfigReader;
import java.sql.*;

public class MyConnection extends AdapterConnection{

    private Connection conn;
    //Flag bit, true indicates occupied, false indicates the channel is available
    private boolean used = false;

    private static String driver;
    private static String url;
    private static String user;
    private static String password ;

    //Static block, let the step of loading the class execute only once, and initialize four attributes
    static {
        try {
            driver = ConfigReader.getPropertyValue("driver");
            url = ConfigReader.getPropertyValue("url");
            user = ConfigReader.getPropertyValue("user");
            password = ConfigReader.getPropertyValue("password");
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    //It is used to initialize the connection channel. It is initialized every time it is created
    {
        try {
            conn = DriverManager.getConnection(url, user, password);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Get connection channel
    public Connection getConn() {
        return conn;
    }

    //Determine whether the connection channel is available
    public boolean isUsed() {
        return used;
    }

    //Set flag bit
    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return this.conn.createStatement();
    }

    //Overridden the method inherited from the adapterconnection class
    //This method is to obtain the state parameters. In essence, it calls Preparedstatement
    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        PreparedStatement pstate = this.conn.prepareStatement(sql);
        return pstate;
    }

    //Overridden the method inherited from the adapterconnection class
    //The purpose of this method is to make the connection channel available, which looks like closing the flow
    @Override
    public void close() throws SQLException {
        this.used = false;
    }
}
