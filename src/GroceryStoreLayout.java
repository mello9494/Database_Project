import com.mysql.cj.xdevapi.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Connection con;
    public ResultSet result;
    public Statement st;

    public String selectedID;
    public int numColumns;
    DefaultTableModel tableModel;

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


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

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

    public int getRowCount(String table){
        try {
            queryDb(con, "select count(*) from " + table);
            return Integer.parseInt(result.getString(1));
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

    public void setTextFieldtext(){
        try {
            int i = 0;
            while(result.next()){
                textFields[i].setText(result.getString(1)); ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new GroceryStoreLayout();
    }
}
