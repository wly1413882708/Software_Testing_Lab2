package com.example.tests;

/*
 * ʹ��POI��ȡExcel�ļ�������
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
	 * ��ȡExcel�����ݲ���ѧ��Ϊ����github��ַΪֵ����HashMap������
	 */
	@SuppressWarnings("deprecation")
    public HashMap<String, String> testReadExcel() throws Exception
    {
        //��ȡ
        Workbook wb = WorkbookFactory.create(new FileInputStream("D:/input.xlsx")); 
        
        Sheet st = null;
        
        HashMap<String, String> map = new HashMap<String, String>();
        
        //ѭ������
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
                switch (ct) {//ֻ�������ַ��������������δ���Ǳ��ʽ�Ͳ���ֵ
                    case Cell.CELL_TYPE_STRING:
                    	//ȥ��string�еĿո񡢿��е�������Ϣ
                    	tmp = cell.getRichStringCellValue().getString().trim();
                    	tmp = tmp.replaceAll("\n", "");
                    	len = tmp.length();
                    	
                    	//�����б��ȥ��
                    	if(tmp.charAt(len - 1) == '/')
                    		tmp = tmp.substring(0, len - 1);
                    	
                        //System.out.println(tmp);  
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        //���ָ�ʽ��ѧ�ŶԿ�ѧ���������д���
                        BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                        tmp = bd.toPlainString();

                        //System.out.println(tmp);
                        break;
                    default:
                        System.out.println("��Ԫ���ʽ��Ϊ�����ַ�������Ϊ��");
                }
                
                if(cell.getColumnIndex() == 0){
                	//System.out.println("����Ϊ0");
                	a = tmp;
                }else if(cell.getColumnIndex() == 1){
                	//System.out.println("����Ϊ1");
                	b = tmp;
                }else{
                	System.out.println("��������Ԥ��");
                }
            }
            
            if(!a.equals("") && !b.equals("")){//����map
            	map.put(a, b);
            }else if(b.equals("")){
            	System.out.println("ѧ��Ϊ" + a + "������ַΪ�գ��������");
            }else{
            	
            }

        }
        wb.close();
        
        return map;
    }

}
