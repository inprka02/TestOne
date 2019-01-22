package fraud.webservice.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static fraud.webservice.util.Configurator.getInstance;
import fraud.webservice.util.Configurator;
import fraud.webservice.call.webapicall;
import fraud.webservice.util.DBTransition;
import fraud.webservice.util.UserInfo;
import javolution.text.Text;

public class MainCall extends Thread {

	private static Configurator configurator = getInstance();

	private Connection conn = null;
	private Statement stmt = null;

	public static void main(String[] args) {
		System.out.println("process started");

		MainCall main = new MainCall();
		main.start();

	}

	public void run() {

		while (true) {
			try {
				IsConnect();
				List list_UserInfoTB = null;
				list_UserInfoTB = pullDataFromDB();
				System.out.println("Size of List is....." + list_UserInfoTB.size());
				DBTransition dbTrn = new DBTransition();
				for (int i = 0; i < list_UserInfoTB.size(); i++) {
					UserInfo tobj = (UserInfo) list_UserInfoTB.get(i);
					boolean success = false;
					webapicall call = new webapicall();
					System.out.println(tobj.toString());
					success = call.callWebApi(tobj, conn, stmt);
					if (success) {
						// enter in DB logs
						dbTrn.UpdateDB(tobj, conn, stmt);
					} else {
						System.out.println("API hit failed for::" + tobj.getREFERENCE_ID());
					}
				}
				list_UserInfoTB = null;
				try {
					// Thread.sleep(1000*30);
					Thread.sleep(3000 * 10);
				} catch (InterruptedException e) {
					System.out.println("exception in thread sleep");
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("error in main class program exection failed");
				e.printStackTrace();
			}
			System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		} // end while

	}

	public List pullDataFromDB() {
		List list_UserInfoTB = new ArrayList();
		UserInfo tobj = null;
		try {
			IsConnect();
			Text sql = null;
			sql = new Text(configurator.getSqlQuary(1));
			ResultSet rs = null;
			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				while (rs.next()) {
					tobj = new UserInfo();
					tobj.setREFERENCE_ID(rs.getString("REFERENCE_ID"));
					tobj.setALARM_CODE_KEY(rs.getString("ALARM_CODE_KEY"));
					tobj.setCATEGORY(rs.getString("CATEGORY"));
					tobj.setALARM_CREATED_ON(rs.getString("ALARM_CREATED_ON"));
					list_UserInfoTB.add(tobj);
				}
			} else {
				Thread.sleep(20000); // Sleep when no data found for 5 min
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list_UserInfoTB;
	}

	public void IsConnect() throws ClassNotFoundException {

		try {
			if (conn == null || conn.isClosed()) {
				getConnection();
			}
		} catch (Exception ex) {
			getConnection();
		}
	}

	private void getConnection() throws ClassNotFoundException {
		try {
			Class.forName(configurator.getDriverClass());
			if (conn != null && conn.isClosed()) {
				conn = null;
				stmt = null;
			}
			if (conn == null) {
				conn = DriverManager.getConnection(configurator.getDbURL(), configurator.getUsername(),configurator.getPassword());
				stmt = conn.createStatement();
				System.out.println("db connection created successfully");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
}
