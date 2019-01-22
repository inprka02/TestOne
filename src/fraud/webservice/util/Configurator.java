package fraud.webservice.util;



import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import javolution.text.Text;

public class Configurator {
    
//    private Logger logger = Logger.getLogger(Configurator.class);
    private Properties props = null;       
    private String dbURL = null;
    private String driverClass = null;
    private String username = null;
    private String password = null;        
    
    private int sqlcount;
    private String sqlQuary[];    

     
    //private static Configurator configurator;

    /**
     * constructor which initialise all property from resourse property file
     */
    private Configurator() {
        // Load Properties    
    	init();
    	
        
    }
    private static class SingletonHolder 
    { 
      private final static Configurator configurator = new Configurator();
    }

    public static Configurator getInstance()
    {
    	return SingletonHolder.configurator;
    }

    private void init()
    {
    	 loadProps();
         loadOracleConfiguration(); 
         setSqlcount();
         setSqlQuary();
    }

    public String getProperty(String _key) {
        try {
            return props.getProperty(_key);
        } catch (Exception e) {
            System.out.println("There is no entry in property file for key " + _key + " and " + e.getMessage());
            return null;
        }
    }

    public void loadProps() {
        try {
            props = new Properties();
            props.load(new FileInputStream(".." + File.separator + "conf" + File.separator + "loader.properties"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    
    public String getDbURL() {
        return dbURL;
    }   

    public String getDriverClass() {
        return driverClass;
    }

    
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }    

    public Properties getProps() {
        return props;
    }  
    
    public void loadOracleConfiguration() {
        dbURL = getProperty("oracle_url").trim();
        driverClass = getProperty("oracle_driver_class").trim();
        username = getProperty("oracle_username").trim();
        password = getProperty("oracle_password").trim(); 
    }
	public int getSqlcount() {
		return sqlcount;
	}

	public void setSqlcount() {
		this.sqlcount = Integer.parseInt(getProperty("oracle_quary_count").trim());
	}

	public String getSqlQuary(int index) {
		return sqlQuary[index-1];
	}

	public void setSqlQuary() {		
		int noOfquary = getSqlcount();
		this.sqlQuary = new String[noOfquary];
        for (int i = 0; i < noOfquary; i++) {        	
        	sqlQuary[i] = getProperty("oracle_quary." + (i + 1)).trim();
        }		
	}
		
}
