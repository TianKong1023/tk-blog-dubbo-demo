package com.kk.blog.utils;

import com.kk.blog.annotations.ExcelFieldAnnotation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;


public class ExcelExportUtils {

    public static void exportToExcel (List<?> dataList, String fileName) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet(fileName);

            // 创建表头
            Row headerRow = sheet.createRow(0);
            Field[] fields = dataList.get(0).getClass().getDeclaredFields();
            for(int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if(field.isAnnotationPresent(ExcelFieldAnnotation.class)) {
                    ExcelFieldAnnotation annotation = field.getAnnotation(ExcelFieldAnnotation.class);
                    String headName = annotation.value();
                    // 创建表头的单元格
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headName);
                }
            }

            // 填充值
            for(int rowNum = 1; rowNum <= dataList.size(); rowNum++) {
                Row row = sheet.createRow(rowNum);
                for(int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if(field.isAnnotationPresent(ExcelFieldAnnotation.class)) {
                        field.setAccessible(true);
                        Object value = field.get(dataList.get(rowNum - 1));
                        Cell cell = row.createCell(i);
                        cell.setCellValue((String) value);
                    }
                    field.setAccessible(false);
                }
            }

            // 表格文件输出
            FileOutputStream fileOutputStream = new FileOutputStream("/path/" + fileName + ".xlsx");
            wb.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
