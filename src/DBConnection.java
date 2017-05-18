
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Connection with database
 */

/**
 *
 * @author Hien Long
 */
public class DBConnection {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/timetracker";
    private static String user = "root";
    private static String pass = "1490";
    static Connection connection = null;
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }
    
    public static void closeConnection(ResultSet rs, Statement st, Connection con){
        try{
            if(rs != null){
                rs.close();
                rs = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            if(st != null){
                st.close();
                st = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            if(con != null){
                con.close();
                con = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
