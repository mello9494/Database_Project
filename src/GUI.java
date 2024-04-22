import org.w3c.dom.css.RGBColor;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.SQLException;

class GUI {
//    JFrame frame;
//    JLabel label;
//    JPanel panel;
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
//    int width = (3 * size.width) / 5;
//    int height = size.height - 100;

    int width = 800;
    int height = 700;
//    GUI(){
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(width, height);
//        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10 ,0));
//
//        JPanel panel = panel1();
//        JPanel panel2 = panel2();
//        JPanel panel3 = panel3();
//        JPanel panel4 = panel4();
//
//        JTable table = new JTable();
//
//        JLabel label = new JLabel("Grocery Store");
//        label.setFont(new Font("Arial", Font.PLAIN, 25));
//
//        TitledBorder title_border = new TitledBorder("Add Books");
//        panel4.add(label, BorderLayout.WEST);
//        panel.setBorder(title_border);
//
//        // table needs a model before being created
//        table.setPreferredSize(new Dimension(panel2.getWidth() - 10, panel2.getHeight() - 10));
//        table.setBackground(Color.cyan);
//        table.setBorder(new LineBorder(Color.BLACK, 2));
//
//        panel2.add(table);
//
//        JTextField textField1 = new JTextField();
//        textField1.setPreferredSize(new Dimension(140, 20));
//
//        panel.add(new JLabel("Customer"));
//        panel.add(textField1);
//        panel.add(new JLabel("Employee"));
////        panel.add(textField1);
//
//
//        frame.add(panel4);
//        frame.add(panel);
//        frame.add(panel2);
//        frame.add(panel3);
//
//
//        frame.setResizable(false);
//        frame.setVisible(true);
//    }

    GUI() throws SQLException {
        DB testDB = new DB();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLayout(null);

        // top panel
        JPanel panel1 = new JPanel();
        JLabel title = new JLabel("Grocery Store", JLabel.CENTER);

        panel1.setBounds(0, 0, width, 100);
        panel1.setBackground(new Color(255, 100, 0));
        panel1.setLayout(new GridLayout(1, 1));

        title.setFont(new Font("Arial", Font.PLAIN, 40));

        panel1.add(title);

        // left panel
        JPanel panel2 = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel temp1 = new JLabel("temp1");
        JLabel temp2 = new JLabel("temp2");
        JTextField textField = new JTextField();

        panel2.setBounds(0, 100, width / 2, height - 128);
        panel2.setBackground(new Color(157, 183, 173));
        panel2.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 2), testDB.getTableName().toUpperCase())); // retrieves the current table name
        panel2.setLayout(new GridBagLayout());

        temp1.setBorder(new LineBorder(Color.BLACK, 2));
        temp2.setBorder(new LineBorder(Color.BLACK, 2));

        textField.setPreferredSize(new Dimension(200, 20));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 1); // space around each element
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel2.add(temp1, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel2.add(textField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel2.add(temp2, gbc);

        // right panel
        JPanel panel3 = new JPanel();
        panel3.setBounds(width / 2, 100, width / 2, height - 128);
        panel3.setBackground(new Color(194, 159, 231));




        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);

        frame.setVisible(true);
    }
    JPanel panel1(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension((width / 2) - 10, (3 * height) / 5));
        panel.setMinimumSize(new Dimension((width / 2) - 60, (3 * height) / 5 - 100));
//        panel.setBackground(new Color(255, 199, 206));
        panel.setLayout(new GridLayout(3, 2, 30, 10));
//        panel.setLayout(new FlowLayout());

        return panel;
    }

    JPanel panel2(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension((width / 2) - 10, (3 * height) / 5));
        panel.setMinimumSize(new Dimension((width / 2) - 60, (3 * height) / 5 - 100)); // not working
        panel.setBackground(new Color(138, 6, 23));

        return panel;
    }

    JPanel panel3(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width - 10, height / 5));
        panel.setMinimumSize(new Dimension(width - 120, height / 5));
        panel.setBackground(new Color(16, 100, 39));

        return panel;
    }

    JPanel panel4(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width - 10, height / 5));
        panel.setMinimumSize(new Dimension(width - 120, height / 5));
        panel.setBackground(new Color(102, 117, 224));
        panel.setLayout(new BorderLayout(10, 0));

        return panel;
    }
}
