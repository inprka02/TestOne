package fraud.webservice.call;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;

import fraud.webservice.util.UserInfo;

public class webapicall {

	public boolean callWebApi(UserInfo tobj, Connection conn, Statement stmt) {
		// TODO Auto-generated method stub
		boolean success = false;
		//tobj.setREFERENCE_ID("255737044142");
		tobj.setREFERENCE_ID("255737044141");
		
		System.out.println("call api for MSISDN" + tobj.getREFERENCE_ID());
		/*String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tan=\"http://tanzania.customization.ws.bss.zsmart.ztesoft.com\">"
				+ "<soapenv:Header><tan:AuthHeader>" + "<tan:Username>mmtest</tan:Username>"
				+ "<tan:Password>mmtest</tan:Password>" + "</tan:AuthHeader></soapenv:Header>"
				+ "<soapenv:Body><tan:TwoWayBlock4Peccancy><tan:TwoWayBlock4PeccancyDto>" + "<tan:MSISDN>"
				+ tobj.getREFERENCE_ID() + "</tan:MSISDN><tan:UserPwd>1234</tan:UserPwd>"
				+ "</tan:TwoWayBlock4PeccancyDto></tan:TwoWayBlock4Peccancy>" + "</soapenv:Body></soapenv:Envelope>";
*/			

			String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tan=\"http://tanzania.customization.ws.bss.zsmart.ztesoft.com\">"
						+ "<soapenv:Header><tan:AuthHeader>" + "<tan:Username>FRAMS</tan:Username>"
						+ "<tan:Password>@FRAMS123</tan:Password>" + "</tan:AuthHeader></soapenv:Header>"
						+ "<soapenv:Body><tan:TwoWayBlock4Peccancy><tan:TwoWayBlock4PeccancyDto>" + "<tan:MSISDN>"
						+ tobj.getREFERENCE_ID() + "</tan:MSISDN><tan:UserPwd>1234</tan:UserPwd>"
						+ "</tan:TwoWayBlock4PeccancyDto></tan:TwoWayBlock4Peccancy>" + "</soapenv:Body></soapenv:Envelope>";
						
		HttpURLConnection connection;
		try {
	//		String URL = "http://10.0.75.68:8070/ocswebservices/services/WebServicesSoapTanzania";
			String URL = "http://10.0.75.53:8080/ocswebservices/services/WebServicesSoapTanzania";
			connection = (HttpURLConnection) (new URL(URL).openConnection());
			System.out.println(xml);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setRequestProperty("SOAPAction", "");
			connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			System.out.println("Test unsubscription  for msisdn" + tobj.getREFERENCE_ID());
			OutputStream out = connection.getOutputStream();
			OutputStreamWriter wout = new OutputStreamWriter(out, "UTF-8");
			wout.write(xml);
			wout.flush();
			InputStream i = connection.getInputStream();
			int c = 0;
			String xmlrpc = "";
			while ((c = i.read()) != -1) {
				xmlrpc += (char) c;
			}
			System.out.println("SOAP response is as " + xmlrpc);
			int responseCode = connection.getResponseCode();
			String strMsg = xmlrpc;
			int startTag1 = strMsg.indexOf("<ReturnCode>");
			int endTag1 = strMsg.indexOf("</ReturnCode>");
			String returncode = strMsg.substring(startTag1, endTag1).replaceAll("<ReturnCode>", "");
			System.out.println("returncode is " + returncode);
			int startTag2 = strMsg.indexOf("<ReturnMsg>");
			int endTag2 = strMsg.indexOf("</ReturnMsg>");
			String ReturnMsg = strMsg.substring(startTag2, endTag2).replaceAll("<ReturnMsg>", "");
			System.out.println("ReturnMsg is " + ReturnMsg);
			tobj.setAPI_RETURNMSG(ReturnMsg);
			tobj.setAPI_RETURNCODE(returncode);
			tobj.setAPI_STATUSCODE(""+responseCode);
			out.close();
			connection.disconnect();
			success = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	public static void main(String[] args) {

	}

}
