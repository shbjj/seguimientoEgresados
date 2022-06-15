/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hbdye
 */
public class Excel {

    public static void main(String[] args) {
        ArrayList<String> t = new ArrayList();
        t.add("SKF0");
        t.add("SKF0");
        t.add("SKF0");
        t.add("SKF0");
        t.add("SKF0");
        try {
            modificar("Hugo Blancas Dominguez", "Matematicas", "ENE-FEB 2022", t);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modificar(String catedratico, String taller, String periodo, ArrayList<String> alumnos) throws IOException {
        try {
            FileInputStream file = new FileInputStream("web\\xlsx\\formatolista.xlsx");

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheetAt(0);

            //Modificar Instructor
            XSSFRow fila = sheet.getRow(6);
            if (fila == null) {
                fila = sheet.createRow(6);
            }
            XSSFCell celda = fila.createCell(1);
            if (celda == null) {
                celda = fila.createCell(1);
            }
            celda.setCellValue("CATEDRÁTICO: " + catedratico.toUpperCase());
            //Modificar Taller
            fila = sheet.getRow(5);
            if (fila == null) {
                fila = sheet.createRow(5);
            }
            celda = fila.createCell(1);
            if (celda == null) {
                celda = fila.createCell(1);
            }
            celda.setCellValue("TALLER: " + taller.toUpperCase());
            //Modificar semestre
            fila = sheet.getRow(5);
            if (fila == null) {
                fila = sheet.createRow(5);
            }
            celda = fila.createCell(25);
            if (celda == null) {
                celda = fila.createCell(25);
            }
            celda.setCellValue(periodo.toUpperCase());

            int cont = 0;
            for (String nombre : alumnos) {
                fila = sheet.getRow(10 + cont);
                if (fila == null) {
                    fila = sheet.createRow(10 + cont);
                }
                celda = fila.getCell(2);
                //celda = fila.createCell(2);
                if (celda == null) {
                    celda = fila.createCell(2);
                }
                XSSFCellStyle xd = celda.getCellStyle();
                celda.setCellValue(nombre.toUpperCase());
                celda.setCellStyle(xd);

                cont++;
            }

            file.close();

            FileOutputStream out = new FileOutputStream("D:\\Archivos\\Descargas\\lista"+taller+".xlsx");
            wb.write(out);
            out.close();
//            OutputStream outputStream = response.getOutputStream();
//            //Output the workbook to the response.
//            wb.write(outputStream);
//            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
