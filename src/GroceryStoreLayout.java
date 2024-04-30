import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
    private JDialog dialog = new JDialog(this, "");
    JLabel error = new JLabel();

    public Connection con;
    public ResultSet result;
    public Statement st;

    public int numColumns;
    public DefaultTableModel tableModel;

    GroceryStoreLayout(String url, String username, String password){
        makeConnection(url, username, password);

        queryDb(con, "select * from Customer"); // initial query to create table
        generateTable(); // generate table
        Table_Data.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setID_selectorItems(); // set ID selector box

        dialog.setSize(400, 100); // error message dialog box
        dialog.setLocationRelativeTo(this);

        setContentPane(frame);
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
                    System.out.print(' ');
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
                queryDb(con, "select * from " + table_selector.getSelectedItem());
                generateTable();
            }
        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton();
                queryDb(con, "select * from " + table_selector.getSelectedItem());
                generateTable();
            }
        });


        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButton();
                queryDb(con, "select * from " + table_selector.getSelectedItem());
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

    public void makeConnection(String url, String username, String password){
        try {
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
            // get column names
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
                    try { // make sure the input is only numbers
                        int temp = Integer.parseInt(textFields[i - 1].getText());
                        newPreparedStatement.setInt(i, temp);
                    } catch (NumberFormatException nfe) {
                        dialog.setTitle("Numbers only");
                        error.setText("Only numbers allowed for element " + result.getMetaData().getColumnName(i - 1));
                        dialog.add(error);
                        dialog.setVisible(true);
                        return;
                    }
                }
                else if(columnType == 7){ // float
                    try { // make sure the input is only numbers
                        float temp = Float.parseFloat(textFields[i - 1].getText());
                        newPreparedStatement.setFloat(i, temp);
                    } catch (NumberFormatException nfe) {
                        dialog.setTitle("Numbers only");
                        error.setText("Only numbers allowed for element " + result.getMetaData().getColumnName(i - 1));
                        dialog.add(error);
                        dialog.setVisible(true);
                        return;
                    }
                }
            }
            newPreparedStatement.executeUpdate();
            dialog.setTitle("Success");
            error.setText("Created Successfully");
            dialog.add(error);
            dialog.setVisible(true);
        } catch (SQLException e) {
            SQLError(e);
        }
    }

    public void updateButton(){
        try {
            StringBuilder statement = new StringBuilder("update $tablename set ");
            PreparedStatement newPreparedStatement;
            int columnType;
            String colName;
            for(int i = 0; i < numColumns; i++){ // add insert options for every column needed in table
                colName = tableModel.getColumnName(i);
                if(i == numColumns - 1){ statement.append(colName).append(" = ?"); }
                else{ statement.append(colName).append(" = ?, "); }
            }

            statement.append(" where ").append(tableModel.getColumnName(0)).append(" = ").append(tableModel.getValueAt(Table_Data.getSelectedRow(), 0)).append(";"); // only update the selected row
            statement.replace(7, 17, table_selector.getSelectedItem().toString());
            newPreparedStatement = con.prepareStatement(statement.toString());
            queryDb(con, "select * from " + table_selector.getSelectedItem().toString()); // to get the column types

            for(int i = 1; i <= numColumns; i++){ // replace the "?" with the proper values
                columnType = result.getMetaData().getColumnType(i);
                // get data type of table element
                if(columnType == 1 || columnType == 12){ // char or varchar
                    newPreparedStatement.setString(i, textFields[i - 1].getText());
                }
                else if(columnType == 4) { // int
                    try { // make sure the input is only numbers
                        int temp = Integer.parseInt(textFields[i - 1].getText());
                        newPreparedStatement.setInt(i, temp);
                    } catch (NumberFormatException nfe) {
                        dialog.setTitle("Numbers only");
                        error.setText("Only numbers allowed for element " + result.getMetaData().getColumnName(i - 1));
                        System.out.println(result.getMetaData().getColumnName(i));
                        dialog.add(error);
                        dialog.setVisible(true);
                        return;
                    }
                }
                else if(columnType == 7){ // float
                    try { // make sure the input is only numbers
                        float temp = Float.parseFloat(textFields[i - 1].getText());
                        newPreparedStatement.setFloat(i, temp);
                    } catch (NumberFormatException nfe) {
                        dialog.setTitle("Numbers only");
                        error.setText("Only numbers allowed for element " + result.getMetaData().getColumnName(i - 1));
                        dialog.add(error);
                        dialog.setVisible(true);
                        return;
                    }
                }
            }
            newPreparedStatement.executeUpdate();
            dialog.setTitle("Success");
            error.setText("Updated Successfully");
            dialog.add(error);
            dialog.setVisible(true);
        } catch (SQLException e) {
            SQLError(e);
        } catch (ArrayIndexOutOfBoundsException aie) {
            dialog.setTitle("Error");
            error.setText("Please select a row to update.");
            dialog.add(error);
            dialog.setVisible(true);
        }
    }

    public void deleteButton(){
        StringBuilder statement = new StringBuilder("delete from $tablename where ");
        PreparedStatement newPreparedStatement;

        try {
            statement.replace(12, 22, table_selector.getSelectedItem().toString());
            statement.append(tableModel.getColumnName(0)).append(" = ").append(tableModel.getValueAt(Table_Data.getSelectedRow(), 0)); // only delete the selected row
            newPreparedStatement = con.prepareStatement(statement.toString());
            newPreparedStatement.executeUpdate();
            dialog.setTitle("Success");
            error.setText("Deleted successfully.");
            dialog.add(error);
            dialog.setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException a) {
            dialog.setTitle("Error");
            error.setText("Please selecta row to delete.");
            dialog.add(error);
            dialog.setVisible(true);
        }
    }

    public void SQLError(SQLException e){
        // show popup window with correct error
        String errorColumn = ""; // name of the column throwing the error
        String relatedTable = ""; // name of the table the foreign key is referencing
        switch (e.getErrorCode()) {
            case 0: // missing data
                dialog.setTitle("Null entry");
                error.setText("Entries cannot be null.");
                break;
            case 1062: // duplicate primary key
                dialog.setTitle("Duplicate primary key");
                error.setText("ID must be unique.");
                break;
            case 1452: // foreign key error
                for (int i = 0; i < numColumns; i++) {
                    if (e.getLocalizedMessage().toLowerCase().contains(tableModel.getColumnName(i).toLowerCase())) {
                        errorColumn = tableModel.getColumnName(i);
                        break;
                    }
                }
                for (int i = 0; i < 7; i++) {
                    if (errorColumn.toLowerCase().contains(table_selector.getItemAt(i).toLowerCase())) {
                        relatedTable = table_selector.getItemAt(i);
                        break;
                    }
                }
                dialog.setTitle("Foreign key constraint");
                error.setText("No ID in " + relatedTable + " matches the data in " + errorColumn);
                break;
            case 1406: // input length too long
                for (int i = 0; i < numColumns; i++) {
                    if (e.getLocalizedMessage().toLowerCase().contains(tableModel.getColumnName(i).toLowerCase())) {
                        errorColumn = tableModel.getColumnName(i);
                        break;
                    }
                }
                dialog.setTitle("Input too long");
                error.setText("Data in " + errorColumn + " is too long.");
        }
        dialog.add(error);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new GroceryStoreLayout(args[0], args[1], args[2]);
    }
}
