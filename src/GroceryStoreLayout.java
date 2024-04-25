import com.mysql.cj.xdevapi.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

public class GroceryStoreLayout extends JFrame{
    private JPanel frame;
    private JPanel Top;
    private JPanel Left;
    private JPanel Right;
    private JLabel Title;
    private JTable Table_Data;
    private JScrollPane scroll_pane;
    private JComboBox<String> table_selector;
    private JComboBox<String> ID_selector;
    private JButton createButton;
    private JButton removeButton;
    private JButton updateButton;
    private JPanel tableSelect;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JLabel selectTableText;
    private JLabel selectIDText;
    private JLabel[] labels = {label1, label2, label3, label4, label5, label6, label7, label8, label9, label10};
    private JTextField[] textFields= {textField1, textField2, textField3, textField4, textField5, textField6, textField7, textField8, textField9, textField10};
    private JDialog dialog = new JDialog(this, "");;

    public Connection con;
    public ResultSet result;
    public Statement st;

    public String selectedID;
    public int numColumns;
    DefaultTableModel tableModel;
    Dictionary<String, Integer> dataTypes = new Hashtable<>(); // data types of getColumnType returned numbers


    GroceryStoreLayout(){
        setContentPane(frame);
        makeConnection();

        queryDb(con, "select * from Customer"); // initial query to create table
        generateTable(); // generate table
        Table_Data.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setID_selectorItems(); // set ID selector box

        setTitle("Grocery Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        table_selector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryDb(con, "select * from " + table_selector.getSelectedItem());
                generateTable();
                try {
                    numColumns = result.getMetaData().getColumnCount();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                setID_selectorItems();
                hideLabelsAndTextFields();
                for(int i = 0; i < numColumns; i++){ // reset the text fields to empty
                    textFields[i].setText("");
                }
            }
        });

        ID_selector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(Objects.equals(ID_selector.getSelectedItem().toString(), "All")){
                        queryDb(con, "select * from " + table_selector.getSelectedItem().toString());
                        generateTable();
                    }
                    else{
                        queryDb(con, "select * from " + table_selector.getSelectedItem().toString() + " where " + Table_Data.getColumnName(0) + " = " + ID_selector.getSelectedItem().toString());
                        generateTable();
                        setTextFieldtext();
                    }
                } catch (NullPointerException npe) { // will return an error when selecting the table
                    System.out.println(' ');
                }
            }
        });

        Table_Data.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = Table_Data.getSelectedRow();
                for (int i = 0; i < numColumns; i++) {
                    textFields[i].setText(tableModel.getValueAt(selectedRow, i).toString());
                }

            }
        });

        this.addWindowListener(new WindowAdapter() { // close the connection when the window is closed
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createButton();
                generateTable();
            }
        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton();
                generateTable();
            }
        });


        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButton();
                generateTable();
            }
        });
    }

    public void generateTable(){
        try {
            numColumns = result.getMetaData().getColumnCount();
            tableModel = (DefaultTableModel) Table_Data.getModel();
            tableModel.setRowCount(0);

            String[] colNames = new String[numColumns];
            for(int i = 0; i < numColumns; i++){
                colNames[i] = result.getMetaData().getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);

            String[] tempArr = new String[numColumns];
            while(result.next()) { // add elements to tempArr
                for (int i = 1; i <= numColumns; i++) {
                    tempArr[i - 1] = result.getString(i);
                }
                tableModel.addRow(tempArr);
                tempArr = new String[numColumns];
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeConnection(){
        try {
            String url = "jdbc:mysql://localhost:3306/DB_Project";
            String username = "root";
            String password = "dkvUs83Sk#4";

            con = DriverManager.getConnection(url, username, password); // connect to the DB
            st = con.createStatement();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void queryDb(Connection con, String query){
        try {
            Statement st = con.createStatement();
            result = st.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setID_selectorItems(){
        ID_selector.removeAllItems();
        ID_selector.addItem("All");
        queryDb(con, "select " + Table_Data.getColumnName(0) + " from " + table_selector.getSelectedItem().toString());
        try {
            while(result.next()){
                ID_selector.addItem(result.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void hideLabelsAndTextFields(){
        for(int i = 0; i < 10; i++){
            if(i < numColumns){
                labels[i].setVisible(true);
                textFields[i].setVisible(true);
                labels[i].setText(Table_Data.getColumnName(i));
            }
            else{
                labels[i].setVisible(false);
                textFields[i].setVisible(false);
            }
        }
    }

    public void setTextFieldtext(){ // set the text fields when an ID is selected
        try {
            int i = 0;
            while(result.next()){
                textFields[i].setText(result.getString(1));
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createButton(){
        try {
            // get table name
            //get column names
            StringBuilder statement = new StringBuilder("insert into $tablename values (");
            PreparedStatement newPreparedStatement;
            int columnType;
            for(int i = 0; i < numColumns; i++){ // add insert options for every column needed in table
                if(i == numColumns - 1){ statement.append("?);"); }
                else{ statement.append("?, "); }
            }

            statement.replace(12, 22, table_selector.getSelectedItem().toString());
            newPreparedStatement = con.prepareStatement(statement.toString());
            queryDb(con, "select * from " + table_selector.getSelectedItem().toString());

            for(int i = 1; i <= numColumns; i++){
                columnType = result.getMetaData().getColumnType(i);
                // get data type of table element
                if(columnType == 1 || columnType == 12){ // char or varchar
                    newPreparedStatement.setString(i, textFields[i - 1].getText());
                }
                else if(columnType == 4){ // int
                    newPreparedStatement.setInt(i, Integer.parseInt(textFields[i - 1].getText()));
                }
                else if(columnType == 6){ // float
                    newPreparedStatement.setFloat(i, Float.parseFloat(textFields[i - 1].getText()));
                }
            }
            int rows = newPreparedStatement.executeUpdate();
            if(rows > 0){
                System.out.println("Add success!");
            }

        } catch (SQLException e) {
//            throw new RuntimeException(e);
            // show popup window with correct error
            errorMessageDialogBox("Too long", e);
        }
    }

    public void updateButton(){
        try {
            StringBuilder statement = new StringBuilder("update $tablename set ");
            PreparedStatement newPreparedStatement;
            int columnType;
            int rows;
            String colName;
            for(int i = 0; i < numColumns; i++){ // add insert options for every column needed in table
                colName = tableModel.getColumnName(i);
                if(i == numColumns - 1){ statement.append(colName).append(" = ?"); }
                else{ statement.append(colName).append(" = ?, "); }
            }

            statement.append("where ").append(tableModel.getColumnName(0)).append(" = ").append(tableModel.getValueAt(Table_Data.getSelectedRow(), 0)); // only update the selected row
            statement.replace(7, 17, table_selector.getSelectedItem().toString());
            newPreparedStatement = con.prepareStatement(statement.toString());
            queryDb(con, "select * from " + table_selector.getSelectedItem().toString()); // to get the column types

            for(int i = 1; i <= numColumns; i++){
                columnType = result.getMetaData().getColumnType(i);
                // get data type of table element
                if(columnType == 1 || columnType == 12){ // char or varchar
                    newPreparedStatement.setString(i, textFields[i - 1].getText());
                }
                else if(columnType == 4){ // int
                    newPreparedStatement.setInt(i, Integer.parseInt(textFields[i - 1].getText()));
                }
                else if(columnType == 6){ // float
                    newPreparedStatement.setFloat(i, Float.parseFloat(textFields[i - 1].getText()));
                }
            }
            rows = newPreparedStatement.executeUpdate();
            if(rows > 0){
                System.out.println("Update success!");
            }
            dialogBox("Updated Successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteButton(){
        StringBuilder statement = new StringBuilder("delete from $tablename where ");
        PreparedStatement newPreparedStatement;

        statement.replace(12, 22, table_selector.getSelectedItem().toString());
        statement.append(tableModel.getColumnName(0)).append(" = ").append(tableModel.getValueAt(Table_Data.getSelectedRow(), 0)); // only delete the selected row
        try {
            newPreparedStatement = con.prepareStatement(statement.toString());
            newPreparedStatement.executeUpdate();
            dialogBox("Deleted Successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dialogBox(String message){
        dialog.setTitle("Success");
        JLabel newLabel = new JLabel();
        newLabel.setText(message);
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(this);
        dialog.add(newLabel);
        dialog.setVisible(true);
    }

    public void errorMessageDialogBox(String message, SQLException e){

    }

    public static void main(String[] args) {
        new GroceryStoreLayout();
    }
}
