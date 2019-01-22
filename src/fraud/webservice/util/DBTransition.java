package fraud.webservice.util;

import fraud.webservice.util.Configurator.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javolution.text.Text;

public class DBTransition {
	
    private Configurator configurator = Configurator.getInstance();

	public void UpdateDB(UserInfo tobj, Connection conn, Statement stmt) {
		// TODO Auto-generated method stub
		insertlogs(tobj, conn, stmt);
		updateMasterTable(tobj, conn, stmt);
		System.out.println("DB updation successfully done for " + tobj.getREFERENCE_ID());
	}

	private void updateMasterTable(UserInfo tobj, Connection conn, Statement stmt) {
		// TODO Auto-generated method stub
		System.out.println("update mastrerTable for " + tobj.getREFERENCE_ID());
		try {
			Text sql = new Text(configurator.getSqlQuary(2));
            sql = sql.replace("<REFERENCE_ID>",tobj.getREFERENCE_ID());
            sql = sql.replace("<CATEGORY>",tobj.getCATEGORY());
            sql = sql.replace("<ALARM_CODE_KEY>",tobj.getALARM_CODE_KEY());
            System.out.println(sql.toString());            
			stmt.executeUpdate(sql.toString());
		} catch (Exception ex) {
			System.out.println("error in updateMasterTable block for " + tobj.getREFERENCE_ID());
			ex.printStackTrace();
		}
	}

	private void insertlogs(UserInfo tobj, Connection conn, Statement stmt) {
		// TODO Auto-generated method stub
		System.out.println("Inset Logs for " + tobj.getREFERENCE_ID());
		Text sql = null;
		try {
			IsConnect(conn, stmt);
			sql = new Text(
					"insert into tbl_fraud_stop_logs (REFERENCE_ID,ALARM_CODE_KEY,CATEGORY,ALARM_CREATED_ON,API_RETURNCODE,API_RETURNMSG,API_STATUSCODE,PROCESS_DATETIME)"
							+ " values ('" + tobj.getREFERENCE_ID() + "','" + tobj.getALARM_CODE_KEY() + "','"
							+ tobj.getCATEGORY() + "','" + tobj.getALARM_CREATED_ON() + "','" + tobj.getAPI_RETURNCODE()
							+ "','" + tobj.getAPI_RETURNMSG() + "','" + tobj.getAPI_STATUSCODE() + "',sysdate)");
			System.out.println("insert query"+sql.toString());
			stmt.executeUpdate(sql.toString());
		} catch (Exception e) {
			System.out.println("error in insertlogs block for " + tobj.getREFERENCE_ID());
			e.printStackTrace();
		}

	}

	public void IsConnect(Connection conn, Statement stmt) throws ClassNotFoundException {

		try {
			if (conn == null || conn.isClosed()) {
				getConnection(conn, stmt);
			}
		} catch (Exception ex) {
			getConnection(conn, stmt);
		}
	}

	private void getConnection(Connection conn, Statement stmt) throws ClassNotFoundException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			if (conn != null && conn.isClosed()) {
				conn = null;
				stmt = null;
			}
			if (conn == null) {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@10.50.0.10:1521/orcl", "orv5_etl", "orv5_etl");
				stmt = conn.createStatement();
				System.out.println("db connection created successfully under DBTransition classs");
			}
		} catch (SQLException ex) {
			System.out.println("error in creating the DB connection under DBTransition classs");
			ex.printStackTrace();
		}

	}

}
