package timetracker;

/**
 * Employee Data Access Object
 *
 * @author Hien Long
 */
import java.util.Date;
import java.sql.*;
import java.util.Calendar;

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
/* REVISION: Originally, I was planning to create table clock with (id, punchTime, punchType). 
   However, due to the complexion of calculating worked hours, and display report,
    I decided to change my schema to (id, punchIn, punchOut). These implementions 
    of methods below (addTime, checkInStatus, addTimeHelper) are still the old implementation
    that need to be revised according to the new schema
    /*
    Add a punch in, punch out time for specified employee ID
    I use NOW() function in mysql. NOW() returns date/time formatted 2014-11-22 12:45:34
     * @param id
     * @throws ClassNotFoundException
     * @throws SQLException 
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
        if (result != 0) {
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
        String checkStatus = "SELECT * FROM clock WHERE id =" + id + "WHERE TRUNC(punchIn) = timestamp.now() ";
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

    public void printIndividualReport(int id) throws ClassNotFoundException, SQLException {

        
        String printStatement = "SELECT * FROM clock WHERE id =" + id +" INNER JOIN employee ON clock.id = employee.id ";
        connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(printStatement);
        ResultSet rs = statement.executeQuery();
        if (rs != null) {
            System.out.println("Employee ID: " + rs.getInt("id")+ "  Employee Name: " + rs.getString("name"));
            while (rs.next()) {
            //   if(rs.getDate(2) == sql)
                System.out.println(rs.getTime(2));
            }
        }

    }

    /**
     * Calculate the worked hours everyday of each employee
     */
    public long calculateWorkedHours(java.sql.Timestamp t1, java.sql.Timestamp t2) {
        long miliseconds1 = t1.getTime();
        long miliseconds2 = t2.getTime();
        long diff = miliseconds2 - miliseconds1;
        long diffMinutes = diff / (60 * 1000);
        return diffMinutes;
    }
    /**
     * Select the date associated with ID, and check In
     */

}
