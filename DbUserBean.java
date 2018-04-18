package edu.uic.ids517.managedbeans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import javax.faces.event.ValueChangeEvent;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;



import edu.uic.ids517.beans.DbAccessBean;

//@ManagedBean(name="dbUserBean")
//@SessionScoped
public class DbUserBean {
	private String userName;
	private String password;
	private String host;
	private String dbms;
	private String message;
	private String schema;
	//private TableName tableName;
	private int size;
	private int columnSize;
	private Boolean tableListRendered;
	private Boolean columnListRendered;
	private String tName;
	private ArrayList<String> cName;
	private ArrayList<String> table;
	private String[] tableNm;
	private ArrayList<String> column;
	private ResultSet result;
	private Boolean renderSet;
	private Result rset;
	private Boolean isCnameEmpty;
	private String enterText;
	private String display;
	//@ManagedProperty(value="#{fileImp}")
	private FileImport fileImp;
	private String sqlQuery;
	private Boolean renderQuery;
	private int rowCount;
	private int colCount;
	private ArrayList<String> numericCol;
	private boolean renderMessage;
	private boolean renderDisplay;
	
	
	
	
	
	public boolean isRenderDisplay() {
		return renderDisplay;
	}
	public void setRenderDisplay(boolean renderDisplay) {
		this.renderDisplay = renderDisplay;
	}
	public boolean isRenderMessage() {
		return renderMessage;
	}
	public void setRenderMessage(boolean renderMessage) {
		this.renderMessage = renderMessage;
	}
	
	public ArrayList<String> getNumericCol() {
		return numericCol;
	}
	public void setNumericCol(ArrayList<String> numericCol) {
		this.numericCol = numericCol;
	}
	
	
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getColCount() {
		return colCount;
	}
	public void setColCount(int colCount) {
		this.colCount = colCount;
	}
	public Boolean getRenderQuery() {
		return renderQuery;
	}
	public void setRenderQuery(Boolean renderQuery) {
		this.renderQuery = renderQuery;
	}
	public String getSqlQuery() {
		return sqlQuery;
	}
	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}
	public String[] getTableNm() {
		return tableNm;
	}
	public void setTableNm(String[] tableNm) {
		this.tableNm = tableNm;
	}
	public FileImport getFileImp() {
		return fileImp;
	}
	public void setFileImp(FileImport fileImp) {
		this.fileImp = fileImp;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getEnterText() {
		return enterText;
	}
	public void setEnterText(String enterText) {
		this.enterText = enterText;
	}
	public Boolean getIsCnameEmpty() {
		return isCnameEmpty;
	}
	public void setIsCnameEmpty(Boolean isCnameEmpty) {
		this.isCnameEmpty = isCnameEmpty;
	}
	public Result getRset() {
		return rset;
	}
	public void setRset(Result rset) {
		this.rset = rset;
	}
	public Boolean getRenderSet() {
		return renderSet;
	}
	public void setRenderSet(Boolean renderSet) {
		this.renderSet = renderSet;
	}
	public ResultSet getResult() {
		return result;
	}
	public void setResult(ResultSet result) {
		this.result = result;
	}
	public ArrayList<String> getcName() {
		return cName;
	}
	public void setcName(ArrayList<String> cName) {
		this.cName = cName;
	}
	public int getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}
	public Boolean getColumnListRendered() {
		return columnListRendered;
	}
	public void setColumnListRendered(Boolean columnListRendered) {
		this.columnListRendered = columnListRendered;
	}
	public ArrayList<String> getColumn() {
		return column;
	}
	public void setColumn(ArrayList<String> column) {
		this.column = column;
	}
	public ArrayList<String> getTable() {
		return table;
	}
	public void setTable(ArrayList<String> table) {
		this.table = table;
	}
	public Boolean getTableListRendered() {
		return tableListRendered;
	}
	public void setTableListRendered(Boolean tableListRendered) {
		this.tableListRendered = tableListRendered;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	/*public TableName getTableName() {
		return tableName;
	}
	public void setTableName(TableName tableName) {
		this.tableName = tableName;
	}*/
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getDbms() {
		return dbms;
	}
	public void setDbms(String dbms) {
		this.dbms = dbms;
	}
	public String login() {
		setMessage("null");
		DbAccessBean dbAccessBean = new DbAccessBean();
		dbAccessBean.setDbUserBean(this);
		if(dbAccessBean.connect().equalsIgnoreCase("FAILURE")){
			setDisplay("Unable to Connect to Database. Please contact administrator");
			setMessage(dbAccessBean.getMessage());
			return "FAILURE";
		}
		setMessage("");
		return "SUCCESS";
	}
	public String tableName()
	{
		renderDisplay = false;
		renderMessage = false;
		renderSet = false;
		tableListRendered = true;
		renderQuery = false;
	     
	   // ResultSet result;
		setMessage("null");
		DbAccessBean dbAccessBean = new DbAccessBean();
		dbAccessBean.setDbUserBean(this);
		if(dbAccessBean.connect().equalsIgnoreCase("FAILURE")){
			setMessage("Unable to Connect to Database. Please contact administrator");
			return "FAILURE";
		}
		setMessage("");
		table = new ArrayList<String>();
		sqlQuery="SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema='"+getSchema()+"'";
		
		try {
			result = dbAccessBean.getStatement().executeQuery(sqlQuery);
			while(result.next())
			{
				table.add(result.getString(1));
				
			}
			size = table.size();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			renderMessage = true;
			message = e.getMessage();
			e.printStackTrace();
			tableListRendered = false;
		}
		catch (Exception e1)
		{
			renderMessage = true;
			message = e1.getMessage();
			e1.printStackTrace();
			tableListRendered = false;
		}
		
		return "SUCCESS";
		
	}
	
	public String getTableName(ValueChangeEvent event){
		tName = event.getNewValue().toString();
	   
		return columnName();
	}
	
	public String columnName()
	{
		renderMessage = false;
		renderDisplay = false;
		renderSet = false;
		columnListRendered = true;
		renderQuery=false;
	   
	  //  ResultSet result;
		setMessage("null");
		if(!tName.isEmpty())
		{
			DbAccessBean dbAccessBean = new DbAccessBean();
			dbAccessBean.setDbUserBean(this);
			if(dbAccessBean.connect().equalsIgnoreCase("FAILURE")){
				setMessage("Unable to Connect to Database. Please contact administrator");
				return "FAILURE";
			}
			setMessage("");
			column = new ArrayList<String>();
			sqlQuery="SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name ='"+tName+"'"+" AND table_schema='"+getSchema()+"'";
			
			try {
				result = dbAccessBean.getStatement().executeQuery(sqlQuery);
				while(result.next())
				{
					column.add(result.getString(1));
					
				}
				columnSize = column.size();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				renderMessage = true;
				message = e.getMessage();
				e.printStackTrace();
				columnListRendered = false;
			}
			catch (Exception e1)
			{
				renderMessage = true;
				message = e1.getMessage();
				e1.printStackTrace();
				columnListRendered = false;
			}
			
			return "SUCCESS";
		}
		else
		{
			renderMessage = true;
			display= "Please Select a table";
			return "FAILURE";
		}
	}
	
	public String displayTable()
	{
		
	    renderMessage = false;

		renderSet = true;
	    renderQuery = false;
	 //   ResultSet result;
		setMessage("null");
		DbAccessBean dbAccessBean = new DbAccessBean();
		dbAccessBean.setDbUserBean(this);
		if(dbAccessBean.connect().equalsIgnoreCase("FAILURE")){
			setMessage("Unable to Connect to Database. Please contact administrator");
			return "FAILURE";
		}
		setMessage("");
		isCnameEmpty = false;
		if(cName.isEmpty() )
		{
			isCnameEmpty = true;
		}
		
		sqlQuery="SELECT * FROM "+ gettName();
		try {
			  renderQuery = true;
			result = dbAccessBean.getStatement().executeQuery(sqlQuery);
			rset =  ResultSupport.toResult(result);
			rowCount = rset.getRowCount();
			String[]cNm = rset.getColumnNames();
			colCount = cNm.length;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			renderMessage = true;
			message = e.getMessage();
			e.printStackTrace();
			renderSet = false;
		}
		catch (Exception e1)
		{
			renderMessage = true;
			message = e1.getMessage();
			e1.printStackTrace();
			renderSet = false;
		}
		
		return "SUCCESS";
		
		
	}
	
	public String createTable()
	{
		try {
			
			if(!enterText.isEmpty() & !enterText.startsWith("SELECT"))
			{
		
				renderQuery = false;
				renderMessage = false;
				setMessage("null");
				DbAccessBean dbAccessBean = new DbAccessBean();
				dbAccessBean.setDbUserBean(this);
				if(dbAccessBean.connect().equalsIgnoreCase("FAILURE")){
					setMessage("Unable to Connect to Database. Please contact administrator");
					return "FAILURE";
				}
				setMessage("");
				
				
						if(enterText.startsWith("SELECT")||enterText.startsWith("SHOW"))
						{
							dbAccessBean.getStatement().executeQuery(enterText)	;
						}
						else
						{
							dbAccessBean.getStatement().executeUpdate(enterText);
							tableName();
						}
						renderDisplay = true;
						display= "Table Created";
			}
			else
			{
				renderMessage = true;
				renderDisplay = false;
				message = "Please Enter a Create Query";
			}
			
				

		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			renderMessage = true;
			renderDisplay = false;
			message = e.getMessage();
			e.printStackTrace();
		}
		catch (Exception e1)
		{
			renderMessage = true;
			renderDisplay = false;
			message = e1.getMessage();
			e1.printStackTrace();
		}
		
		return "SUCCESS";
		
		
	}
	
	public String dropTable()
	{
		try {
			
			if(!tName.isEmpty())
			{
				renderMessage = false;
				renderDisplay = false;
				renderQuery=true;
				setMessage("null");
				DbAccessBean dbAccessBean = new DbAccessBean();
				dbAccessBean.setDbUserBean(this);
				if(dbAccessBean.connect().equalsIgnoreCase("FAILURE")){
					setMessage("Unable to Connect to Database. Please contact administrator");
					return "FAILURE";
				}
				setMessage("");
			    sqlQuery="DROP TABLE "+tName;
				
						
						
						dbAccessBean.getStatement().executeUpdate(sqlQuery);
						tableName();
			}
			else
			{
				renderMessage = true;
				message= "Please Select a Table to be Dropped.";
			}

		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			renderMessage = true;
			message = e.getMessage();
			e.printStackTrace();
		}
		catch (Exception e1)
		{
			renderMessage = true;
			message = e1.getMessage();
			e1.printStackTrace();
		}
		
		return "SUCCESS";
		
	}
	
	public String createTableInDatabase()
	{
		renderQuery = false;
		setMessage("null");
		DbAccessBean dbAccessBean = new DbAccessBean();
		dbAccessBean.setDbUserBean(this);
		if(dbAccessBean.connect().equalsIgnoreCase("FAILURE")){
			setMessage("Unable to Connect to Database. Please contact administrator");
			return "FAILURE";
		}
		setMessage("");
	    sqlQuery="Create table S17g305_"+fileImp.getFileLabel()+" ("+fileImp.getColumnList()+")";
		System.out.println(sqlQuery);
		try {
				
				
				dbAccessBean.getStatement().executeUpdate(sqlQuery);
				tableName();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			message = e.getMessage();
			e.printStackTrace();
		}
		catch (Exception e1)
		{
			message = e1.getMessage();
			e1.printStackTrace();
		}
		
		
		
		return "SUCCESS";
		
	}
	public void tabelNameChanged(ValueChangeEvent e)
	{
		//assign new value to localeCode
		tName = e.getNewValue().toString();

	}
		
}
