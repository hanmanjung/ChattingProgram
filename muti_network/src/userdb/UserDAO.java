package userdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	private String infoID;
	private String infoPWD;
	private String infoName;
	
	private Connection con;
	private ResultSet rs;
	private DataSource dataFactory;
	private Statement stmt;
	private PreparedStatement pstmt;
	
	private String driver = "oracle.jdbc.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String username="scott";
	private String password="scott";
	
	public UserDAO() {
			try {
				Context ctx = new InitialContext();
				Context envContext = (Context) ctx.lookup("java:/comp/env");
				dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	public int login(String infoID, String infoPWD) { // � ������ ���� ������ �α����� �õ��ϴ� �Լ�, ���ڰ����� ID�� Password�� �޾� login�� �Ǵ���.
		connDB();
		String SQL = "SELECT infoPWD FROM t_info WHERE infoID = ?"; // ������ DB�� �Էµ� ��ɾ SQL �������� ����.
		try {
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1,  infoID);
			rs = pstmt.executeQuery(); // ��� ����� �޾ƿ��� ResultSet Ÿ���� rs ������ �������� ������ ����� �־��� 
			if (rs.next()) {
				if (rs.getString(1).contentEquals(infoPWD)) {
					return 1; // �α��� ����
				}
				else {
					return 0; // ��й�ȣ ����ġ
				}
			}
			return -1; // ���̵� ����
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // DB ���� 
	}


	

	//�ߺ����� Ȯ��
	public boolean ID_Check(String infoID) {
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM t_info WHERE infoID = ? and infoPWD = ?");
		    pst.setString(1, infoID);
		    rs = pst.executeQuery();
		    if (rs.next()) {
		    		return false;
		    }else {
		    		return true;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		    return false;		
	}
	
	public int join(UserDAO userDAO) {
		connDB();
	//	if(!ID_Check(userDAO.getinfoID())) return 0;
		
		try {
				PreparedStatement pst = con.prepareStatement("INSERT INTO t_info VALUES (?,?,?)");
				pst.setString(1, userDAO.getinfoID());
				pst.setString(2, userDAO.getinfoPWD());
				pst.setString(3, userDAO.getinfoName());
				pst.executeUpdate();
				return 1;
		} catch (Exception e) {
				e.printStackTrace();
		}
		return -1;
	}
	
	// ���� ������ ��������
	@SuppressWarnings("unused")
	public UserDAO getUser(String infoName) {
		connDB();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT infoName FROM t_info WHERE infoID = ?");
			pst.setString(1, infoName);
			rs = pst.executeQuery();
			ArrayList<UserDAO> list2 = new ArrayList<UserDAO>();
			
			while (rs.next()) {
				UserDAO dao = new UserDAO();
				dao.setinfoName(rs.getString("�̸�"));
				ListIterator<UserDAO> element = list2.listIterator();
//				UserDAO userDAO = new UserDAO();
//				userDAO.setinfoName(rs.getString(1));
//				System.out.println(rs.getString(1));
				list2.add(dao);
				return dao;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private void connDB() {
		try {
			Class.forName(driver);
			System.out.println("Oracle ����̹� �ε� ����");
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Connection ���� ����");
			stmt = con.createStatement();
			System.out.println("Statement ���� ����");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getinfoID() {
		return infoID;
	}
	public void setinfoID(String infoID) { 
		this.infoID = infoID; 
	}
	public String getinfoPWD() {
		return infoPWD;
	}
	public void setinfoPWD(String infoPWD) { 
		this.infoPWD = infoPWD; 
	}
	public String getinfoName() {
		return infoName;
	}
	public void setinfoName(String infoName) { 
		this.infoName = infoName; 
	}
}
