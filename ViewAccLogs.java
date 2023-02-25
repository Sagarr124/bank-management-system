package BankManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAccLogs extends JFrame implements ActionListener {

    JPanel	mainPanel;
    JPanel  bottomPanel;
    JPanel  upperPanel;
    JTextArea tArea ;
    JScrollPane jp;
    JButton btnOK;
    private final String accNo;
    private int maximum;


    public ViewAccLogs(String accNo) {
        super ("Sukkur IBA Bank ");

        this.accNo = accNo;

        tArea = new JTextArea();
        tArea.setEditable(false);
        DBConn con = null;

        try {
            con = new DBConn();
            String q1 = "SELECT * FROM ClientAccLogs WHERE AccountNo = '" + accNo + "'";
            ResultSet rs = con.stmt.executeQuery(q1);

            while(rs.next()) {
                String record = " " + rs.getString(1) + ". |  " + rs.getString(2) + "  |  " + rs.getString(3) + "  |  " + rs.getString(4) + " \n";
                tArea.append(record);
                ++maximum;
            }
            rs.close();
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


        WindowListener L = new WindowAdapter () {
            public void windowClosing(WindowEvent e) {
                new MainMenu(accNo).setVisible(true);
                setVisible(false);
            }
        };

        addWindowListener(L);

        btnOK = new JButton("OK");
        mainPanel = new JPanel(new BorderLayout());

        jp = new JScrollPane(tArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        upperPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnOK.addActionListener(this);
        upperPanel.add(new JLabel(" "));
        JLabel lbl1 = new JLabel("View Account Logs", SwingConstants.CENTER);
        lbl1.setFont(new Font("", Font.BOLD, 14));
        lbl1.setSize(5, 4);

        // setting app icon:
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("SIBA_Logo.jpg")));

        upperPanel.add(lbl1);
        upperPanel.add(new JLabel(" ", SwingConstants.CENTER));
        upperPanel.add(new JLabel(" ", SwingConstants.CENTER));
        upperPanel.add(new JLabel("      Account No : " + accNo , SwingConstants.LEFT));
        upperPanel.add(new JLabel("      Total Account Logs : " +  maximum , SwingConstants.LEFT));
        mainPanel.add(upperPanel, BorderLayout.NORTH);
        mainPanel.add(jp, BorderLayout.CENTER);
        bottomPanel.add(btnOK);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane (mainPanel);
        setBounds(450,100,500,500);
        setVisible(true);

    }


    public void actionPerformed  (ActionEvent ae) {
        Object src = ae.getSource();

        if (src == btnOK){
            setVisible(false);
            new MainMenu(accNo).setVisible(true);
        }
    }

}
