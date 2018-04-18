package edu.uic.ids517.managedbeans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.uic.ids517.beans.DbAccessBean;

public class Computation {

	private String colName;
	private String compColName;
	private List<Double> valueColumn;
	private List<Double> computeColumn;
	private DbUserBean dbUserBean;
	private String message;
	private boolean renderMessage;
	private boolean renderDisplay;
	private String display;
	
	
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
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getCompColName() {
		return compColName;
	}
	public void setCompColName(String compColName) {
		this.compColName = compColName;
	}
	public DbUserBean getDbUserBean() {
		return dbUserBean;
	}
	public void setDbUserBean(DbUserBean dbUserBean) {
		this.dbUserBean = dbUserBean;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getRenderCompute() {
		return renderCompute;
	}
	public void setRenderCompute(Boolean renderCompute) {
		this.renderCompute = renderCompute;
	}


	private Boolean renderCompute;
	private ResultSet result;

	public void renderComp()
	{
		renderCompute = true;
		renderMessage= false;
		renderDisplay = false;
		dbUserBean.setRenderSet(false);
		 dbUserBean.setRenderQuery(false);
   	  dbUserBean.setRenderDisplay(false);
	}
	public void notRender()
	{
		renderCompute = false;
		renderMessage= false;
		renderDisplay=false;
		
	}
	
	
       public String compute() throws SQLException
       {
    	 
    	  try
    	  {
        	  
        	 
    		  if(!colName.isEmpty() & !compColName.isEmpty())
    		  {
		    		  dbUserBean.setRenderMessage(false);
		    		  
		    		  dbUserBean.setRenderSet(false);
		    		  renderMessage=false;
		    		  renderDisplay = false;
		    		  	valueColumn = new ArrayList<Double>();
		    		  	computeColumn = new ArrayList<Double>();
		    		    List<Integer> ID = new ArrayList<Integer>();
					    DbAccessBean dbAccessBean = new DbAccessBean();
						dbAccessBean.setDbUserBean(dbUserBean);
						if(dbAccessBean.connect().equalsIgnoreCase("FAILURE"))
						{
											return "FAILURE";
						}
						
						String sqlQuery = "SELECT "+colName+" FROM "+dbUserBean.gettName();
						System.out.println(sqlQuery);
						result = dbAccessBean.getStatement().executeQuery(sqlQuery);
						
						while(result.next())
						{
							
							valueColumn.add(result.getDouble(1));
						}
						System.out.println("value column data is:"+valueColumn);
						double sum = 0.0;
						for(int i=0; i< valueColumn.size()-1;i++)
						{
							computeColumn.add(sum);
							
							sum = Math.log(valueColumn.get(i+1)) - Math.log(valueColumn.get(i)) ;
							
							
						}
						computeColumn.add(sum);
						System.out.println("computed data is:"+computeColumn);
						Statement s = dbAccessBean.getConnection().createStatement();
							sqlQuery = "SELECT ID FROM "+dbUserBean.gettName();
						    result = s.executeQuery(sqlQuery);
						    while(result.next())
						    {
						    	ID.add(result.getInt(1));
						    }
			
						System.out.print(ID);
						Statement s1 = dbAccessBean.getConnection().createStatement();
						for(int i=0;i<valueColumn.size();i++)
						{
							sqlQuery = "UPDATE "+dbUserBean.gettName()+" SET "+compColName+" = "+computeColumn.get(i)+" WHERE ID = "+ID.get(i);
							
							System.out.println(sqlQuery);
							s1.executeUpdate(sqlQuery);
						}
						renderDisplay = true;
						display= "Column "+compColName+ " Computed Successfully";
					    return "SUCCESS";
    		  }
    		  else
    		  {
    			  renderMessage = true;
    			  message = "Please Select a Source and Destination Column.";
    			  return "FAILURE";
    		  }
						
    	 }
    	  catch(Exception e)
    	  {
    		  renderDisplay= false;
    		  renderMessage=true;
    		  message = e.getMessage();
    		  return "FAILURE";
    	  }
    	  
    	  
       }
       public String reset()
 	  {
    	  renderMessage = false;
    	  renderDisplay = false;
    	  dbUserBean.setRenderMessage(false);
    	  dbUserBean.setRenderSet(false);
    	  dbUserBean.setRenderDisplay(false);
    	  renderCompute = false;
    	  dbUserBean.setRenderQuery(false);
    	  
 		  return "SUCCESS";
 	  }
	
}
