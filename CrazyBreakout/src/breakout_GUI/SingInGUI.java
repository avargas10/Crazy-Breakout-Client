package breakout_GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import comunication_Handler.client_handler;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SingInGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txt_username;
	private JTextField txt_IpAdress;
	private JTextField txt_port;
	private client_handler _handler;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SingInGUI frame = new SingInGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SingInGUI() {
		_handler= new client_handler();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnl_singIn = new JPanel();
		pnl_singIn.setBounds(0, 0, 450, 272);
		contentPane.add(pnl_singIn);
		pnl_singIn.setLayout(null);
		
		txt_username = new JTextField();
		txt_username.setBounds(160, 46, 223, 19);
		pnl_singIn.add(txt_username);
		txt_username.setColumns(10);
		
		JLabel lbl_username = new JLabel("Username");
		lbl_username.setBounds(59, 48, 83, 15);
		pnl_singIn.add(lbl_username);
		
		JLabel lbl_IpAdress = new JLabel("Ip Adress");
		lbl_IpAdress.setBounds(59, 103, 70, 15);
		pnl_singIn.add(lbl_IpAdress);
		
		txt_IpAdress = new JTextField();
		txt_IpAdress.setColumns(10);
		txt_IpAdress.setBounds(160, 101, 223, 19);
		pnl_singIn.add(txt_IpAdress);
		
		txt_port = new JTextField();
		txt_port.setColumns(10);
		txt_port.setBounds(160, 160, 223, 19);
		pnl_singIn.add(txt_port);
		
		JLabel lbl_port = new JLabel("Port");
		lbl_port.setBounds(59, 162, 70, 15);
		pnl_singIn.add(lbl_port);
		
		JButton btn_Play = new JButton("Play");
		btn_Play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_handler.connect(txt_username.getText(), txt_IpAdress.getText(), txt_port.getText());
			}
		});
		btn_Play.setBounds(295, 222, 117, 25);
		pnl_singIn.add(btn_Play);
	}
}
