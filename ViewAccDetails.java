package BankManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAccDetails extends JFrame implements ActionListener {

    JPanel	mainPanel;
    JPanel  centerPanel;
    JPanel  leftPanel;
    JPanel  rightPanel;
    JPanel  bottomPanel;
    JLabel lbl1 = new JLabel("\nAccount Details",SwingConstants.CENTER);

    JLabel lbl2 = new JLabel("Name of Account Holder :");
    JLabel lbl3 = new JLabel("Account No :");
    JLabel lbl4 = new JLabel("Balance :");
    Color color1 = new Color(0,0,0);
    Color color2 = new Color(255,255,255);

    JTextField lblName = new JTextField();
    JTextField lblAcctNo = new JTextField();
    JTextField lblBal = new JTextField();

    JButton btnOk = new JButton("OK");
    JLabel lblLeft;
    JLabel lblRight;

    private final String accNo;
    private String name;
    private int balance;


    public ViewAccDetails(String accNo) {

        super ("Sukkur IBA Bank");

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

        WindowListener L = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new MainMenu(accNo).setVisible(true);
                setVisible(false);
            }
        };

        addWindowListener(L);

        Font font = new Font("SansSerif", Font.BOLD, 14);
        Font font1 = new Font("TimesNewRoman", Font.BOLD, 12);
        lblLeft = new JLabel("          ");
        lblRight = new JLabel("         ");
        lblName.setBackground(color2);
        lblAcctNo.setBackground(color2);
        lblBal.setBackground(color2);
        lblName.setBorder(BorderFactory.createLineBorder(color1));
        lblAcctNo.setBorder(BorderFactory.createLineBorder(color1));
        lblBal.setBorder(BorderFactory.createLineBorder(color1));

        lbl1.setFont(font);
        lblName.setEditable(false);
        lblAcctNo.setEditable(false);
        lblBal.setEditable(false);
        lblName.setSize(5,4);
        lblAcctNo.setSize(5,4);
        lblBal.setSize(5,4);
        lbl2.setFont(font1);
        lbl3.setFont(font1);
        lbl4.setFont(font1);

        lbl1.setSize(5,4);
        lbl2.setSize(5,4);
        lbl3.setSize(5,4);
        lbl4.setSize(5,4);

        btnOk.updateUI();
        lbl1.updateUI();
        lbl2.updateUI();
        lbl3.updateUI();
        lbl4.updateUI();
        lblName.updateUI();
        lblAcctNo.updateUI();
        lblBal.updateUI();

        mainPanel = new JPanel(new BorderLayout(10,10));
        centerPanel = new JPanel(new GridLayout(3,2,8,8));
        leftPanel = new JPanel(new GridLayout(3,1));
        rightPanel = new JPanel(new GridLayout(3,1));
        btnOk.addActionListener(this);
        bottomPanel = new JPanel(new FlowLayout());

        mainPanel.add(lbl1,BorderLayout.NORTH);

        centerPanel.add(lbl2);
        centerPanel.add(lblName);
        centerPanel.add(lbl3);
        centerPanel.add(lblAcctNo);
        centerPanel.add(lbl4);
        centerPanel.add(lblBal);
        mainPanel.add(centerPanel,BorderLayout.CENTER);

        lblAcctNo.setText(accNo);
        lblName.setText(name);
        lblBal.setText(String.valueOf(balance));

        // setting app icon:
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("SIBA_Logo.jpg")));

        bottomPanel.add(btnOk);
        mainPanel.add(lblLeft,BorderLayout.EAST);
        mainPanel.add(lblRight,BorderLayout.WEST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane (mainPanel);

        setBounds(450,200,400,250);
        setResizable(false);
        setVisible(true);

    }


    public void actionPerformed  (ActionEvent ae) {
        Object src = ae.getSource();

        if (src == btnOk){
            setVisible(false);
            new MainMenu(accNo).setVisible(true);
        }
    }

}
