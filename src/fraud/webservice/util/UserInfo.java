package fraud.webservice.util;

import java.io.Serializable;

public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6444180298592592040L;
	private String REFERENCE_ID;
	private String ALARM_CODE_KEY;
	private String CATEGORY;
	private String ALARM_CREATED_ON;
	private String API_RETURNCODE;
	private String API_RETURNMSG;
	private String API_STATUSCODE;

	public String getREFERENCE_ID() {
		return REFERENCE_ID;
	}

	public void setREFERENCE_ID(String rEFERENCE_ID) {
		REFERENCE_ID = rEFERENCE_ID;
	}

	public String getALARM_CODE_KEY() {
		return ALARM_CODE_KEY;
	}

	public void setALARM_CODE_KEY(String aLARM_CODE_KEY) {
		ALARM_CODE_KEY = aLARM_CODE_KEY;
	}

	public String getCATEGORY() {
		return CATEGORY;
	}

	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}

	public String getALARM_CREATED_ON() {
		return ALARM_CREATED_ON;
	}

	public void setALARM_CREATED_ON(String aLARM_CREATED_ON) {
		ALARM_CREATED_ON = aLARM_CREATED_ON;
	}

	public String getAPI_RETURNCODE() {
		return API_RETURNCODE;
	}

	public void setAPI_RETURNCODE(String aPI_RETURNCODE) {
		API_RETURNCODE = aPI_RETURNCODE;
	}

	public String getAPI_RETURNMSG() {
		return API_RETURNMSG;
	}

	public void setAPI_RETURNMSG(String aPI_RETURNMSG) {
		API_RETURNMSG = aPI_RETURNMSG;
	}

	public String getAPI_STATUSCODE() {
		return API_STATUSCODE;
	}

	public void setAPI_STATUSCODE(String aPI_STATUSCODE) {
		API_STATUSCODE = aPI_STATUSCODE;
	}

	@Override
	public String toString() {
		return "UserInfo [REFERENCE_ID=" + REFERENCE_ID + ", ALARM_CODE_KEY=" + ALARM_CODE_KEY + ", CATEGORY="
				+ CATEGORY + ", ALARM_CREATED_ON=" + ALARM_CREATED_ON + ", API_RETURNCODE=" + API_RETURNCODE
				+ ", API_RETURNMSG=" + API_RETURNMSG + ", API_STATUSCODE=" + API_STATUSCODE + "]";
	}

}
