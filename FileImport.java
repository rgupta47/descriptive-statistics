package edu.uic.ids517.managedbeans;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Iterator;

import java.util.Scanner;


import javax.faces.context.FacesContext;


import org.apache.myfaces.custom.fileupload.UploadedFile;

//import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;




import edu.uic.ids517.beans.DbAccessBean;

//@ManagedBean(name="fileImport")
//@RequestScoped
public class FileImport {
	
	
	private UploadedFile uploadedFile;
	private String fileLabel;
	private String fileName;
	private long fileSize;
	private String fileContentType;
	private int numberRows;
	private int numberColumns;
	private String uploadedFileContents;
	private boolean importFile;
	private boolean fileImportError;
	private String filePath;
	private String tempFileName;
	private FacesContext facesContext;
	private String dataSetFileLabel;
	private String fileFormat;
	private String fileType;
	private String headerRow;
	private String message;
	private String dataFile[][];
	private String dataFileWithHeader[][];
	//@ManagedProperty(value = "#{dbUserBean)")
	private DbUserBean dbUserBean;
	private String columnList;

	private String dataType[];
	private String filePathUpdated;
	private int columnSize;
	private int n; 
	private Boolean renderTable;

	
	


	public Boolean getRenderTable() {
		return renderTable;
	}



	public void setRenderTable(Boolean renderTable) {
		this.renderTable = renderTable;
	}



	public String getFilePathUpdated() {
		return filePathUpdated;
	}



	public void setFilePathUpdated(String filePathUpdated) {
		this.filePathUpdated = filePathUpdated;
	}



	public String[] getDataType() {
		return dataType;
	}



	public void setDataType(String[] dataType) {
		this.dataType = dataType;
	}








	public String[][] getDataFileWithHeader() {
		return dataFileWithHeader;
	}



	public void setDataFileWithHeader(String[][] dataFileWithHeader) {
		this.dataFileWithHeader = dataFileWithHeader;
	}



	public String getColumnList() {
		return columnList;
	}



	public void setColumnList(String columnList) {
		this.columnList = columnList;
	}



	public DbUserBean getDbUserBean() {
		return dbUserBean;
	}



	public void setDbUserBean(DbUserBean dbUserBean) {
		this.dbUserBean = dbUserBean;
	}



	public String[][] getDataFile() {
		return dataFile;
	}



	public void setDataFile(String[][] dataFile) {
		this.dataFile = dataFile;
	}
	

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isImportFile() {
		return importFile;
	}
	public void setImportFile(boolean importFile) {
		this.importFile = importFile;
	}
	public String getHeaderRow() {
		return headerRow;
	}
	public void setHeaderRow(String headerRow) {
		this.headerRow = headerRow;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public String getDataSetFileLabel() {
		return dataSetFileLabel;
	}
	public void setDataSetFileLabel(String dataSetFileLabel) {
		this.dataSetFileLabel = dataSetFileLabel;
	}
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public String getFileLabel() {
		return fileLabel;
	}
	public void setFileLabel(String fileLabel) {
		this.fileLabel = fileLabel;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public int getNumberRows() {
		return numberRows;
	}
	public void setNumberRows(int numberRows) {
		this.numberRows = numberRows;
	}
	public int getNumberColumns() {
		return numberColumns;
	}
	public void setNumberColumns(int numberColumns) {
		this.numberColumns = numberColumns;
	}
	public String getUploadedFileContents() {
		return uploadedFileContents;
	}
	public void setUploadedFileContents(String uploadedFileContents) {
		this.uploadedFileContents = uploadedFileContents;
	}

	public boolean isFileImportError() {
		return fileImportError;
	}
	public void setFileImportError(boolean fileImportError) {
		this.fileImportError = fileImportError;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getTempFileName() {
		return tempFileName;
	}
	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}
	public FacesContext getFacesContext() {
		return facesContext;
	}
	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	
	public String processFileUpload() 
	{
		renderTable=false;
		message ="";
	    n = 0;
		File tempFile = null;
		FileOutputStream fos = null;
		fileInitial();
		 try
		 {
			if(fileFormat.equals("Excel csv") & fileName.contains(".csv"))
			{
				renderTable=true;
				
				tempFile = new File(tempFileName);
				fos = new FileOutputStream(tempFile);
				// next line if want file uploaded to disk
				fos.write(uploadedFile.getBytes());
				fos.close();
				String input = null;
				Scanner s = new Scanner(tempFile);
				while(s.hasNext())  // counting the number of rows in the file.
				{
					input= s.nextLine();
					System.out.println(input);
					n++;
				}
				s.close();
				
			    String[] column = input.split(",");
			    columnSize = column.length;
			    dataFileWithHeader = new String [n][columnSize];
			    Scanner t = new Scanner(tempFile);
	    		 
			    for(int i=0; i<n; i++)  // storing data into multidimentional array.
			    {
			    	 input = t.nextLine();
		    		 column = input.split(",");
			    	
			    	for(int j = 0; j< columnSize; j++)
			    	{
			    		dataFileWithHeader[i][j] = column[j];
			    	
					}
		 	    }
			    fileLoad();
			
			    t.close();
				//numberRows = n;
				importFile = true;
			}
			
			
			else if(fileFormat.equals("Excel") & fileName.contains(".xls"))
			{
				renderTable=true;
				tempFile = new File(tempFileName);
				//String[] tempFileName1 = tempFileName.split(".");
				//System.out.println(tempFileName1[0]);
				File outputFile = new File(filePathUpdated+"/"+fileLabel+"_xlsTocsv.csv");
				// For storing data into CSV files
		        StringBuffer data = new StringBuffer();
				FileOutputStream ofos = new FileOutputStream(outputFile);
				// next line if want file uploaded to disk
				fos = new FileOutputStream(tempFile);
				fos.write(uploadedFile.getBytes());
				fos.close();
				
				 FileInputStream file = new FileInputStream(tempFile);
				//POIFSFileSystem tempFile1 = new POIFSFileSystem(new FileInputStream(tempFileName));
			    HSSFWorkbook wb = new HSSFWorkbook(file);
			    HSSFSheet sheet = wb.getSheetAt(0);
			    Row row;
			    Cell cell;

			    int rows; // No of rows
			    rows = sheet.getPhysicalNumberOfRows();
			    Iterator<Row> rowIterator = sheet.iterator();
	
			    String name = sheet.getSheetName();
			    System.out.println(name);
			    if (headerRow.equals("Yes"))
				{
			    	setNumberRows(rows-1);
				}
				else
				{
					setNumberRows(rows);
				}
			    while (rowIterator.hasNext()) 
		        {
		                row = rowIterator.next();
		                // For each row, iterate through each columns
		                Iterator<Cell> cellIterator = row.cellIterator();
				    while (cellIterator.hasNext()) 
	                {
	                        cell = cellIterator.next();
	                        
	                        switch (cell.getCellType()) 
	                        {
	                        case Cell.CELL_TYPE_BOOLEAN:
	                                data.append(cell.getBooleanCellValue() + ",");
	                                break;
	                                
	                        case Cell.CELL_TYPE_NUMERIC:
	                                data.append(cell.getNumericCellValue() + ",");
	                                break;
	                                
	                        case Cell.CELL_TYPE_STRING:
	                                data.append(cell.getStringCellValue() + ",");
	                                break;
	
	                        case Cell.CELL_TYPE_BLANK:
	                                data.append("" + ",");
	                                break;
	                        
	                        default:
	                                data.append(cell + ",");
	                        }
	                }
				    data.append('\n'); 
		        }

	        ofos.write(data.toString().getBytes());
	        ofos.close();
				//numberRows = n;
	        tempFile = new File(filePathUpdated+fileLabel+"_xlsTocsv.csv");
			String input = null;
			Scanner s = new Scanner(tempFile);
			while(s.hasNext())  // counting the number of rows in the file.
			{
				input= s.nextLine();
				System.out.println(input);
				n++;
			}
			s.close();
			
		    String[] column = input.split(",");
		    columnSize = column.length;
		    dataFileWithHeader = new String [n][columnSize];
		    Scanner t = new Scanner(tempFile);
    		 
		    for(int i=0; i<n; i++)  // storing data into multidimentional array.
		    {
		    	 input = t.nextLine();
	    		 column = input.split(",");
		    	
		    	for(int j = 0; j< columnSize; j++)
		    	{
		    		dataFileWithHeader[i][j] = column[j];
		    	
				}
	 	    }
		    fileLoad();
		
		    t.close();
			//numberRows = n;
			importFile = true;
				importFile = true; 
			    
			}
			else if(fileFormat.equals("Excel Tab") & fileName.contains(".txt"))
			{
				renderTable= true;
				
				tempFile = new File(tempFileName);
				fos = new FileOutputStream(tempFile);
				// next line if want file uploaded to disk
				fos.write(uploadedFile.getBytes());
				fos.close();
				String input = null;
				Scanner s = new Scanner(tempFile);
				while(s.hasNext()) 
				{
					input= s.nextLine();
					System.out.println(input);
					n++;
				}
				s.close();
			    String[] column = input.split("\t");
			    columnSize = column.length;
			    dataFileWithHeader = new String [n][columnSize];
			    Scanner t = new Scanner(tempFile);
	    		 
			    for(int i=0; i<n; i++)  // storing data into multidimentional array.
			    {
			    	 input = t.nextLine();
		    		 column = input.split("\t");
			    	
			    	for(int j = 0; j< columnSize; j++)
			    	{
			    		dataFileWithHeader[i][j] = column[j];
			    	
					}
		 	    }	
			    fileLoad();
			    t.close();
				importFile = true;
			}
			
			else
			{
				FileClear();
				message = "Please Select the correct File Format";
			}
			
			
			
			
		} // end try
		catch (Exception e) { // set codes return FAIL etc}
		
			FileClear();
			message = e.getMessage(); 
			renderTable= false;
			return "FAIL";
		}
		
		return "SUCCESS";
	}
	
	
	public void fileInitial()
	{
		
		try
		{
		//setNumberRows(0);
		uploadedFileContents = null;
		//messageBean.setErrorMessage("");
		facesContext = FacesContext.getCurrentInstance();
		filePath = facesContext.getExternalContext().getRealPath("/temp");
		importFile = false;
		fileImportError = true;
		fileName = uploadedFile.getName();
		String[] filePart =fileName.split("\\\\");
		fileName = filePart[filePart.length-1];
		fileSize = uploadedFile.getSize();
		fileContentType = uploadedFile.getContentType();
		// next line if want upload in String for memory processing
		// uploadedFileContents = new String(uploadedFile.getBytes());
		tempFileName = filePath +"/"+ fileName;
		//tempFile = new File(tempFileName);
		String splitFilepath[] = filePath.split("\\\\");
		filePathUpdated ="";
		for(int i= 0; i<splitFilepath.length;i++)
		{
			String filePathUpdt = splitFilepath[i]+"/";
			filePathUpdated =filePathUpdated+ filePathUpdt;
		}
		}
		catch(Exception e)
		{
			message= e.getMessage();
			FileClear();
		}
	}
	
	public void FileClear()
	{
		 fileContentType = null;
		 numberRows = 0;
		 uploadedFileContents = null;
		 filePath = null;
		 tempFileName =null;
		 facesContext =null;
		 dataSetFileLabel = null;
		 fileFormat= null;
		 fileType = null;
		 fileSize = 0;
		 fileName = null;
		 headerRow = null;
		 fileLabel= null;
		
	}


	public void fileLoad() throws Exception
	{
		// check for header row and remove it.
		if (headerRow.equals("Yes"))
		{
			
			setNumberRows(n-1);
			dataFile = new String[getNumberRows()][columnSize];
			int k =0; // counter used to copy data without header.
			for(int i = 1; i<=getNumberRows(); i++)
			{
				
				for(int j = 0; j<columnSize; j++)
				{
					dataFile[k][j] = dataFileWithHeader[i][j];
				}
				k=k+1;
				
			}
			
		}
		else
		{
			setNumberRows(n);
			dataFile = dataFileWithHeader;
		}
		
		for(int i = 0;i< getNumberRows(); i++)
		{
			if(dataFile[i][1].contains("String")|dataFile[i][1].contains("string")|dataFile[i][1].contains("STRING") )
			{
				dataFile[i][1] = "varchar(100)";
			}
		}
	    //creating table while importing file.
	    if(fileType.equalsIgnoreCase("Metadata"))
	    {
		    ArrayList<String> tableColumnName = new ArrayList<String>();
		    
		    for (int i = 0; i< dataFile.length; i++)
		    {
		    	for(int j =0; j< 1; j++ )
		    	{
		    		tableColumnName.add(dataFile[i][j]+" "+dataFile[i][j+1]);
		    	}
		    }
		    
		   
		    tableColumnName.add("ID INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT");
		    columnList = tableColumnName.toString();
		    columnList = columnList.replace("[", "");
		    columnList= columnList.replace("]", "");
		    
		    System.out.println(columnList);
		    DbAccessBean dbAccessBean = new DbAccessBean();
			dbAccessBean.setDbUserBean(dbUserBean);
			if(dbAccessBean.connect().equalsIgnoreCase("FAILURE")){
				setMessage("Unable to Connect to Database. Please contact administrator");
				
			}
			setMessage("");
			String sqlQuery="Create table S17g305_"+getFileLabel()+" ("+getColumnList()+")";
			System.out.println(sqlQuery);
			try {
					
					
					dbAccessBean.getStatement().executeUpdate(sqlQuery);
					dbUserBean.tableName();

			} catch (SQLException e) {
				
				message = e.getMessage();
				e.printStackTrace();
			}
			catch (Exception e1)
			{
				message = e1.getMessage();
				e1.printStackTrace();
			}
		    
	    }
	    
	    //insert data into the created table
	    else if(fileType.equalsIgnoreCase("Data"))
	    {
	    		String split;
	    		DbAccessBean dbAccessBean = new DbAccessBean();
				dbAccessBean.setDbUserBean(dbUserBean);
				if(dbAccessBean.connect().equalsIgnoreCase("FAILURE"))
				{
					setMessage("Unable to Connect to Database. Please contact administrator");
				}
				String sqlQuery;
				if(fileFormat.equals("Excel Tab"))
				{
					 split = "'\t'";
				}
				else
				{
					 split = "','";
				}
				String fileNm;
				if(fileName.contains(".xls"))
				{
					fileNm = filePathUpdated+"/"+fileLabel+"_xlsTocsv.csv";
				}
				else
				{
					fileNm =getFilePathUpdated()+getFileName();
				}
				if (headerRow.equalsIgnoreCase("Yes"))
				{
					sqlQuery="LOAD DATA LOCAL INFILE '"+fileNm + "' INTO TABLE s17g305_"+getFileLabel()+" FIELDS TERMINATED BY "+split+"  OPTIONALLY ENCLOSED BY '\"'" + " LINES TERMINATED BY '\n' IGNORE 1 LINES ";
				}
				else
				{
					sqlQuery="LOAD DATA LOCAL INFILE '"+fileNm + "' INTO TABLE s17g305_"+getFileLabel()+" FIELDS TERMINATED BY "+split+"  OPTIONALLY ENCLOSED BY '\"'" + " LINES TERMINATED BY '\n'";
				}
				//System.out.println(sqlQuery);
				try
		    	{
				dbAccessBean.getStatement().executeUpdate(sqlQuery);
		    	}
	    	catch (SQLException e) 
	    	{
				
				message = e.getMessage();
				e.printStackTrace();
			}
			catch (Exception e1)
			{
				message = e1.getMessage();
				e1.printStackTrace();
			}
		    
	    }
	}
}
	
	


