package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Collin on 2-7-2017.
 */
public class Database {
    private static Connection connection = null;

    public static void open() {
        if (connection!=null){
            throw new Error("Databse is al verbonden");
        }
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/hitdossier?user=root");
        } catch (SQLException ex){
            System.out.println("" + ex.getMessage());
            System.out.println("" + ex.getMessage());
            System.out.println("" + ex.getMessage());
            throw new Error("fout bij verbinden MySQL");
        }
    }

    public static Connection getConnection(){
        if(connection==null){
            throw new Error("database is gesloten");
        }

        return connection;
    }

    public static void  Close() {
        if (connection!=null){
            try{
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
            connection=null;
        }
    }
}
