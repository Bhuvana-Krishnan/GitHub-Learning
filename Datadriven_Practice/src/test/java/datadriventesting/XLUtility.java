package datadriventesting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {
	
	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path = null;
	
	XLUtility (String path)
	{
		this.path=path;
	}
	
	public int getRowCount(String SheetName) throws IOException
	{
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(SheetName);
		int rowcount = sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return rowcount;
	}
	
	public int getColCount(String SheetName, int rownum) throws IOException
	{
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(SheetName);
		row = sheet.getRow(rownum);
		int colcount = row.getLastCellNum();
		workbook.close();
		fi.close();
		return colcount;
	}
	
	public String getCellData(String SheetName, int rownum, int colcount) throws IOException
	{
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(SheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(colcount);
		
		DataFormatter formatter = new DataFormatter();
		String data;
		try {
			data = formatter.formatCellValue(cell); // Returns the formatted value of a cell as a String regardless of the 
		}
		catch (Exception e) {
			data = "";			
		}
		workbook.close();
		fi.close();
		return data;		
	}
	
	public void setCellData (String SheetName, int rownum, int colcount, String data) throws IOException
	{
	
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(SheetName);
		row = sheet.getRow(rownum);
		cell = row.createCell(colcount);
		cell.setCellValue(data);
		
		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
		
	}

}





















