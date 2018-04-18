package edu.uic.ids517.managedbeans;


import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import javax.faces.context.FacesContext;

import edu.uic.ids517.beans.DbAccessBean;

public class ExtractData {
	
	private String fileType;
	private String message;
	private DbUserBean dbUserBean;
	private String display;
	private String filePath;
	private FacesContext facesContext;
	private ResultSet result;
	private ResultSetMetaData rsmd;
	private boolean renderMessage;
	private boolean renderDisplay;
	
	public boolean isRenderMessage() {
		return renderMessage;
	}

	public void setRenderMessage(boolean renderMessage) {
		this.renderMessage = renderMessage;
	}

	public boolean isRenderDisplay() {
		return renderDisplay;
	}

	public void setRenderDisplay(boolean renderDisplay) {
		this.renderDisplay = renderDisplay;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DbUserBean getDbUserBean() {
		return dbUserBean;
	}

	public void setDbUserBean(DbUserBean dbUserBean) {
		this.dbUserBean = dbUserBean;
	}

	public String dataToFile()
	{
		try
		{
		
			renderMessage= false;
			renderDisplay= false;
			dbUserBean.setRenderQuery(false);
			
			if(fileType.equalsIgnoreCase("CSV"))
			{
				if(dbUserBean.getcName().isEmpty() &  dbUserBean.gettName().isEmpty() & fileType.isEmpty())
				{
					renderMessage = true;
					message = "Please Select a Table, Number of Columns and File Type  for exporting the data.";
				}
	
				String colName = dbUserBean.getcName().toString();
				colName = colName.replace("[", "");
				colName = colName.replace("]", "");
				System.out.print(colName);
				 DbAccessBean dbAccessBean = new DbAccessBean();
					dbAccessBean.setDbUserBean(dbUserBean);
					if(dbAccessBean.connect().equalsIgnoreCase("FAILURE")){
						setMessage("Unable to Connect to Database. Please contact administrator");
						
					}
					setMessage("");
					facesContext = FacesContext.getCurrentInstance();
					filePath = facesContext.getExternalContext().getRealPath("/temp");
					String splitFilepath[] = filePath.split("\\\\");
					String filePathUpdated ="";
					for(int i= 0; i<splitFilepath.length;i++)
					{
						String filePathUpdt = splitFilepath[i]+"/";
						filePathUpdated =filePathUpdated+ filePathUpdt;
					}
					
					String fileName = filePathUpdated+dbUserBean.gettName()+".csv";
					FileWriter writer = new FileWriter(fileName);
					
					String sqlQuery="SELECT "+colName+" FROM "+dbUserBean.gettName();
					System.out.println(sqlQuery);
					System.out.println(filePath);
					result = dbAccessBean.getStatement().executeQuery(sqlQuery);
					rsmd = result.getMetaData();
					int j;
					for( j=1; j<dbUserBean.getcName().size(); j++)
					{
						writer.append(rsmd.getColumnName(j));
						writer.append(',');
						
					}
					writer.append(rsmd.getColumnName(j));
					writer.append(System.getProperty("line.separator"));
					while(result.next())
					{
						int i;
						
						for(i =1;i<dbUserBean.getcName().size(); i++)
						{
								
								writer.append(result.getString(i));
								writer.append(',');
								
						}
						writer.append(result.getString(i));
								
						writer.append(System.getProperty("line.separator"));
					}
					
					writer.flush();
		            writer.close();
		            System.out.println("CSV File is created successfully.");
				}
			
			if(fileType.equalsIgnoreCase("TAB"))
			{
				if(dbUserBean.getcName().isEmpty() &  dbUserBean.gettName().isEmpty() & fileType.isEmpty())
				{
					renderMessage = true;
					message = "Please Select a Table, Number of Columns and File Type  for exporting the data.";
				}
				String colName = dbUserBean.getcName().toString();
				colName = colName.replace("[", "");
				colName = colName.replace("]", "");
				System.out.print(colName);
				 DbAccessBean dbAccessBean = new DbAccessBean();
					dbAccessBean.setDbUserBean(dbUserBean);
					if(dbAccessBean.connect().equalsIgnoreCase("FAILURE")){
						setMessage("Unable to Connect to Database. Please contact administrator");
						
					}
					setMessage("");
					facesContext = FacesContext.getCurrentInstance();
					filePath = facesContext.getExternalContext().getRealPath("/temp");
					String splitFilepath[] = filePath.split("\\\\");
					String filePathUpdated ="";
					for(int i= 0; i<splitFilepath.length;i++)
					{
						String filePathUpdt = splitFilepath[i]+"/";
						filePathUpdated =filePathUpdated+ filePathUpdt;
					}
					
					String fileName = filePathUpdated+dbUserBean.gettName()+".txt";
					FileWriter writer = new FileWriter(fileName);
					
					String sqlQuery="SELECT "+colName+" FROM "+dbUserBean.gettName();
					System.out.println(sqlQuery);
					System.out.println(filePath);
					result = dbAccessBean.getStatement().executeQuery(sqlQuery);
					rsmd = result.getMetaData();
					int j;
					for( j=1; j<dbUserBean.getcName().size(); j++)
					{
						writer.append(rsmd.getColumnName(j));
						writer.append('\t');
						
					}
					writer.append(rsmd.getColumnName(j));
					writer.append(System.getProperty("line.separator"));
				
					while(result.next())
					{
						int i;
						for(i =1;i<dbUserBean.getcName().size(); i++)
						{
						
								writer.append(result.getString(i));
								writer.append('\t');
								
						}
						writer.append(result.getString(i));
								
						writer.append(System.getProperty("line.separator"));
					}
					
					writer.flush();
		            writer.close();
		            System.out.println("CSV File is created successfully.");
				}
			renderDisplay = true;
			renderMessage = false;
			display= "File EXTRACTED SUCCESSFULLY AT:" + filePath;
			return "SUCCESS";	
			}
			
		catch(SQLException e1)
		{
			renderMessage = true;
			renderDisplay = false;
			message = e1.getMessage();
			return "FAIL";
		}
		catch(Exception e)
		{
			renderMessage = true;
			renderDisplay = false;
			message = e.getMessage();
			return "FAIL";
		}
	}

}
