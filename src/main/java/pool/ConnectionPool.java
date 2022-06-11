package pool;

import util.ConfigReader;

import javax.swing.*;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    //Design ConnectionPool into singleton mode
    private ConnectionPool(){}

    private static volatile ConnectionPool connectionPool;

    public static ConnectionPool getInstance(){
        if (connectionPool == null){
            synchronized (ConnectionPool.class){
                if (connectionPool == null){
                    connectionPool = new ConnectionPool();
                }
            }
        }
        return connectionPool;
    }

    //Get the minimum number of connections and waiting time
    private int minConnectCount = Integer.parseInt(ConfigReader.getPropertyValue("minConnectCount"));
    private int waitTime = Integer.parseInt(ConfigReader.getPropertyValue("waitTime"));

    //Property -- list collection, used to store connection objects
    private List<Connection> pool = new ArrayList<>();

    //Store connection objects in the pool collection
    {
        for (int i = 1; i <= minConnectCount; i ++){
            pool.add(new MyConnection());
        }
    }

    //Method to get the connection object
    private Connection getMC(){
        Connection result = null;
        //Traversing objects in the connection pool
        for (Connection conn : pool){
            MyConnection mc = (MyConnection) conn;
            if(!mc.isUsed()) {// indicates that the connection is available
                synchronized (ConnectionPool.class){
                    if (!mc.isUsed()){
                        mc.setUsed(true);
                        result = mc;
                    }
                }
                break;
            }
        }
        return result;
    }

    //This method is to obtain the connection object and adds a waiting mechanism
    public Connection getConnection() {
        Connection result = this.getMC();
        int count = 0;// Record the number of cycles
        while (result == null && count < waitTime*10){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = this.getMC();
            count++;
        }
        if (result == null){
            throw new SystemBusyException("the current system is busy, please try again later");
        }
        return result;
    }
}
