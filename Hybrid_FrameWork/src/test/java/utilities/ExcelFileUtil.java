package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
	Workbook wb;
	//constructor for reading path of excel file
    public ExcelFileUtil(String Excelpath) throws Throwable
    {
    	FileInputStream fi=new FileInputStream(Excelpath);
    	wb=WorkbookFactory.create(fi);
    }
    //method for counting no of rows in sheet
    public int rowCount(String Sheetname)
    {
		return wb.getSheet(Sheetname).getLastRowNum();
		
    	
    }
    //method for reading cell data
    public String getCellData(String sheetName,int row,int column)
    {
		String data="";
		if(wb.getSheet(sheetName).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int celldata=(int)wb.getSheet(sheetName).getRow(row).getCell(column).getNumericCellValue();
			data=String.valueOf(celldata);
		}
		else
		{
			data=wb.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
		
    }
    //method for writing results
    public void setcellData(String sheetName,int row,int column,String status,String writeExcel) throws Throwable
    {
    	//get sheet from wb
    	Sheet ws=wb.getSheet(sheetName);
    	//get row from sheet
    	Row rowNum=ws.getRow(row);
    	//create cell
    	Cell cell=rowNum.createCell(column);
    	//write status
    	cell.setCellValue(status);
    	if (status.equalsIgnoreCase("pass")) {
    		CellStyle style=wb.createCellStyle();
    		Font font=wb.createFont();
    		font.setColor(IndexedColors.GREEN.getIndex());
    		font.setBold(true);
    		style.setFont(font);
    		ws.getRow(row).getCell(column).setCellStyle(style);
			
		}
    	else if (status.equalsIgnoreCase("fail")) {
    		CellStyle style=wb.createCellStyle();
    		Font font=wb.createFont();
    		font.setColor(IndexedColors.RED.getIndex());
    		font.setBold(true);
    		style.setFont(font);
    		ws.getRow(row).getCell(column).setCellStyle(style);
    		
			
		}
    	else if (status.equalsIgnoreCase("Blocked")) {
    		CellStyle style=wb.createCellStyle();
    		Font font=wb.createFont();
    		font.setColor(IndexedColors.BLUE.getIndex());
    		font.setBold(true);
    		style.setFont(font);
    		ws.getRow(row).getCell(column).setCellStyle(style);
			
		}
    	FileOutputStream fo=new FileOutputStream(writeExcel);
    	wb.write(fo);
    }
    public static void main(String[] args) throws Throwable {
    	ExcelFileUtil xl=new ExcelFileUtil("C:\\New folder (2)\\sample3.xlsx");
    	//count no of rows in emp sheet
    	int rc=xl.rowCount("Emp");
    	System.out.println(rc);
    	for(int i=1;i<=rc;i++) {
    		String fname=xl.getCellData("Emp", i, 0);
    		String mname=xl.getCellData("Emp", i, 1);
    		String lname=xl.getCellData("Emp", i, 2);
    		String Eid=xl.getCellData("Emp", i, 3);
    		System.out.println(fname+"  "+mname+"  "+lname+" "+Eid);
    		xl.setcellData("Emp", i, 4, "pass", "D:/Results6.xlsx");
    	}
		
	}
    
	

}
