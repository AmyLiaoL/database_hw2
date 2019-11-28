import java.io.*;
import java.sql.*;
import java.util.Scanner;

import oracle.jdbc.driver.*;
import oracle.sql.*;

public class AS2 {

    public static void main(String args[]) throws SQLException, IOException {
        String username, password;
        username = "\"18080427d\"";			// Your Oracle Account ID
        password = "kfdnigpa"; 		// Password of Oracle Account

        // Connection
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        OracleConnection conn =
                (OracleConnection)DriverManager.getConnection(
                        "jdbc:oracle:thin:@studora.comp.polyu.edu.hk:1521:dbms",
                        username, password);
        Statement stmt = conn.createStatement();

        studentchoose(stmt);
        conn.close();
    }

    public static void studentchoose(Statement stmt) throws SQLException {

        System.out.println(
                "Hello, what do you want to do?\n"
                +"1. see all the courses\n"
                +"2. see the courses I've registered\n"
                +"3. register course\n"
                +"4. modify my personal information\n\n"
                +"Please enter 1 or 2 or 3 or 4 AND your student ID"
        );
        Scanner obj = new Scanner(System.in);
        int userchoose = obj.nextInt();
        String stuID = obj.nextLine();
        switch (userchoose) {
                case 1:
                    allcourses(stuID, stmt);
                    break;
                case 2:
                    regcourses(stuID, stmt);
                    break;
                case 3:
                    register(stuID, stmt);
                    break;
                case 4:
                    modify(stuID, stmt);
                    break;
            }

        return ;
    }

    public static void allcourses(String ID, Statement stmt) throws SQLException {
        ResultSet rset = stmt.executeQuery("SELECT DISTINCT COURSE_ID, COURSE_TITLE, STAFF_NAME, SECTION FROM COURSES");
        while (rset.next())
        {
            System.out.println(rset.getString(1)
                    + " " + rset.getString(2)
                    + " " + rset.getString(3)
                    + " " + rset.getString(4));
        }
        return ;
    }
    public static void regcourses(String ID, Statement stmt) throws SQLException {
        ResultSet rset = stmt.executeQuery("SELECT DISTINCT COURSE_TITLE FROM COURSES WHERE EXISTS (SELECT COURSE_ID FROM ENROLLMENT WHERE STUDENT_ID = '"+ ID.trim()+"')");
        System.out.println("\nYour courses: ");
        while (rset.next()){
            System.out.println(rset.getString("COURSE_TITLE"));
        }
        return ;
    }
    public static void register(String ID, Statement stmt) throws SQLException {
        System.out.println("what courses do you want?");
        Scanner obj = new Scanner(System.in);
        String course = obj.nextLine();
        String selecourID = "SELECT COURSE_ID FROM COURSES WHERE COURSE_TITLE = '"+ course.trim()+"'";
        ResultSet courseID = stmt.executeQuery(selecourID);
        courseID.next();
        String StrcourID = courseID.getString("COURSE_ID");
        System.out.println(StrcourID);

        String seletime = "SELECT TO_CHAR (SYSDATE, 'YYYY/MM/DD') NOW FROM DUAL";
        ResultSet time = stmt.executeQuery(seletime);
        time.next();
        String Strtime = time.getString("NOW");
        //String insert = "INSERT INTO ENROLLMENT (STUDENT_ID, COURSE_ID, REG_DATE, GRADE) VALUES ('"+ID.trim()+"', '"+StrcourID.trim()+"', TO_DATE('"+ Strtime.trim() +"','YYYY/MM/DD'), NULL)";
        //stmt.executeUpdate(insert);
        return ;
    }
    public static void modify(String ID, Statement stmt) throws SQLException {
        System.out.println("What do you want to modify?\n+" +
                        "Please input name, department, or address\n");
        Scanner obj = new Scanner(System.in);
        String input = obj.nextLine();
        switch (input){
            case ("name"):
                System.out.println("What's your new name?");
                String name = obj.nextLine();
                String query1 = "UPDATE STUDENTS SET STUDENT_NAME = '"+name.trim()+"' WHERE STUDENT_ID = '"+ID.trim()+"'";
                stmt.executeUpdate(query1);
                break;
            case ("department"):
                System.out.println("What's your new department?");
                String dep = obj.nextLine();
                String query2 = "UPDATE STUDENTS SET DEPARTMENT = '"+dep.trim()+"' WHERE STUDENT_ID = '"+ID.trim()+"'";
                stmt.executeUpdate(query2);
                break;
            case ("address"):
                System.out.println("What's your new address?");
                String add = obj.nextLine();
                String query3 = "UPDATE STUDENTS SET ADDRESS = '"+add.trim()+"' WHERE STUDENT_ID = '"+ID.trim()+"'";
                stmt.executeUpdate(query3);
                break;
        }
    }
}

