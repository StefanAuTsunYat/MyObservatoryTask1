package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import base.Base;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
    private static ArrayList <String> headerList = new ArrayList <String> ();
    private static ArrayList <String> testdataList = new ArrayList <String> ();

    public static void readExcel(String scenarioName)throws IOException
    {
        System.out.println("Read Excecl for case: "+scenarioName);
        XSSFWorkbook excelWBook;
        XSSFSheet excelWSheet;

        DataFormatter dataFormatter = new DataFormatter();


        // String path = "resource/AIAConnectTestData_was9.xlsx";
        String path = "resource/TestData.xlsx";


        try {
            FileInputStream excelFile = new FileInputStream(path);
            excelWBook = new XSSFWorkbook(excelFile);
            excelWSheet = excelWBook.getSheetAt(0);
            headerList.clear();
            testdataList.clear();

            for (Row row: excelWSheet) {
                String cellValue1stCol = dataFormatter.formatCellValue(row.getCell(0));
                String cellValue2stCol = dataFormatter.formatCellValue(row.getCell(1));
                //Setup the array list for header

//              if (row.getRowNum() == 0) {
                if (cellValue1stCol.trim().equals("caseName")) {
                    for (Cell cell: row) {
                        String cellValue = dataFormatter.formatCellValue(cell);
                        // System.out.print(cellValue +"\t;");
                        headerList.add(cellValue);
                    }
                }
                //Setup the array list for test data
                else if (cellValue1stCol.trim().equals(scenarioName.trim())||cellValue2stCol.trim().equals(scenarioName.trim())) {
                    for (Cell cell: row) {
                        String cellValue = dataFormatter.formatCellValue(cell);
                        //System.out.print(cellValue +"\t;");
                        testdataList.add(cellValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int hlsize = headerList.size();
        int dlsize = testdataList.size();
        System.out.println("hlsize:"+hlsize);
        System.out.println("dlsize:"+dlsize);
//DF        for(int i=0;i<hlsize;i++){
//        	try{
//        	System.out.println("("+headerList.get(i)+"," + testdataList.get(i)+")");
//        	}catch(Exception e){
//        		e.printStackTrace();
//        	}
// DF       }
    }

    public static String getValue(String fieldName) {
        int ColIndex = headerList.indexOf(fieldName.trim());
        String fieldValue = testdataList.get(ColIndex).trim();
        return fieldValue;
    }

    public static void writeRuntime(String scenarioName, String runTime, String status, String report_url, boolean run_flag) {
        String path = "resource/Runtime.xlsx";
        if (!run_flag) {
            System.out.println("============no need to write runtime excel============");
            return;
        }else {
            File result = new File(path);
            if (!result.exists()) {
                System.out.println("============runtime excel not exists============");
                return;
            }
        }

        System.out.println("write Runtime excle : "+scenarioName);
        XSSFWorkbook excelWBook;
        XSSFSheet excelWSheet;

        DataFormatter dataFormatter = new DataFormatter();

        try {
            FileInputStream excelFile = new FileInputStream(path);
            excelWBook = new XSSFWorkbook(excelFile);
            excelWSheet = excelWBook.getSheetAt(0);
	        boolean isWrite = false;

            Calendar my_calendar = Calendar.getInstance();
	        String run_date = (my_calendar.get(Calendar.MONTH) + 1) + "/" + (my_calendar.get(Calendar.DATE)) + "/"
	        		+ (my_calendar.get(Calendar.YEAR));

            for (Row row: excelWSheet) {
                String cellValue1stCol = dataFormatter.formatCellValue(row.getCell(0));

                if (cellValue1stCol.trim().equals(scenarioName.trim())) {
                    writeCell(row,1,run_date);
                    writeCell(row,2,runTime);
					if (!Base.getFailReason().equals("")){
						writeCell(row,3,Base.getFailReason());
						Base.setFailReason("");
					}else{
                    writeCell(row,3,status);
					}
                    if (Base.getRunMode().equalsIgnoreCase("cloud")){
                        writeCell(row,4,report_url);
                    }
					isWrite = true;
                    break;
                }
            }

	        if (!isWrite) {
                Row row = excelWSheet.createRow(excelWSheet.getLastRowNum() + 1);
                writeCell(row,0,scenarioName.trim());
                writeCell(row,1,run_date);
                writeCell(row,2,runTime);
				if (!Base.getFailReason().equals("")){
					writeCell(row,3,Base.getFailReason());
					Base.setFailReason("");
				}else{
                writeCell(row,3,status);
				}
                if (Base.getRunMode().equalsIgnoreCase("cloud")){
                    writeCell(row,4,report_url);
                }
            }

            FileOutputStream outputStream = new FileOutputStream(path);
            outputStream.flush();
            excelWBook.write(outputStream);
            outputStream.close();
            excelFile.close();
        } catch (Exception e) {
            System.out.println("write runtime excel fail!");
            e.printStackTrace();
        }
    }

    private static void writeCell(Row row, int cell_index, String cell_value) {
        if (row.getCell(cell_index) != null) {
            row.getCell(cell_index).setCellValue(cell_value);
        }else {
            row.createCell(cell_index).setCellValue(cell_value);
        }
    }
}
