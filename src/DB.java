import java.sql.*;

public class DB{
    Statement st;
    ResultSet result;
    ResultSetMetaData meta;
    Connection con;
    DB() throws SQLException {
        String testQuery = "SELECT * FROM Scores";
        // replace this info with the info for your DB
        String url = "jdbc:mysql://localhost:3306/testdatabase";
        String username = "root";
        String password = "dkvUs83Sk#4";
        //

        con = DriverManager.getConnection(url, username, password);


        st = con.createStatement();
        result = st.executeQuery(testQuery);
        result.next();

        meta = result.getMetaData();
        int columnCount = meta.getColumnCount(); // get the amount of columns from the query

//        System.out.println(colName);
//        System.out.println(columnCount);
//        System.out.println(result.getString("userId")); // get the first item from the row "userId"

        // prints all data from result
//        while(result.next()){
//            for(int i = 1; i <= columnCount; i++){
//                System.out.print(result.getString(i) + "\t");
//            }
//            System.out.println();
//        }

        con.close();
    }

    public String getTableName() throws SQLException{
        return meta.getTableName(1);
    }
}
