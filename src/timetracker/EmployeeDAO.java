package timetracker;


import java.util.Date;
import java.sql.*;
import java.util.Calendar;

/*
 * Employee Data Access Object
 */
/**
 *
 * @author Hien Long
 */
public class EmployeeDAO {

    private int id;
    String name;
    Connection connection;
    /*
    SQL queries
     */
    String insertEmployee = "INSERT INTO employee(id, name) VALUES (?,?)";
    String insertTime = "INSERT INTO clock(id, punchType) VALUES (?,?)";

    public EmployeeDAO() {

    }

    /*
    Add new employee
     */
    public void addNewEmployee(int id, String name) throws ClassNotFoundException, SQLException {
        Employee emp = new Employee(id, name);
        connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(insertEmployee);
        statement.setInt(1, emp.getId());
        statement.setString(2, emp.getName());
        statement.executeUpdate();
        DBConnection.closeConnection(null, statement, connection);

    }

    /*
    Add a punch in, punch out time for specified employee ID
    I use NOW() function in mysql. NOW() returns date/time formatted 2014-11-22 12:45:34
     */
    public void addTime(int id) throws ClassNotFoundException, SQLException {

        connection = DBConnection.getConnection();
        String insertTime = "INSERT INTO clock (id, punchType) VALUES(?,?)";
        boolean status = false;
        status = checkInStatus(id);
        System.out.println(status);
        // if status return true, that means he already checked in. Add punch out time
        // Otherwise ask employee to add punch in time
        if (status) {
            addTimeHelper(id, "O");
        } else {
            addTimeHelper(id, "I");
        }
    }

    public void addTimeHelper(int id, String punchType) throws SQLException, ClassNotFoundException {
    
        connection = DBConnection.getConnection();
        PreparedStatement stm = connection.prepareStatement(insertTime);
        stm.setInt(1, id);
        stm.setString(2, punchType);
        int result = stm.executeUpdate();
        if(result != 0){
        //    DBConnection.commit();
        } else {
            DBConnection.rollback();
        }
        DBConnection.closeConnection(null, stm, connection);
    }

    /*
    Checking if the employee with specified ID alrready check or not.
    Return true if already check in, false if he didn't.
     */
    public boolean checkInStatus(int id) throws SQLException, ClassNotFoundException {
        boolean status = false;
        String checkStatus = "SELECT * FROM clock WHERE id =" + id + " OR punchType= " + "'I'";
        connection = DBConnection.getConnection();
        
        PreparedStatement st = connection.prepareStatement(checkStatus);
        ResultSet rs = st.executeQuery();
        System.out.println(rs);
        if (rs != null) {
            status = true;
        } else {
            status = false;
        }
        return status;
    }
    
    public void printIndividualReport(int id){
    //    String printStatement = "SELECT * FROM "
    }
    
    
    
}
