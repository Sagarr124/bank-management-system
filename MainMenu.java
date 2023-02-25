package BankManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainMenu extends JFrame implements ActionListener {

    JPanel	mainPanel, centerPanel;

    JButton btnViewAcc ;
    JButton btnViewLogs;
    JButton btnTransfer ;
    JButton btnDeposit ;
    JButton btnWithdraw;
    JButton btnChangePIN;
    JButton btnLogOut;
    JLabel lblLeft;
    JLabel lblRight;
    private final String accNo;

    public MainMenu(String accNo) {
        super ("Sukkur IBA Bank ");

        this.accNo = accNo;

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        Font font = new Font("SansSerif", Font.BOLD, 12);
        btnViewAcc = new JButton("View Account");
        btnViewAcc.setFont(font);
        btnViewLogs = new JButton("View Account Logs");
        btnViewLogs.setFont(font);
        btnTransfer = new JButton("Transfer Money");
        btnTransfer.setFont(font);
        btnDeposit = new JButton("Deposit");
        btnDeposit.setFont(font);
        btnWithdraw = new JButton("Withdraw");
        btnWithdraw.setFont(font);
        btnChangePIN = new JButton("Change PIN Code");
        btnChangePIN.setFont(font);
        btnLogOut = new JButton("Log Out");
        btnLogOut.setFont(font);
        btnLogOut.setForeground(Color.RED);
        lblLeft = new JLabel("               ");
        lblRight = new JLabel("               ");

        mainPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel = new JPanel(new GridLayout(7, 1, 8, 8));

        btnViewAcc.addActionListener(this);
        btnViewLogs.addActionListener(this);
        btnTransfer.addActionListener(this);
        btnDeposit.addActionListener(this);
        btnChangePIN.addActionListener(this);
        btnWithdraw.addActionListener(this);
        btnLogOut.addActionListener(this);
        JLabel lbl1 = new JLabel("Main Menu", SwingConstants.CENTER);
        lbl1.setFont(new Font("TimesNewRoman", Font.BOLD, 14));
        lbl1.setSize(5,4);

        // setting app icon:
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("SIBA_Logo.jpg")));

        mainPanel.add(lbl1,BorderLayout.NORTH);
        centerPanel.add(btnViewAcc);
        centerPanel.add(btnViewLogs);
        centerPanel.add(btnTransfer);
        centerPanel.add(btnDeposit);
        centerPanel.add(btnWithdraw);
        centerPanel.add(btnChangePIN);
        centerPanel.add(btnLogOut);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(lblLeft, BorderLayout.EAST);
        mainPanel.add(lblRight, BorderLayout.WEST);
        mainPanel.add(new JLabel(""), BorderLayout.SOUTH);

        setContentPane (mainPanel);

        setResizable(false);
        setBounds(500,150,300,400);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();

        if(src == btnViewAcc) {
            new ViewAccDetails(accNo).setVisible(true);
            setVisible(false);
        }
        else if(src == btnViewLogs) {
            new ViewAccLogs(accNo).setVisible(true);
            setVisible(false);
        }
        else if(src == btnTransfer) {
            new TransferMoney(accNo).setVisible(true);
            setVisible(false);

        }
        else if(src == btnDeposit) {
            new Deposit(accNo).setVisible(true);
            setVisible(false);

        }
        else if(src == btnWithdraw) {
            new Withdrawal(accNo).setVisible(true);
            setVisible(false);

        }
        else if(src == btnChangePIN) {
            new ChangePIN(accNo).setVisible(true);
            setVisible(false);

        }
        else if(src == btnLogOut) {
            DBConn con = null;
            try {
                con = new DBConn();
                String q1 = "UPDATE ClientAccStatus SET LogInStatus = " + 0 + " WHERE AccountNo = '" + accNo + "'";
                con.stmt.executeUpdate(q1);
            }
            catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
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

            new LogIn().setVisible(true);
            setVisible(false);
        }

    }

}
