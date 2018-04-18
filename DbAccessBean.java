package edu.uic.ids517.beans;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.uic.ids517.managedbeans.DbUserBean;


public class DbAccessBean {
	private Statement statement;
	private ResultSet resultSet;
	private Connection connection;
	private ResultSetMetaData resultSetMetaData;
	private DatabaseMetaData databaseMetaData;
	private DbUserBean dbUserBean;
	private String message;
	private String ipAddress;
	
	
	
	

	public Statement getStatement() {
		return statement;
	}
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	public ResultSet getResultSet() {
		return resultSet;
	}
	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public ResultSetMetaData getResultSetMetaData() {
		return resultSetMetaData;
	}
	public void setResultSetMetaData(ResultSetMetaData resultSetMetaData) {
		this.resultSetMetaData = resultSetMetaData;
	}
	public DatabaseMetaData getDatabaseMetaData() {
		return databaseMetaData;
	}
	public void setDatabaseMetaData(DatabaseMetaData databaseMetaData) {
		this.databaseMetaData = databaseMetaData;
	}
	public DbUserBean getDbUserBean() {
		return dbUserBean;
	}
	public void setDbUserBean(DbUserBean dbUserBean) {
		this.dbUserBean = dbUserBean;
	}
	
	public String connect(){
		try{

			String dbms = dbUserBean.getDbms();
			String schema = dbUserBean.getSchema();
			String url = null;
			
			if(dbms.equalsIgnoreCase("MySQL"))
			{
				Class.forName("com.mysql.jdbc.Driver");	
				url = "jdbc:mysql://" + dbUserBean.getHost() + ":3306/" + schema;
			}
			else if(dbms.equalsIgnoreCase("DB2"))
			{
				Class.forName("com.ibm.db2.jcc.DB2Driver");	
				url = "jdbc:db2://" + dbUserBean.getHost() + ":50000/" + schema;
			}
			else if(dbms.equalsIgnoreCase("oracle"))
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");	
				url = "jdbc:oracle:thin:@//" + dbUserBean.getHost() + ":1521:" + schema;
			}
			
		    setConnection(DriverManager.getConnection(url,dbUserBean.getUserName(),dbUserBean.getPassword()));		  
		    setStatement(getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_UPDATABLE));
		    accessLog();
		    return "SUCCESS";
		    
		}
		catch(ClassNotFoundException e){
			message = e.getMessage();
			return "FAILURE";
		}
		catch(SQLException e){
			message = e.getMessage();
			return "FAILURE";
		}
		catch(Exception e){
			message = e.getMessage();
			return "FAILURE";
		}
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ResultSet processSelect(String sqlQuery){
		try {
			//connect();
			resultSet = getStatement().executeQuery(sqlQuery);
			return resultSet;
			
		} catch (SQLException e) {
			message = e.getMessage();
			return null;
		}
		catch (Exception e) {
			message= e.getMessage();
			return null;
		}
	}
	
	public String accessLog()
	{
		try
		{
			Timestamp t = new Timestamp(System.currentTimeMillis());
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		       String sessionId = session.getId();
		       System.out.println(sessionId);
				String sqlQuery = "Create table if not exists s17g209_Accesslog (LogID INT(6)"
						+ " NOT NULL AUTO_INCREMENT , Username char(50) not null, "
						+ "dbms char(50) ,DateTime char(50), "
						+ "Activity char(50),"
						+ "IPAddress char(50), SessionID char(50), PRIMARY KEY (LogID)) "
						+ "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;";
				
				if (ipAddress.equals(null))
				{
					ServletRequest request = null;
					ipAddress = request.getRemoteAddr();
					System.out.println(ipAddress);
				}
				Statement s =  getConnection().createStatement();
				s.executeUpdate(sqlQuery);
				String insertQuery = "Insert into s17g305_Accesslog (Username,dbms," + "DateTime,Activity,IPAddress,SessionID)"
						+ " values ( '" + dbUserBean.getUserName() + "','" + dbUserBean.getDbms() + "','"
						+ t.toString() + "','" + "Login" + "','" + ipAddress + "','" + sessionId + "')";
				Statement s1 =  getConnection().createStatement();
				s1.executeUpdate(insertQuery);
			return "SUCCESS";
		}
		catch (Exception e)
		{
			message = e.getMessage();
			return "FAILURE";
		}
	}
	
	
}
