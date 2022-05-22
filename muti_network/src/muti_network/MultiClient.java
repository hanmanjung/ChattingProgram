package muti_network;


import java.awt.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import userdb.UserDAO;

public class MultiClient implements ActionListener {
   private Socket socket;
   private ObjectInputStream ois;
   private ObjectOutputStream oos;
   private JFrame jframe, login1, signup; // â 
   private JTextField jtf, idc, pass, name;//����,���̵�,���â 
   private JTextArea jta, jlo;//ŰƼ���â,�α���â     Ÿ��
   private JLabel jlb1, jlb2, jlb3, jID, jPW, jNAME;//ŰƼâ���� �������̵�� �������ּҸ���Ÿ���� ��
   private JPanel jp1, jp2, jp3, jp4, jPanel1, jPanel2;//��ư��� ��Ƽ� �����ӿ� ���̴� �ٱ���
   private String ip;//�α����Ҽ��ִ� ������
   private String id;//�α����Ҽ��ִ� ���̵�
   private String Name;
   private JButton jbtn, jbtn1 , jbtn2, jbtn3, jbtn4, jbtn5, jexit, jfriend;//���۹�ư�̶� �α��� ���� ��ư�̿���
   public boolean changepower = false;
   public boolean saypower = false;
   private boolean login = false;
   UserDAO userDAO = new UserDAO();

   Image img = new ImageIcon("C:\\Users\\HanManJung\\Downloads\\���ŰƼ.jpg").getImage();

   public MultiClient() {
      /*ip = argIp;
      id = argId;*/
      jframe = new JFrame("Multi Chatting");
      login1 = new JFrame("Login");
      JProgressBar progressBar = new JProgressBar();
      progressBar.setStringPainted(true);
      progressBar.setIndeterminate(true);
      progressBar.setBounds(32, 303, 195, 14);
      
      
      jtf = new JTextField(20);
      idc = new JTextField(20);
      pass = new JPasswordField(20);
      name = new JTextField(20);
      

      jta = new JTextArea(43, 43) {
         {
            setOpaque(false);
         }

         public void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, null);
            super.paintComponent(g);
         }
      };
      jlo = new JTextArea(30,30); 
      jlb1 = new JLabel("�������") {
         {
            setOpaque(false);
         }
      };
      jlb2 = new JLabel("IP : " + ip) {
         {
            setOpaque(true);
         }
      };
      jID = new JLabel("�ƾƵ�"); //ID ��
      jPW = new JLabel("�н�����"); //�н����� ��
      jbtn = new JButton("Enter"); // ä������ ��ư
      jfriend = new JButton("ģ�����"); // ģ����� ��ư
      jbtn1 = new JButton("Login"); //�α��� ��ư
      jbtn2 = new JButton("signup"); //ȸ������ ��ư
      jexit = new JButton("exit"); //���� ��ư
      jp1 = new JPanel(); // �ٱ���
      jp2 = new JPanel();
      jp3 = new JPanel();  //�α��� ȭ��
      jp4 = new JPanel();
      
      jbtn.setFont(new Font("HY����L", Font.PLAIN, (int) 20));
      jlb1.setFont(new Font("HY����L", Font.PLAIN, (int) 15));
      jlb1.setBackground(Color.PINK);
      jlb2.setBackground(Color.PINK);
      jlb2.setFont(new Font("HY����L", Font.PLAIN, (int) 15));
      
      jID.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
      jID.setHorizontalAlignment(jID.CENTER);
      jPW.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
      jPW.setHorizontalAlignment(jPW.CENTER);
      
      idc.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
      idc.setBackground(Color.WHITE);
      pass.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
      pass.setBackground(Color.WHITE);

      jbtn1.setBackground(Color.PINK);
      jbtn1.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
      jbtn2.setBackground(Color.PINK);
      jbtn2.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
      jexit.setBackground(Color.PINK);
      jexit.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
      jbtn.setBackground(Color.PINK);
      jfriend.setBackground(Color.PINK);
      jlo.setBackground(Color.PINK);
      
      jp1.setLayout(new BorderLayout());
      jp2.setLayout(new BorderLayout());
      jp3.setLayout(new GridLayout(4, 3, 0, 0));

      jp1.add(jbtn, BorderLayout.EAST); 
      jp1.add(jfriend, BorderLayout.WEST);
      jp1.add(jtf, BorderLayout.CENTER);
      jp2.add(jlb1, BorderLayout.CENTER);
      jp2.add(jlb2, BorderLayout.EAST);
      
      jp1.setBackground(Color.PINK);
      jp2.setBackground(Color.PINK);
      jp3.setBackground(Color.PINK);
      jp4.setBackground(Color.PINK);
      jp3.add(jID);
      jp3.add(idc);
      jp3.add(jPW);
      jp3.add(pass);
      jp3.add(jbtn1);
      jp3.add(jbtn2);
      jp3.add(jexit);
      
      jframe.add(jp1, BorderLayout.SOUTH);
      jframe.add(jp2, BorderLayout.NORTH);
      login1.add(jp3, BorderLayout.EAST);
      login1.add(jp4, BorderLayout.EAST);
      
      JScrollPane jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      jframe.add(jsp, BorderLayout.CENTER);
      JScrollPane jsp1 = new JScrollPane(jlo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      login1.add(jp3, BorderLayout.CENTER);

      jtf.addActionListener(this); // ��ǵ�
      jbtn.addActionListener(this);
      jfriend.addActionListener(this);
      jbtn2.addActionListener(this);
      jexit.addActionListener(this);
      
      jframe.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            try {
               oos.writeObject(ip + "#exit");
            } catch (IOException ee) {
               ee.printStackTrace();
            }
            System.exit(0);
         }

         public void windowOpened(WindowEvent e) {
            jtf.requestFocus();
         }
      });

      jbtn1.addActionListener(this);

      jta.setEditable(false);
      Toolkit tk = Toolkit.getDefaultToolkit();
      Dimension d = tk.getScreenSize();
      int screenHeight = d.height;
      int screenWidth = d.width;
      
      jframe.pack();
      jframe.setSize(500, 500);
      jframe.setLocation((screenWidth - jframe.getWidth()) / 2, (screenHeight - jframe.getHeight()) / 2);
      jframe.setResizable(false);
      jframe.setVisible(false);

      login1.pack();
      login1.setSize(800, 300);
      login1.setLocation((screenWidth - jframe.getWidth()) / 2, (screenHeight - jframe.getHeight()) / 2);
      login1.setResizable(false);
      login1.setVisible(true);
   }

   public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource();
      String msg = jtf.getText();

      String str = e.getActionCommand();
      
      if (str.equals("Login")) {
    	 ip = idc.getText();
         id = pass.getText();
    	 int result = userDAO.login(ip,id);
    	 if(result == 1) {
             //�α��� ���� �޽���
             JOptionPane.showMessageDialog(null, "�α��� ����");
             
          }else {
             JOptionPane.showMessageDialog(null, "�α��� ����");
             System.exit(0);
          }
         jframe.setVisible(true);
         login1.setVisible(false);

      }
      
      if (str.equals("exit")){
         System.exit(0);
      }
      
      if (str.equals("signup")) {
    	  MultiClient2 m2 = new MultiClient2();
    	  login1.setVisible(false);
      }
      
      if (str.equals("ģ�����")) {
    	  MultiClient3 m3 = new MultiClient3();
      }
      
      
      if (obj == jtf && changepower == true) {
         changepower = false;
         if (msg == null || msg.length() == 0) {
            JOptionPane.showMessageDialog(jframe, "����������", "���", JOptionPane.WARNING_MESSAGE);
         } else {
        	id = jtf.getText();
            jtf.setText("");
         }
      } else if (obj == jtf && saypower == true) {
         saypower = false;
         if (msg == null || msg.length() == 0) {
            JOptionPane.showMessageDialog(jframe, "����������", "���", JOptionPane.WARNING_MESSAGE);
         } else {
            id = jtf.getText();
            jtf.setText("");
         }
      }

      if (obj == jtf) {
         if (msg == null || msg.length() == 0) {
            JOptionPane.showMessageDialog(jframe, "����������", "���", JOptionPane.WARNING_MESSAGE);
         } else {
            try {
               oos.writeObject(id + "#" + msg);
            } catch (IOException ee) {
               ee.printStackTrace();
            }
            jtf.setText("");
         }
      } else if (obj == jbtn) {
         try {
            oos.writeObject(ip + "#" + msg);
         } catch (IOException ee) {
            ee.printStackTrace();
         }
         jtf.setText("");
      }else if (obj == jexit) {
          try {
              oos.writeObject(ip + "#exit");
           } catch (IOException ee) {
              ee.printStackTrace();
           }
          System.exit(0);
        }
   }


	public void exit() {
	      System.exit(0);
	   }

	public class MultiClient2 extends JFrame{
		MultiClient2(){
			setTitle("signup"); //Ÿ��Ʋ
	        jPanel1 = new JPanel(new GridLayout(4,2,30,30));
	        jbtn3 = new JButton("�α���â���� ���ư���");
	        jbtn4 = new JButton("����");
	        
	        jID = new JLabel("���̵�"); //ID ��
	        jPW = new JLabel("�н�����"); //�н����� ��
	        jNAME = new JLabel("�̸�");
	        
			idc = new JTextField(20);
	        pass = new JPasswordField(20);
	        name = new JTextField(20);
	        
	        jID.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
	        jID.setHorizontalAlignment(jID.CENTER);
	        jPW.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
	        jPW.setHorizontalAlignment(jPW.CENTER);
	        jNAME.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
	        jNAME.setHorizontalAlignment(jPW.CENTER);
	        
	        
	        idc.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
	        idc.setBackground(Color.WHITE);
	        pass.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
	        pass.setBackground(Color.WHITE);
	        name.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
	        name.setBackground(Color.WHITE);
	
	        jPanel1.setBackground(Color.PINK);
	        
	        jbtn3.setBackground(Color.PINK);
	        jbtn3.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
	        jbtn4.setBackground(Color.PINK);
	        jbtn4.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
	        
	        setSize(800, 500);
	
	        add(jPanel1);
	        
	        jPanel1.add(jID);
	        jPanel1.add(idc);
	        jPanel1.add(jPW);
	        jPanel1.add(pass);
	        jPanel1.add(jNAME);
	        jPanel1.add(name);
	        jPanel1.add(jbtn3);
	        jPanel1.add(jbtn4);
	        
	        jbtn3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object obj = e.getSource();
				    String msg = jtf.getText();

				    String str = e.getActionCommand();
				    
				    if (str.equals("�α���â���� ���ư���")) {
				    	  login1.setVisible(true);
				    	  dispose();
				      }
				}
			});
	        
	        
	        jbtn4.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object obj = e.getSource();
				    String msg = jtf.getText();

				    String str = e.getActionCommand();
				    
				    if (str.equals("����")) {
				    	UserDAO userdao = new UserDAO();
				    	userdao.setinfoID(idc.getText());
				    	userdao.setinfoPWD(pass.getText());
				    	userdao.setinfoName(name.getText());
				        
				        int result4 = userDAO.join(userdao);
				        
				        if(result4 == 1) {
					    	  login1.setVisible(true);
					    	  JOptionPane.showMessageDialog(null, "���ԿϷ�");
					    	  dispose();
				        }else {
				        	JOptionPane.showMessageDialog(null, "���Խ���");
				        }
				      }
				}
			});
	        
	         
	        Dimension frameSize = getSize();
	        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
	        setLocation((windowSize.width - frameSize.width) / 2,
	                (windowSize.height - frameSize.height) / 2); //ȭ�� �߾ӿ� ����
	        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        setVisible(true);
	    }
	}
	
	public class MultiClient3 extends JFrame {
		MultiClient3(){
			setTitle("ģ�����"); //Ÿ��Ʋ
	        jPanel2 = new JPanel();
	        jbtn5 = new JButton("�ݱ�");
	        jlb3 = new JLabel();
	        
	        
	        jPanel2.setBackground(Color.PINK);
	        
	        String name = userDAO.getinfoName();
	        jlb3.setText(name);
	        
	        setSize(800, 500);
	    	
	        add(jPanel2);
	        jPanel2.add(jbtn5);
	        jPanel2.add(jlb3);
	        jbtn5.setBackground(Color.PINK);
	        jbtn5.setFont(new Font("HY����L", Font.PLAIN, (int) 30));
	        
	        jbtn5.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object obj = e.getSource();			

				    String str = e.getActionCommand();
				    
				   
				    if (str.equals("�ݱ�")) {
				    	jta.setVisible(true);
				    	dispose();
				    }
					
				}
	        	
	        });
	        
	        jtf = new JTextField(200);
	        jtf.setBackground(Color.WHITE);
	        
	        userDAO.getUser(getName());
	        
	        
	        
	        Dimension frameSize = getSize();
	        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
	        setLocation((windowSize.width - frameSize.width) / 2,
	                (windowSize.height - frameSize.height) / 2); //ȭ�� �߾ӿ� ����
	        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        setVisible(true);
		}
	}
   
   public void init() throws IOException {
      socket = new Socket("172.16.5.250", 5000);
      System.out.println("connected...");
      oos = new ObjectOutputStream(socket.getOutputStream());
      ois = new ObjectInputStream(socket.getInputStream());
      MultiClientThread ct = new MultiClientThread(this);
      Thread t = new Thread(ct);
      t.start();
   }

   public static void main(String args[]) throws IOException {
      JFrame.setDefaultLookAndFeelDecorated(true);
      MultiClient cc = new MultiClient();
      cc.init();
   }

   public ObjectInputStream getOis() {
      return ois;
   }

   public JTextArea getJta() {
      return jta;
   }

   public String getId() {
      return id;
   }

   public void SetName(String a) {
      id = a;
   }

   public void Clear() {
	   jta.setText(""); //�ʱ�ȭ�ǰ�
	   jtf.requestFocus();
	}
   
   public void My(){
	   jta.setDisabledTextColor(Color.blue);
   }
}