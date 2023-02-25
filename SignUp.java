package BankManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Random;

public class SignUp extends JFrame implements ActionListener {

    JPanel	mainPanel;
    JPanel  centerPanel;
    JPanel  bottomPanel;
    JPanel  upperPanel;
    JButton btnSubmit ;
    JButton btnCancel;
    JRadioButton rb1, rb2, rb3;
    ButtonGroup G1;

    JLabel lblAcctNo = new JLabel("Account No :");
    JLabel lblPIN = new JLabel("PIN :");
    JLabel lblName = new JLabel("Name :");
    JLabel lblbYear = new JLabel("Birth Year :");
    JLabel lblBlank1 = new JLabel("    ");
    JLabel lblBlank2 = new JLabel("    ");
    JLabel lblGender = new JLabel("Gender :");
    JLabel lblAddress = new JLabel("Address :");
    JLabel lblCtyTwn = new JLabel("City/Town :");
    JLabel lblState = new JLabel("State :");
    JLabel lblPhone = new JLabel("Phone :");
    JLabel lblLeft;
    JLabel lblRight;
    JTextField[] fields;

    public SignUp() {
        super ("Sukkur IBA Bank");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        rb1 = new JRadioButton("Male");
        rb2 = new JRadioButton("Female");
        rb3 = new JRadioButton("Other");
        G1 = new ButtonGroup();
        G1.add(rb1);
        G1.add(rb2);
        G1.add(rb3);


        btnSubmit = new JButton("Submit");
        btnCancel = new JButton("Cancel");
        lblLeft = new JLabel("               ");
        lblRight = new JLabel("               ");
        fields = new JTextField[8];

        mainPanel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new GridLayout(12,2,5,5));

        upperPanel = new JPanel(new GridLayout(4,1,5,5));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


        lblAcctNo.setSize(5,4);
        lblPIN.setSize(5,4);
        lblName.setSize(5,4);
        lblbYear.setSize(5, 4);
        lblGender.setSize(5, 4);
        lblAddress.setSize(5,4);
        lblCtyTwn.setSize(5,4);
        lblState.setSize(5,4);
        lblPhone.setSize(5,4);

        for (int i = 0; i < 8; i++) {
            fields[i] = new JTextField("",15);
            fields[i].setSize(5,4);
            fields[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        fields[0].setEditable(false);
        fields[7].setEditable(false);

        centerPanel.add(lblAcctNo);
        centerPanel.add(fields[0]);
        centerPanel.add(lblName);
        centerPanel.add(fields[1]);
        centerPanel.add(lblGender);
        centerPanel.add(rb1);
        centerPanel.add(lblBlank1);
        centerPanel.add(rb2);
        centerPanel.add(lblBlank2);
        centerPanel.add(rb3);
        centerPanel.add(lblbYear);
        centerPanel.add(fields[2]);
        centerPanel.add(lblAddress);
        centerPanel.add(fields[3]);
        centerPanel.add(lblCtyTwn);
        centerPanel.add(fields[4]);
        centerPanel.add(lblState);
        centerPanel.add(fields[5]);
        centerPanel.add(lblPhone);
        centerPanel.add(fields[6]);
        centerPanel.add(lblPIN);
        centerPanel.add(fields[7]);

        // setting app icon:
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("SIBA_Logo.jpg")));

        mainPanel.add(centerPanel,BorderLayout.CENTER);
        mainPanel.add(lblLeft,BorderLayout.EAST);
        mainPanel.add(lblRight,BorderLayout.WEST);
        upperPanel.add(new JLabel(" "));
        JLabel lbl1 = new JLabel("Create Account",SwingConstants.CENTER);
        lbl1.setFont(new Font("",Font.BOLD,12));
        lbl1.setSize(5,4);
        upperPanel.add(lbl1);
        upperPanel.add(new JLabel(" ",SwingConstants.CENTER));
        upperPanel.add(new JLabel(" ",SwingConstants.CENTER));
        mainPanel.add(upperPanel,BorderLayout.NORTH);

        btnCancel.addActionListener(this);
        btnSubmit.addActionListener(this);
        bottomPanel.add(btnSubmit);
        bottomPanel.add(btnCancel);

        mainPanel.add(bottomPanel,BorderLayout.SOUTH);

        setContentPane (mainPanel);

        setBounds(450,100,500,500);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        DBConn con = null;

        try {
            String gender = null;

            if(rb1.isSelected()) {
                gender = "Male";
            }
            else if(rb2.isSelected()) {
                gender = "Female";
            }
            else if(rb3.isSelected()) {
                gender = "Other";
            }

            String a = fields[1].getText();
            String b = fields[2].getText();
            String c = fields[3].getText();
            String d = fields[4].getText();
            String e = fields[5].getText();
            String f = fields[6].getText();

            if (src == btnCancel) {
                new LogIn().setVisible(true);
                setVisible(false);
            }
            else if (src == btnSubmit) {
                if (a == null || b == null || c == null || d == null || e == null || f == null || gender == null) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields!");
                }
                else {
                    //Generating AccountNo:
                    String start = "10000000";
                    Random rand1 = new Random();
                    int num1 = (rand1.nextInt(89999999) + 10000000);
                    int num1_1 = Math.abs(num1);
                    String end = Integer.toString(num1_1);
                    String accNo = start + end;

                    //Generating PIN:
                    rand1 = new Random();
                    int num2 = (rand1.nextInt(8999) + 1000);
                    int PIN = Math.abs(num2);

                    con = new DBConn();
                    String q1 = "INSERT INTO ClientInfo VALUES ('" + accNo + "', '" + a + "', '" + gender + "', " + Integer.parseInt(b) + ", '" + c + "', '" + d + "', '" + e + "', '" + f + "', " + PIN + ")";
                    String q2 = "INSERT INTO ClientAccStatus VALUES ('" + accNo + "', " + 0 + ", " + 0 + ")";
                    con.stmt.executeUpdate(q1);
                    con.stmt.executeUpdate(q2);
                    JOptionPane.showMessageDialog(null, "Your Account Number: " + accNo + "\nPIN: " + PIN);
                    new LogIn().setVisible(true);
                    setVisible(false);
                }
            }
        }
        catch (NumberFormatException nfe) {
            System.out.println("Please fill the complete form.");
        }
        catch (Exception e) {
            System.out.println("Error Occurred: " + e.getMessage());
        }
        finally {
            if(con != null) {
                try {
                    con.close();
                }
                catch (SQLException sqle) {
                    System.out.println("Error closing connection.");
                }
            }

        }
    }

}
