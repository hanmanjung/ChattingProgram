package muti_network;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import userdb.UserDAO;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import java.awt.TextField;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
public class wooriclient extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	UserDAO userDAO = new UserDAO();
	MultiClient cc = new MultiClient();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					wooriclient frame = new wooriclient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public wooriclient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 274, 576);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		cc.actionPerformed(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 258, 538);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(87, 10, 74, 21);
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setFont(new Font("±º∏≤", Font.BOLD, 10));
		textField.setText("«‚±‚≈Â≈Âƒ£±∏");
		panel.add(textField);
		textField.setColumns(30);
		
		textField_1 = new JTextField();
		userDAO.getinfoName();
		textField_1.setText("ø¬∂Û¿Œ:");
		textField_1.setFont(new Font("±º∏≤", Font.BOLD, 12));
		textField_1.setEnabled(false);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(12, 46, 53, 21);
		panel.add(textField_1);
		
		JList listOnMember = new JList();
		listOnMember.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listOnMember.setBounds(22, 77, 213, 174);
		panel.add(listOnMember);
		
		textField_2 = new JTextField();
		textField_2.setText("ø¿«¡∂Û¿Œ:");
		textField_2.setFont(new Font("±º∏≤", Font.BOLD, 12));
		textField_2.setEnabled(false);
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(12, 259, 67, 21);
		panel.add(textField_2);
		
		JList listOffMember = new JList();
		listOffMember.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listOffMember.setBounds(22, 290, 213, 174);
		panel.add(listOffMember);
		
		JButton btnNewButton = new JButton("√§∆√");
		btnNewButton.setBounds(22, 505, 97, 23);
		panel.add(btnNewButton);
		cc.actionPerformed(null);
		
		JButton button = new JButton("∑Œ±◊æ∆øÙ");
		button.setBounds(138, 505, 97, 23);
		panel.add(button);
	}
}
