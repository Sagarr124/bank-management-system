package BankManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransferMoney extends JFrame implements ActionListener {

    JPanel	mainPanel;
    JPanel  centerPanel;
    JPanel  leftPanel;
    JPanel  rightPanel;
    JPanel  bottomPanel;
    JLabel lbl2 = new JLabel("Account No (From) :");
    JLabel lbl3 = new JLabel("Balance          :");
    JLabel lbl4 = new JLabel("Account No (To)   :");
    JLabel lbl5 = new JLabel("Amount           :");

    JTextField lblAccFr = new JTextField();
    JTextField lblBal = new JTextField();
    JTextField txtAccTo = new JTextField();
    JTextField txtAmt = new JTextField();

    JButton btnTrans = new JButton("Transfer");
    JButton btnCancel   = new JButton("Back");

    JLabel lblLeft;
    JLabel lblRight;
    private final String accNo;
    private int balance;

    public TransferMoney(String accNo) {
        super ("Sukkur IBA Bank");

        this.accNo = accNo;

        DBConn con = null;

        try {
            con = new DBConn();
            String q1 = "SELECT Balance FROM ClientAccStatus WHERE AccountNo = '" + accNo + "'";
            ResultSet rs1 = con.stmt.executeQuery(q1);
            while (rs1.next()) {
                balance = rs1.getInt(1);
            }
            rs1.close();
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

        lblAccFr.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblBal.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtAccTo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtAmt.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        lblAccFr.setEditable(false);
        lblBal.setEditable(false);

        lblAccFr.setSize(5,4);
        lblBal.setSize(5,4);
        txtAccTo.setSize(5,4);
        txtAmt.setSize(5,4);
        txtAmt.setFont(new Font("", Font.BOLD, 12));
        lbl2.setFont(new Font("", Font.BOLD , 12));
        lbl3.setFont(new Font("", Font.BOLD , 12));
        lbl4.setFont(new Font("", Font.BOLD , 12));
        lbl5.setFont(new Font("", Font.BOLD, 12));
        lbl2.setSize(5,4);
        lbl3.setSize(5,4);
        lbl4.setSize(5,4);
        lbl5.setSize(5,4);
        JLabel lbl1 = new JLabel("Transfer Money",SwingConstants.CENTER);
        lbl1.setFont(new Font("TimesNewRoman",Font.BOLD,14));
        lbl1.setSize(5,4);

        btnTrans.updateUI();
        btnCancel.updateUI();

        lbl1.updateUI();
        lbl2.updateUI();
        lbl3.updateUI();
        lbl4.updateUI();
        lbl5.updateUI();

        lblAccFr.updateUI();
        lblBal.updateUI();
        txtAccTo.updateUI();
        txtAmt.updateUI();

        mainPanel = new JPanel(new BorderLayout(10,10));
        centerPanel = new JPanel(new GridLayout(4,2,10,10));
        leftPanel = new JPanel(new GridLayout(3,1));
        rightPanel = new JPanel(new GridLayout(3,1));
        btnTrans.addActionListener(this);
        btnCancel.addActionListener(this);
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));

        mainPanel.add(lbl1,BorderLayout.NORTH);
        bottomPanel.add(btnTrans);
        bottomPanel.add(btnCancel);

        lblAccFr.setText(accNo);
        lblBal.setText(String.valueOf(balance));

        centerPanel.add(lbl2);
        centerPanel.add(lblAccFr);
        centerPanel.add(lbl3);
        centerPanel.add(lblBal);
        centerPanel.add(lbl4);
        centerPanel.add(txtAccTo);
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
        String accTo = txtAccTo.getText();
        String amt = txtAmt.getText();

        if(src == btnTrans) {
            try {
                if(amt.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Enter Any Valid Amount.");
                }

                int amount = Integer.parseInt(amt);

                if(balance > amount) {
                    con = new DBConn();
                    String q1 = "SELECT Balance FROM ClientAccStatus WHERE AccountNo = '" + accTo + "'";
                    ResultSet rs1 = con.stmt.executeQuery(q1);

                    if(rs1.next()) {
                        int b = rs1.getInt(1);
                        int trsAmt = b + amount;
                        String q2 = "UPDATE ClientAccStatus SET Balance = " + trsAmt + " WHERE AccountNo = '" + accTo + "'";
                        con.stmt.executeUpdate(q2);

                        int newBal = balance - amount;
                        String q3 = "UPDATE ClientAccStatus SET Balance = " + newBal + " WHERE AccountNo = '" + accNo + "'";
                        con.stmt.executeUpdate(q3);

                        //Getting Current Date and Time:
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String curDateTime = dtf.format(now);

                        String q4 = "INSERT INTO ClientAccLogs (Date_Time, AccountNo, Action_, Remarks_) VALUES ('" + curDateTime + "', '" + accNo + "', 'TRANSFERRED AMOUNT OF " + amount + " TO " + accTo + "', '-----')";
                        con.stmt.executeUpdate(q4);
                        JOptionPane.showMessageDialog(null, "Operation Successful!");
                        new MainMenu(accNo).setVisible(true);
                        setVisible(false);
                        rs1.close();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Other Account Doesn't Exist. Please Enter Valid Account No.");
                    }
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
