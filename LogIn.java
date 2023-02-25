package BankManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogIn extends JFrame implements ActionListener, Runnable {
    JTextField txtAcctNo;
    JPasswordField txtPIN;
    JButton btnLogIn, btnSignUp;

    JLabel timeRunning = new JLabel("", SwingConstants.LEFT);
    JLabel lblDateRunning = new JLabel("", SwingConstants.LEFT);

    String currentTime;
    String dtString;

    Thread clockThread;
    Thread dateThread;

    Color c1 = new Color(0, 0, 128);

    public LogIn() {
        super ("Sukkur IBA Bank");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel  pMain = new JPanel ();
        pMain.setLayout(new BorderLayout());

        JPanel pUpper = new JPanel();
        pUpper.setLayout(new GridLayout(1,2));

        JPanel pUp = new JPanel();
        pUp.setLayout(new GridLayout(5,1));

        JPanel  pBtn = new JPanel ();
        pBtn.setLayout(new FlowLayout());

        JPanel pLeft = new JPanel(new GridLayout(3,1, 8,8));
        JPanel pRight = new JPanel(new GridLayout(3,1,8,8));
        JLabel lblAcctNo = new  JLabel("Account No :");
        JLabel lblPIN = new JLabel("PIN :");

        txtAcctNo = new JTextField("",10);
        txtPIN = new JPasswordField("",10);
        lblAcctNo.setSize(5,4);
        lblPIN.setSize(5,4);
        txtAcctNo.setSize(5,4);
        txtPIN.setSize(5,4);

        lblDateRunning.setSize(5,4);
        timeRunning.setSize(5,4);
        lblDateRunning.updateUI();
        timeRunning.updateUI();

        txtAcctNo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtPIN.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        pLeft.add(lblAcctNo);
        pLeft.add(lblPIN);

        pRight.add(txtAcctNo);
        pRight.add(txtPIN);

        pUpper.add(pLeft);
        pUpper.add(pRight);
        JLabel lbl1 = new JLabel("Welcome To Sukkur IBA Bank", SwingConstants.CENTER);
        lbl1.setFont(new Font("TimesNewRoman", Font.BOLD, 18));
        lbl1.setSize(5, 4);
        lbl1.setForeground(c1);

        pUp.add(lbl1, JLabel.CENTER);
        pUp.add(new JLabel(" ", JLabel.CENTER));
        pUp.add(lblDateRunning);
        pUp.add(timeRunning);
        pUp.add(new JLabel(" ", JLabel.CENTER));

        JLabel lblSpaces1 = new JLabel("         ");
        JLabel lblSpaces2 = new JLabel("         ");
        JLabel lblSpaces3 = new JLabel("         ");
        pMain.add(pUp,BorderLayout.NORTH);
        pMain.add(pUpper,BorderLayout.CENTER);
        pMain.add(lblSpaces1,BorderLayout.EAST);
        pMain.add(lblSpaces2,BorderLayout.WEST);
        pMain.add(lblSpaces3,BorderLayout.SOUTH);


        btnLogIn = new JButton("Login");
        btnSignUp = new JButton("Sign Up");
        pBtn.add(btnSignUp);
        ((FlowLayout)pBtn.getLayout()).setHgap(80);
        pBtn.add(btnLogIn);

        pMain.add(pBtn,BorderLayout.SOUTH);
        btnLogIn.addActionListener(this);
        btnSignUp.addActionListener(this);

        // setting app icon:
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("SIBA_Logo.jpg")));

        setContentPane(pMain);
        setBounds(450,200,400,300);
        setResizable(false);
        setVisible(true);

        clockThread = new Thread(this);
        dateThread = new Thread(this);
        clockThread.start();
        dateThread.start();

    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        DBConn con = null;

        try {
            if(src == btnLogIn) {
                String a = txtAcctNo.getText();
                char[] b = txtPIN.getPassword();

                if((a.equals("")) || (b.length == 0)) {
                    JOptionPane.showMessageDialog(null, "Please Enter Account No. and PIN.");
                }
                else {
                    con = new DBConn();
                    String q1 = "SELECT * FROM ClientInfo WHERE AccountNo = '" + a + "' and PIN = " + Integer.parseInt(String.valueOf(b)) + "";
                    ResultSet rs = con.stmt.executeQuery(q1);

                    if(rs.next()) {
                        String q2 = "UPDATE ClientAccStatus SET LogInStatus = " + 1 + " WHERE AccountNo = '" + a + "'";
                        con.stmt.executeUpdate(q2);
                        new MainMenu(a).setVisible(true);
                        setVisible(false);
                        rs.close();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Incorrect Account No. or PIN.");
                    }
                }
            }
            else if(src == btnSignUp) {
                new SignUp().setVisible(true);
                setVisible(false);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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

    public void run() {

        Thread thisThread = Thread.currentThread();

        while (clockThread == thisThread) {
            iterateTime();
            pause(); // for 1000 milliseconds
        }

        while (dateThread == thisThread) {
            iterateDate();
            pause(); // for 1000 milliseconds
        }
    }

    private void iterateTime() {
        // get the time and convert it to a date
        Calendar cal = Calendar.getInstance();
        java.util.Date date = cal.getTime();
        DateFormat dateFormatter = DateFormat.getTimeInstance();
        SimpleDateFormat dateFormatterH = new SimpleDateFormat("H:mm:ss");
        currentTime = dateFormatterH.format(date);
        timeRunning.setText("  Time : " + dateFormatter.format(date));
    }

    private void iterateDate() {
        // get the time and convert it to a date
        Calendar cal = Calendar.getInstance();
        java.util.Date date = cal.getTime();
        SimpleDateFormat dt = new SimpleDateFormat ("dd MMM yyyy '(' EE ')' ");
        dtString = dt.format(date);
        lblDateRunning.setText("  Date : " + dtString);
    }

    private void pause() {
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(LogIn::new);

    }
}
