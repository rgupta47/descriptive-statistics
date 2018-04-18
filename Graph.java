package edu.uic.ids517.managedbeans;


import java.io.File;

import java.sql.ResultSet;
import java.sql.SQLException;


import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;


import org.apache.commons.math3.stat.regression.SimpleRegression;

import org.jfree.chart.ChartFactory;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.mysql.jdbc.ResultSetMetaData;

import edu.uic.ids517.beans.DbAccessBean;


public class Graph {
	
	private ActionBeanMath actionBeanMath;
	private DbUserBean dbUserBean;
	private String message;
	private XYSeriesCollection xySeriesVariable;
	private XYSeriesCollection xyTimeSeriesCollection; 
	private XYSeries predictorSeries;
	private XYSeries responseSeries;
	private XYSeries xySeries;
	private ResultSet resultSet;
	private ResultSetMetaData resultSetMetaData;
	private String scatterPlotFile;
	private boolean renderChart;
	private String graphType;
	private boolean renderMessage;
	
	

	

	public boolean isRenderMessage() {
		return renderMessage;
	}

	public void setRenderMessage(boolean renderMessage) {
		this.renderMessage = renderMessage;
	}

	public String getGraphType() {
		return graphType;
	}

	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}

	public String getScatterPlotFile() {
		return scatterPlotFile;
	}

	public void setScatterPlotFile(String scatterPlotFile) {
		this.scatterPlotFile = scatterPlotFile;
	}

	public ActionBeanMath getActionBeanMath() {
		return actionBeanMath;
	}

	public void setActionBeanMath(ActionBeanMath actionBeanMath) {
		this.actionBeanMath = actionBeanMath;
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

	public boolean isRenderChart() {
		return renderChart;
	}

	public void setRenderChart(boolean renderChart) {
		this.renderChart = renderChart;
	}
	
	public Graph()
	{
		xySeries= new XYSeries("Random");
		xySeriesVariable= new XYSeriesCollection();
		xyTimeSeriesCollection = new XYSeriesCollection();
	 	predictorSeries= new XYSeries("Predictor");
	 	responseSeries = new XYSeries("Response");	
	}
	
	public String ScatterPlot() throws SQLException
	{
		try {
				if(!graphType.isEmpty() & !actionBeanMath.getrVariable().isEmpty() & !actionBeanMath.getpVariable().isEmpty() )
				{
						
						responseSeries.clear();
						predictorSeries.clear();
						xySeries.clear();
						xySeriesVariable.removeAllSeries();
						xyTimeSeriesCollection.removeAllSeries();
						renderChart = false;
						renderMessage = false;
						actionBeanMath.setRenderErr(false);
						System.out.println("Entered the function");
						
						System.out.println(actionBeanMath.getpValue());
						String sqlQuery = "select " + actionBeanMath.getpVariable() + ", " + actionBeanMath.getrVariable() + 
								" from " + dbUserBean.gettName();
						System.out.println(sqlQuery);
						DbAccessBean dbAccessBean = new DbAccessBean();
						dbAccessBean.setDbUserBean(dbUserBean);
						if(dbAccessBean.connect().equalsIgnoreCase("FAILURE"))
						{
							setMessage("Unable to Connect to Database. Please contact administrator");
						}
						resultSet = dbAccessBean.getStatement().executeQuery(sqlQuery);
						if(resultSet!= null)
						{
							resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();
							String predictorName = resultSetMetaData.getColumnTypeName(1);
							String responseName = resultSetMetaData.getColumnTypeName(2);
							List<Double> predictorList = new ArrayList<Double>();
							List<Double> responseList = new ArrayList<Double>();
							while(resultSet.next())
							{
								switch(predictorName.toLowerCase())
								{
									case "int":
										predictorList.add((double) resultSet.getInt(1));
										break;
									case "smallint":
										predictorList.add((double) resultSet.getInt(1));
										break;
									case "float":
										predictorList.add((double)resultSet.getFloat(1));
										break;
									case "double":
										predictorList.add((double) resultSet.getDouble(1));
										break;
									case "long":
										predictorList.add((double) resultSet.getLong(1));
										break;
									default:
										predictorList.add((double) resultSet.getDouble(1));
										break;
								}
								switch(responseName.toLowerCase())
								{
									case "int":
										responseList.add((double) resultSet.getInt(2));
										break;
									case "smallint":
										responseList.add((double) resultSet.getInt(2));
										break;
									case "float":
										responseList.add((double)resultSet.getFloat(2));
										break;
									case "double":
										responseList.add((double) resultSet.getDouble(2));
										break;
									case "long":
										responseList.add((double) resultSet.getLong(2));
										break;
									default:
										responseList.add((double) resultSet.getDouble(2));
										break;
								}
							}
							double[] predictorArray = new double[predictorList.size()];
							for(int i=0;i<predictorList.size();i++)
							{
								predictorArray[i]= (double)predictorList.get(i);
								predictorSeries.add(i+1, (double)predictorList.get(i));
							}
							double[] responseArray= new double[responseList.size()];
							for(int i=0;i<responseList.size();i++)
							{
								responseArray[i]= (double)responseList.get(i);
								responseSeries.add(i+1, (double)responseList.get(i));
							}
							xyTimeSeriesCollection.addSeries(predictorSeries);
							xyTimeSeriesCollection.addSeries(responseSeries);
							SimpleRegression sr = new SimpleRegression();
							if(responseArray.length > predictorArray.length)
							{
								for(int i=0;i<predictorArray.length;i++)
								{
									sr.addData(predictorArray[i], responseArray[i]);
									xySeries.add(predictorArray[i], responseArray[i]);
								}
							}
							else
							{
								for(int i=0;i<responseArray.length;i++)
								{
									sr.addData(predictorArray[i], responseArray[i]);
									xySeries.add(predictorArray[i], responseArray[i]);
								}
							}
							xySeriesVariable.addSeries(xySeries);
						
							System.out.println("Entered the function");
							renderChart = false;
							String predictor = actionBeanMath.getpVariable();
							String response = actionBeanMath.getrVariable();
							System.out.println(predictor);
						
							
									 
							FacesContext context = FacesContext.getCurrentInstance();
							String path = context.getExternalContext().getRealPath("/temp");
							System.out.println(path);
							// System.out.println("path: " + path);
							File out = null;
							
							// setup temporary naming system
							out = new File(path+"/" +
							dbUserBean.getUserName()+"_"+dbUserBean.gettName()+".png");
							// use createPieChart() etc methods similar to below
							
							if(graphType.equals("Scatter Plot"))
							{
								JFreeChart chart = ChartFactory.createScatterPlot(
										"Scatter Plot B/W Response and Predictor Variable",
										predictor, response, xySeriesVariable);
								String splitFilepath[] = path.split("\\\\");
								String filePathUpdated ="";
								for(int i= 0; i<splitFilepath.length;i++)
								{
									String filePathUpdt = splitFilepath[i]+"/";
									filePathUpdated =filePathUpdated+ filePathUpdt;
								}
								
								scatterPlotFile = filePathUpdated+dbUserBean.getUserName()+"_"+dbUserBean.gettName()+".png";
								System.out.println(scatterPlotFile);
								ChartUtilities.saveChartAsPNG(out, chart, 600, 450);
								
								
								renderChart = true;
								return "SUCCESS";
							}
							else if(graphType.equals("Time Series"))
							{
								String xAxis = predictor +" V/S "+ response;
								JFreeChart chart = ChartFactory.createTimeSeriesChart("Time Series", "Time",xAxis , xyTimeSeriesCollection);
			
								 String splitFilepath[] = path.split("\\\\");
									String filePathUpdated ="";
									for(int i= 0; i<splitFilepath.length;i++)
									{
										String filePathUpdt = splitFilepath[i]+"/";
										filePathUpdated =filePathUpdated+ filePathUpdt;
									}
								 scatterPlotFile = filePathUpdated+dbUserBean.getUserName()+"_"+dbUserBean.gettName()+".png";
									System.out.println(scatterPlotFile);
									ChartUtilities.saveChartAsPNG(out, chart, 600, 450);
									
									
									renderChart = true;
									return "SUCCESS";
							}
							else
							{
								renderMessage = true;
								return"FAILURE";
							}
							
						}
						else
						{
							renderChart= false;
							return "FAILURE";
						}
				}
				else
				{
					actionBeanMath.setRenderErr(false);
					renderChart = false;
					renderMessage = true;
					message = "Please Select a Response Variable, Predictor Variable and a Chart Type";
					return "FAILURE";
				}
		}
				catch (Exception e ) 
				{
					renderMessage= true;
					message = e.getMessage();
					renderChart = false;
					return "FAIL";
				
				}
			
		}
	
	public void reset()
	{
		actionBeanMath.setRenderRegress(false);
		actionBeanMath.setRenderStats(false);
		renderChart=false;
		actionBeanMath.setrVariable(null);
		actionBeanMath.setpVariable(null);
		graphType = null;
		actionBeanMath.setRenderErr(false) ;
		renderMessage= false;
		
		
		
	}
	
	
	
	}




