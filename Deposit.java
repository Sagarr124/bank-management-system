package BankManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deposit extends JFrame implements ActionListener {

    JPanel	mainPanel;
    JPanel  centerPanel;
    JPanel  leftPanel;
    JPanel  rightPanel;
    JPanel  bottomPanel;

    JLabel lbl2 = new JLabel("Name             :");
    JLabel lbl3 = new JLabel("Account No       :");
    JLabel lbl4 = new JLabel("Balance          :");
    JLabel lbl5 = new JLabel("Deposit          :");

    JTextField lblName = new JTextField();
    JTextField lblAccNo = new JTextField();
    JTextField lblBal = new JTextField();
    JTextField txtAmt = new JTextField();

    JButton btnDep = new JButton("Deposit");
    JButton btnCancel   = new JButton("Back");

    JLabel lblLeft;
    JLabel lblRight;
    private final String accNo;
    private String name;
    private int balance;

    public Deposit(String accNo) {
        super ("Sukkur IBA Bank ");

        this.accNo = accNo;

        DBConn con = null;

        try {
            con = new DBConn();
            String q1 = "SELECT Client_Name FROM ClientInfo WHERE AccountNo = '" + accNo + "'";
            ResultSet rs1 = con.stmt.executeQuery(q1);
            while(rs1.next()) {
                name = rs1.getString(1);
            }
            rs1.close();

            String q2 = "SELECT Balance FROM ClientAccStatus WHERE AccountNo = '" + accNo + "'";
            ResultSet rs2 = con.stmt.executeQuery(q2);
            while (rs2.next()) {
                balance = rs2.getInt(1);
            }
            rs2.close();
        }
        catch (Exception e) {
            System.out.println("Error Occurred : " + e.getMessage());
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

        WindowListener L = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new MainMenu(accNo).setVisible(true);
                setVisible(false);
            }
        };

        addWindowListener(L);

        lblLeft = new JLabel("               ");
        lblRight = new JLabel("               ");


        lblAccNo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblBal.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtAmt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblName.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        JLabel lbl1 = new JLabel("Deposit Money",SwingConstants.CENTER);
        lbl1.setFont(new Font("TimesNewRoman",Font.BOLD,14));
        lbl1.setSize(5,4);

        lblAccNo.setEditable(false);
        lblBal.setEditable(false);
        lblName.setEditable(false);
        lblAccNo.setSize(5,4);
        lblBal.setSize(5,4);
        txtAmt.setSize(5,4);
        lblName.setSize(5,4);

        txtAmt.setFont(new Font("", Font.BOLD, 12));
        lbl2.setFont(new Font("", Font.BOLD, 12));
        lbl3.setFont(new Font("", Font.BOLD, 12));
        lbl4.setFont(new Font("", Font.BOLD, 12));
        lbl5.setFont(new Font("", Font.BOLD, 12));
        lbl2.setSize(5,4);
        lbl3.setSize(5,4);
        lbl4.setSize(5,4);
        lbl5.setSize(5,4);

        btnDep.updateUI();
        btnCancel.updateUI();

        lbl1.updateUI();
        lbl2.updateUI();
        lbl3.updateUI();
        lbl4.updateUI();
        lbl5.updateUI();

        lblAccNo.updateUI();
        lblBal.updateUI();
        txtAmt.updateUI();
        lblName.updateUI();

        lblAccNo.setText(accNo);
        lblName.setText(name);
        lblBal.setText(String.valueOf(balance));

        mainPanel = new JPanel(new BorderLayout(10,10));
        centerPanel = new JPanel(new GridLayout(4,2,10,10));
        leftPanel = new JPanel(new GridLayout(3,1));
        rightPanel = new JPanel(new GridLayout(3,1));
        btnDep.addActionListener(this);
        btnCancel.addActionListener(this);
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));

        mainPanel.add(lbl1,BorderLayout.NORTH);
        bottomPanel.add(btnDep);
        bottomPanel.add(btnCancel);

        centerPanel.add(lbl2);
        centerPanel.add(lblName);
        centerPanel.add(lbl3);
        centerPanel.add(lblAccNo);
        centerPanel.add(lbl4);
        centerPanel.add(lblBal);
        centerPanel.add(lbl5);
        centerPanel.add(txtAmt);

        // setting app icon:
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("SIBA_Logo.jpg")));

        mainPanel.add(centerPanel,BorderLayout.CENTER);
        mainPanel.add(lblLeft,BorderLayout.EAST);
        mainPanel.add(lblRight,BorderLayout.WEST);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);

        setContentPane (mainPanel);
        setBounds(450,200,400,250);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        DBConn con = null;
        String amt = txtAmt.getText();

        if(src == btnDep) {
            try {
                if(amt.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Enter Any Valid Amount.");
                }

                int dAmt = Integer.parseInt(amt);

                if((dAmt > 0) && (dAmt < 2_000_000)) {
                    con = new DBConn();
                    int newBal = balance + dAmt;
                    String q1 = "UPDATE ClientAccStatus SET Balance = " + newBal + " WHERE AccountNo = '" + accNo + "'";
                    con.stmt.executeUpdate(q1);

                    //Getting Current Date and Time:
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String curDateTime = dtf.format(now);

                    String q2 = "INSERT INTO ClientAccLogs (Date_Time, AccountNo, Action_, Remarks_) VALUES ('" + curDateTime + "', '" + accNo + "', 'DEPOSITED AMOUNT OF " + dAmt + "', '-----')";
                    con.stmt.executeUpdate(q2);
                    JOptionPane.showMessageDialog(null, "Operation Successful!");
                    new MainMenu(accNo).setVisible(true);
                    setVisible(false);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please Enter Any Valid Amount.");
                }
            }
            catch (Exception e) {
                System.out.println("Error Occurred : " + e.getMessage());
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
        else if(src == btnCancel) {
            setVisible(false);
            new MainMenu(accNo).setVisible(true);
        }

    }

}