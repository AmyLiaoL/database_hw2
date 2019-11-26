import java.io.*;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;

public class AS2 {
    public static void main(String args[]) throws SQLException, IOException {
        String username, password;
        username = "";			// Your Oracle Account ID
        password = ""; 		// Password of Oracle Account

        // Connection
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        OracleConnection conn =
                (OracleConnection)DriverManager.getConnection(
                        "jdbc:oracle:thin:@studora.comp.polyu.edu.hk:1521:dbms",
                        username, password);
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT COURSE_ID, COURSE_TITLE, STAFF_NAME, SECTION FROM COURSES");
        while (rset.next())
        {
            System.out.println(rset.getString(1)
                    + " " + rset.getString(2)
                    + " " + rset.getString(3)
                    + " " + rset.getString(4));

        }
        System.out.println();
        conn.close();
    }
    }

