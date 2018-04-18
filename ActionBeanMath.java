package edu.uic.ids517.managedbeans;

import java.lang.Math;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;



import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import org.apache.commons.math3.stat.regression.RegressionResults;
import org.apache.commons.math3.stat.regression.SimpleRegression;

//import com.mysql.jdbc.ResultSetMetaData;

import edu.uic.ids517.beans.DbAccessBean;
import edu.uic.ids517.beans.DescrStatsBean;


public class ActionBeanMath {

private DescrStatsBean descrStatsBean;
private List <DescrStatsBean> descrStatsBeanList;
private List <String> columnNames;
private String descrStats [];

private boolean renderStats;
private boolean renderRegression;
private String message;
private String display;
private DbUserBean dbUserBean;
private ResultSet result;
private ArrayList<Double> table;
private ArrayList<String> tableString;
private Double[] datavalues;
private  ResultSetMetaData rsmd;
private String cName;
private ArrayList<String> colName;
private String pVariable;
private String rVariable;

private PearsonsCorrelation c;
private SimpleRegression sr;

private Boolean renderRegress;
private Boolean renderErr;



private double correlation;
private double intercept;
private double slope;
private double interceptStdErr;
private double slopeStdErr;
private double interceptTStat;
private double slopeTStat;
private double interceptPValue;
private double slopePValue;
private String regressEqn;
private double rSquare;
private double rSquareAdjusted;
private double modelStdErr;
private double regressSumSqr;
private double sumSqrErr;
private double tSumSqr;
private double meanSquare;
private double fValue;
private double meanSqrErr;
private double pValue;
private int tDF,reDF,pDF;



public Boolean getRenderErr() {
	return renderErr;
}
public void setRenderErr(Boolean renderErr) {
	this.renderErr = renderErr;
}
public int gettDF() {
	return tDF;
}
public void settDF(int tDF) {
	this.tDF = tDF;
}
public int getReDF() {
	return reDF;
}
public void setReDF(int reDF) {
	this.reDF = reDF;
}
public int getpDF() {
	return pDF;
}
public void setpDF(int pDF) {
	this.pDF = pDF;
}
public double getpValue() {
	return pValue;
}
public void setpValue(double pValue) {
	this.pValue = pValue;
}
public double getMeanSqrErr() {
	return meanSqrErr;
}
public void setMeanSqrErr(double meanSqrErr) {
	this.meanSqrErr = meanSqrErr;
}
public double getfValue() {
	return fValue;
}
public void setfValue(double fValue) {
	this.fValue = fValue;
}
public double getMeanSquare() {
	return meanSquare;
}
public void setMeanSquare(double meanSquare) {
	this.meanSquare = meanSquare;
}
public double getRegressSumSqr() {
	return regressSumSqr;
}
public void setRegressSumSqr(double regressSumSqr) {
	this.regressSumSqr = regressSumSqr;
}
public double getSumSqrErr() {
	return sumSqrErr;
}
public void setSumSqrErr(double sumSqrErr) {
	this.sumSqrErr = sumSqrErr;
}
public double gettSumSqr() {
	return tSumSqr;
}
public void settSumSqr(double tSumSqr) {
	this.tSumSqr = tSumSqr;
}
public double getrSquare() {
	return rSquare;
}
public void setrSquare(double rSquare) {
	this.rSquare = rSquare;
}
public double getrSquareAdjusted() {
	return rSquareAdjusted;
}
public void setrSquareAdjusted(double rSquareAdjusted) {
	this.rSquareAdjusted = rSquareAdjusted;
}
public double getModelStdErr() {
	return modelStdErr;
}
public void setModelStdErr(double modelStdErr) {
	this.modelStdErr = modelStdErr;
}
public Double[] getDatavalues() {
	return datavalues;
}
public void setDatavalues(Double[] datavalues) {
	this.datavalues = datavalues;
}
public double getCorrelation() {
	return correlation;
}
public void setCorrelation(double correlation) {
	this.correlation = correlation;
}
public double getIntercept() {
	return intercept;
}
public void setIntercept(double intercept) {
	this.intercept = intercept;
}
public double getSlope() {
	return slope;
}
public void setSlope(double slope) {
	this.slope = slope;
}
public double getInterceptStdErr() {
	return interceptStdErr;
}
public void setInterceptStdErr(double interceptStdErr) {
	this.interceptStdErr = interceptStdErr;
}
public double getSlopeStdErr() {
	return slopeStdErr;
}
public void setSlopeStdErr(double slopeStdErr) {
	this.slopeStdErr = slopeStdErr;
}
public double getInterceptTStat() {
	return interceptTStat;
}
public void setInterceptTStat(double interceptTStat) {
	this.interceptTStat = interceptTStat;
}
public double getSlopeTStat() {
	return slopeTStat;
}
public void setSlopeTStat(double slopeTStat) {
	this.slopeTStat = slopeTStat;
}
public double getInterceptPValue() {
	return interceptPValue;
}
public void setInterceptPValue(double interceptPValue) {
	this.interceptPValue = interceptPValue;
}
public double getSlopePValue() {
	return slopePValue;
}
public void setSlopePValue(double slopePValue) {
	this.slopePValue = slopePValue;
}
public String getRegressEqn() {
	return regressEqn;
}
public void setRegressEqn(String regressEqn) {
	this.regressEqn = regressEqn;
}

public Boolean getRenderRegress() {
	return renderRegress;
}
public void setRenderRegress(Boolean renderRegress) {
	this.renderRegress = renderRegress;
}
public String getpVariable() {
	return pVariable;
}
public void setpVariable(String pVariable) {
	this.pVariable = pVariable;
}
public String getrVariable() {
	return rVariable;
}
public void setrVariable(String rVariable) {
	this.rVariable = rVariable;
}
public ArrayList<String> getColName() {
	return colName;
}
public void setColName(ArrayList<String> colName) {
	this.colName = colName;
}
public void setDescrStatsBean(DescrStatsBean descrStatsBean) {
	this.descrStatsBean = descrStatsBean;
}
public List<DescrStatsBean> getDescrStatsBeanList() {
	return descrStatsBeanList;
}
public void setDescrStatsBeanList(List<DescrStatsBean> descrStatsBeanList) {
	this.descrStatsBeanList = descrStatsBeanList;
}
public String getcName() {
	return cName;
}
public void setcName(String cName) {
	this.cName = cName;
}
public ResultSetMetaData getRsmd() {
	return rsmd;
}
public void setRsmd(ResultSetMetaData rsmd) {
	this.rsmd = rsmd;
}
public ResultSet getResult() {
	return result;
}
public void setResult(ResultSet result) {
	this.result = result;
}
public DbUserBean getDbUserBean() {
	return dbUserBean;
}
public void setDbUserBean(DbUserBean dbUserBean) {
	this.dbUserBean = dbUserBean;
}
public String getDisplay() {
	return display;
}
public void setDisplay(String display) {
	this.display = display;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}

// constructor to initialize the variables.
public ActionBeanMath() {

	renderRegress = false;
	renderErr = false;
	descrStatsBean = null;
	//descrStatsBean = new DescrStatsBean();

	sr = new SimpleRegression();
	c = new PearsonsCorrelation();

	descrStatsBeanList = new ArrayList<DescrStatsBean>();
	renderStats = false;
	renderRegression = false;
	columnNames = null;
	descrStats = DescrStatsBean.getColumnNames() ;
	columnNames = new ArrayList<String>(descrStats.length);
	for(int i = 0; i < descrStats.length; i++) 
	{
		columnNames.add(descrStats[i]);
	}


}

public String generateDescriptiveStatistics() 
{
	try
		{
			if(!colName.isEmpty())
			{
				
				renderErr= false;
				renderStats = false;
				
				for(int i =0;i<colName.size();i++)
				{
					cName = colName.get(i);
					descrStatsBean = new DescrStatsBean();
					table = new ArrayList<Double>();
					tableString = new ArrayList<String>();
					
					DbAccessBean dbAccessBean = new DbAccessBean();
					dbAccessBean.setDbUserBean(dbUserBean);
					if(dbAccessBean.connect().equalsIgnoreCase("FAILURE"))
					{
						setMessage("Unable to Connect to Database. Please contact administrator");
					}
					
					
					String sqlQuery = "SELECT "+cName +" FROM "+dbUserBean.gettName();
					System.out.println(sqlQuery);
					result = dbAccessBean.getStatement().executeQuery(sqlQuery);
					String type = result.getMetaData().getColumnTypeName(1);
					System.out.println(type);
					while(result.next())
					{
						
						if(type.equalsIgnoreCase("double")||type.equalsIgnoreCase("int"))
						{
							table.add(result.getDouble(cName));
							datavalues = table.toArray(new Double[table.size()]);
							 
						}
						else 
						{
							tableString.add(result.getString(cName));
							datavalues = null;
						}
					}
					
					if(datavalues.length != 0)
					{
						int numberObs = datavalues.length;
						double[] datavalues1 = new double[numberObs];
						for(int j=0;j<numberObs;j++)
						{
							 datavalues1[j] =  datavalues[j];
							 System.out.println(datavalues1[j]);
						}
						
						descrStatsBean.setDataset(dbUserBean.gettName());
						descrStatsBean.setVariable(cName);
						compute(numberObs,datavalues1);
					
					}
				}
				renderStats = true;
			}
			else
			{
				renderStats = false;
				renderErr = true;
				display = "Please select a Column Name.";
			}
				
	}
	
	catch (Exception e)
	{
		message = e.getMessage();
		display = "Please Select a Numeric Column";
		renderStats = false;
		renderErr = true;
	}
	
	return "SUCCESS";
}

public void compute(int numberObs, double[] datavalues2) throws Exception
{
	
	descrStatsBean.setNumberObs(numberObs);
    descrStatsBean.setMinValue(StatUtils.min(datavalues2));
	descrStatsBean.setMaxValue( StatUtils.max(datavalues2));
	descrStatsBean.setMean(StatUtils.mean(datavalues2));
	descrStatsBean.setVariance( StatUtils.variance(datavalues2, StatUtils.mean(datavalues2)));
	descrStatsBean.setStandardDeviation(Math.sqrt(descrStatsBean.getVariance()));
	descrStatsBean.setMedian(StatUtils.percentile(datavalues2, 50.0));
	descrStatsBean.setQ1(StatUtils.percentile(datavalues2, 25.0));
	descrStatsBean.setQ3(StatUtils.percentile(datavalues2, 75.0));
	descrStatsBean.setIqr(descrStatsBean.getQ3() - descrStatsBean.getQ1());
	descrStatsBean.setRange(descrStatsBean.getMaxValue() - descrStatsBean.getMinValue());
	descrStatsBeanList.add(descrStatsBean);	
}

public String generateRegressionAnalysis()
{
	
		try
		{
			if(!rVariable.isEmpty() & !pVariable.isEmpty())
			{
				
				renderErr = false;
				renderRegress= false;
				
				sr.clear();
				DbAccessBean dbAccessBean = new DbAccessBean();
				dbAccessBean.setDbUserBean(dbUserBean);
				if(dbAccessBean.connect().equalsIgnoreCase("FAILURE"))
				{
					return "FAILURE";
				}
				String sqlQuery= "SELECT "+rVariable+","+pVariable+" FROM "+dbUserBean.gettName();
				result = dbAccessBean.getStatement().executeQuery(sqlQuery);
				
				if(result!= null)
				{
					renderRegress = true;
					rsmd = (ResultSetMetaData) result.getMetaData();
					String yType = rsmd.getColumnTypeName(1);
					String xType = rsmd.getColumnTypeName(2);
					ArrayList<Double> xVariable = new ArrayList<Double>();
					ArrayList<Double> yVariable = new ArrayList<Double>();
					while(result.next())
					{
						switch(xType.toLowerCase())
						{
							case "int":
								xVariable.add((double) result.getInt(2));
								break;
							case "smallint":
								xVariable.add((double) result.getInt(2));
								break;
							case "float":
								xVariable.add((double) result.getFloat(2));
								break;
							case "double":
								xVariable.add((double) result.getDouble(2));
								break;
							case "long":
								xVariable.add((double) result.getLong(2));
								break;
							default:
								message ="Please Select a numberic variable";
								break;
						}
						switch(yType.toLowerCase())
						{
							case "int":
								yVariable.add((double) result.getInt(1));
								break;
							case "smallint":
								yVariable.add((double) result.getInt(1));
								break;
							case "float":
								yVariable.add((double) result.getFloat(1));
								break;
							case "double":
								yVariable.add((double) result.getDouble(1));
								break;
							case "long":
								yVariable.add((double) result.getLong(1));
								break;
							default:
								message ="Please Select a numberic variable";
								break;
						}
					}
					 
					double[] predictorArray = new double[xVariable.size()];
					double[] responseArray= new double[yVariable.size()];
					for(int i=0;i<yVariable.size();i++)
					{
						predictorArray[i]= (double)xVariable.get(i);
						responseArray[i]= (double)yVariable.get(i);
					}
		
					for(int i=0; i < xVariable.size(); i++)
					{
						sr.addData(predictorArray[i],responseArray[i]);
					}
					
					tDF = responseArray.length-1;
					pDF = 1;
					reDF = tDF - pDF;
					TDistribution tDist = new TDistribution(tDF);
					
					 correlation =c.correlation(responseArray, predictorArray); 
					 intercept = sr.getIntercept();
					 slope= sr.getSlope();
					 interceptStdErr =sr.getInterceptStdErr();
					 slopeStdErr = sr.getSlopeStdErr();
					 regressEqn =  "Y = "+intercept+" + ("+slope+")"+pVariable;
					 slopeTStat = slope/slopeStdErr;
					 interceptTStat = intercept/interceptStdErr;
					 interceptPValue = (double)2*tDist.cumulativeProbability(-Math.abs(interceptTStat));
					 slopePValue = (double)2*tDist.cumulativeProbability(-Math.abs(slopeTStat));
					 rSquare = sr.getRSquare();
					 RegressionResults regress = sr.regress();
					 rSquareAdjusted = regress.getAdjustedRSquared();
					 modelStdErr = Math.sqrt(StatUtils.variance(responseArray))*(Math.sqrt(1-rSquareAdjusted));
					 System.out.println(rSquareAdjusted);
					//System.out.print(correlation);
					//renderRegress = rvBean.setRegressionVariable(correlation, intercept, slope, interceptStdErr, slopeStdErr, interceptTStat, slopeTStat, interceptPValue, slopePValue,regressEqn,pVariable);
					//System.out.print(renderRegress);
					  regressSumSqr = sr.getRegressionSumSquares();
					  sumSqrErr = sr.getSumSquaredErrors();
					  tSumSqr = sr.getTotalSumSquares();
					  meanSquare = regressSumSqr/pDF;
					  meanSqrErr = (sumSqrErr/reDF);
					  fValue = meanSquare/meanSqrErr;
					  FDistribution fDistribution = new FDistribution(pDF, reDF);
					  pValue = (double)(1-fDistribution.cumulativeProbability(fValue));
					  
					
					
		
				}
		
			}	
			else
			{
				renderErr = true;
				display="Please Select a Response and a Predictor Variable";
			}
				
			
		}
		catch(Exception e)
		{
			message = e.getMessage();
			renderRegress = false;
			renderErr = true;
		}
		return "SUCCESS";
}


public DescrStatsBean getDescrStatsBean() {
	return descrStatsBean;
}

public List<String> getColumnNames() {
	return columnNames;
}
public void setColumnNames(List<String> columnNames) {
	this.columnNames = columnNames;
}
public String[] getDescrStats() {
	return descrStats;
}
public void setDescrStats(String[] descrStats) {
	this.descrStats = descrStats;
}

public boolean isRenderStats() {
	return renderStats;
}
public void setRenderStats(boolean renderStats) {
	this.renderStats = renderStats;
}
public boolean isRenderRegression() {
	return renderRegression;
}
public void setRenderRegression(boolean renderRegression) {
	this.renderRegression = renderRegression;
}
// . . .
}