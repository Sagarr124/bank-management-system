package BankManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePIN extends JFrame implements ActionListener {

    JPanel mainPanel;
	JPanel  centerPanel;
    JPanel  bottomPanel;
    JLabel lblhead = new JLabel("Change PIN",SwingConstants.CENTER);


    JLabel lbl1 = new JLabel("Account Holder :");
    JLabel lbl2 = new JLabel("Account No :");
    JLabel lblVal1 = new JLabel("Old PIN :");
    JLabel lblVal2 = new JLabel("New PIN :");
    JLabel lblVal3 = new JLabel("Confirm New PIN :");

    JTextField lblName = new JTextField();
    JTextField lblAcctNo = new JTextField();
    JPasswordField txtVal1 = new JPasswordField();
    JPasswordField txtVal2 = new JPasswordField();
    JPasswordField txtVal3 = new JPasswordField();
    JButton btnOk = new JButton("OK");
    JButton btnCancel = new JButton("Cancel");
    JLabel lblLeft;
    JLabel lblRight;
    private final String accNo;
    private String name;


	public ChangePIN(String accNo) {
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

        lblhead.setFont(new Font("TimesNewRoman",Font.BOLD,14));
        lblhead.setSize(5,4);

		lblLeft = new JLabel("         ");
		lblRight = new JLabel("             ");
        lblName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblAcctNo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        txtVal1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtVal2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtVal3.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        lblName.setEditable(false);
		lblAcctNo.setEditable(false);

		lblName.setSize(6,4);
		lblAcctNo.setSize(6,4);
		lblVal1.setSize(5,4);
		lblVal2.setSize(5,4);
		lblVal3.setSize(5,4);
		txtVal1.setSize(6,4);
		txtVal2.setSize(6,4);
		txtVal3.setSize(6,4);

		btnOk.updateUI();
		lblhead.updateUI();

		lbl1.updateUI();
		lbl2.updateUI();

		lblName.updateUI();
		lblAcctNo.updateUI();
		lblVal1.updateUI();
		lblVal2.updateUI();
		lblVal3.updateUI();
		txtVal1.updateUI();
		txtVal2.updateUI();
		txtVal3.updateUI();

		mainPanel = new JPanel(new BorderLayout(10,10));
		centerPanel = new JPanel(new GridLayout(5,2,8,8));
		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		mainPanel.add(lblhead,BorderLayout.NORTH);

		centerPanel.add(lbl1);
		centerPanel.add(lblName);

		centerPanel.add(lbl2);
		centerPanel.add(lblAcctNo);

		centerPanel.add(lblVal1);
		centerPanel.add(txtVal1);

		centerPanel.add(lblVal2);
		centerPanel.add(txtVal2);

		centerPanel.add(lblVal3);
		centerPanel.add(txtVal3);

		lblName.setText(name);
		lblAcctNo.setText(accNo);

        // setting app icon:
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("SIBA_Logo.jpg")));

		mainPanel.add(centerPanel,BorderLayout.CENTER);
        bottomPanel.add(btnOk);
        bottomPanel.add(btnCancel);
        mainPanel.add(lblLeft,BorderLayout.EAST);
        mainPanel.add(lblRight,BorderLayout.WEST);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);

        setContentPane (mainPanel);
        setBounds(500,200,400,300);
        setVisible(true);

	}


	public void actionPerformed(ActionEvent ae) {
	    Object src = ae.getSource();
        DBConn con = null;

	    try {
            if(src == btnOk) {
                int b = Integer.parseInt(new String(txtVal1.getPassword()));
                int c = Integer.parseInt(new String(txtVal2.getPassword()));
                int d = Integer.parseInt(new String(txtVal3.getPassword()));

                if(b < 1000) {
                    JOptionPane.showMessageDialog(null, "Please Enter Current PIN.");
                }
                else if(c < 1000) {
                    JOptionPane.showMessageDialog(null, "Please Enter New PIN.");
                }
                else if(d < 1000) {
                    JOptionPane.showMessageDialog(null, "Please Enter Confirm New PIN.");
                }

                if(c == d) {
                    con = new DBConn();
                    String q1 = "SELECT * FROM ClientInfo WHERE AccountNo = '" + accNo + "' and PIN = " + b + "";
                    ResultSet rs = con.stmt.executeQuery(q1);

                    if(rs.next()) {
                        String q2 = "UPDATE ClientInfo SET PIN = " + d + " WHERE AccountNo = '" + accNo + "'";
                        con.stmt.executeUpdate(q2);
                        JOptionPane.showMessageDialog(null, "PIN changed successfully.");
                        new MainMenu(accNo).setVisible(true);
                        setVisible(false);
                        rs.close();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Old Password is Incorrect.");
                    }

                }
                else {
                    JOptionPane.showMessageDialog(null, "New PIN and Confirm New PIN didn't match.");
                }
            }
            else if(src == btnCancel) {
                new MainMenu(accNo).setVisible(true);
                setVisible(false);
            }
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
