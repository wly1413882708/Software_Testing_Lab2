package com.example.tests;

/*
 * 使用POI读取Excel文件的内容
 */

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;

public class Func {

	/*
	 * 读取Excel的内容并以学号为键，github网址为值存入HashMap并返回
	 */
	@SuppressWarnings("deprecation")
    public HashMap<String, String> testReadExcel() throws Exception
    {
        //读取
        Workbook wb = WorkbookFactory.create(new FileInputStream("D:/input.xlsx")); 
        
        Sheet st = null;
        
        HashMap<String, String> map = new HashMap<String, String>();
        
        //循环遍历
        st = wb.getSheetAt(0);
        for(Row row : st) {
        	String a = "";
            String b = "";
            for(Cell cell : row) {
            	String tmp = "";
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());  
                //System.out.print(cellRef.formatAsString());
                //System.out.print(" - ");  
                
                int ct = cell.getCellType();
                int len = 0;
                switch (ct) {//只考虑了字符串和数的情况，未考虑表达式和布尔值
                    case Cell.CELL_TYPE_STRING:
                    	//去除string中的空格、空行等无用信息
                    	tmp = cell.getRichStringCellValue().getString().trim();
                    	tmp = tmp.replaceAll("\n", "");
                    	len = tmp.length();
                    	
                    	//多余的斜杠去掉
                    	if(tmp.charAt(len - 1) == '/')
                    		tmp = tmp.substring(0, len - 1);
                    	
                        //System.out.println(tmp);  
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        //数字格式的学号对科学记数法进行处理
                        BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                        tmp = bd.toPlainString();

                        //System.out.println(tmp);
                        break;
                    default:
                        System.out.println("单元格格式不为数和字符串，或为空");
                }
                
                if(cell.getColumnIndex() == 0){
                	//System.out.println("列数为0");
                	a = tmp;
                }else if(cell.getColumnIndex() == 1){
                	//System.out.println("列数为1");
                	b = tmp;
                }else{
                	System.out.println("列数超出预计");
                }
            }
            
            if(!a.equals("") && !b.equals("")){//存入map
            	map.put(a, b);
            }else if(b.equals("")){
            	System.out.println("学号为" + a + "的人网址为空，不予添加");
            }else{
            	
            }

        }
        wb.close();
        
        return map;
    }

}
