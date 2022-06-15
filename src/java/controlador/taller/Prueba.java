/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.taller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hbdye
 */
public class Prueba extends HttpServlet {

    //PrintWriter out;

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //out = response.getWriter();
        ServletContext cntx = request.getServletContext();

        // Get the absolute path of the image
        String filename = cntx.getRealPath("/xlsx/boletas.xlsx");
        //out.printf(filename);
        //File file = new File("web//xlsx//boletas.xlsx");
        File file = new File(filename);
        try {
           

            FileInputStream fis;
            try {
                fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=SampleExcel.xls");
                wb.write(response.getOutputStream());
            } catch (FileNotFoundException ex) {
                
        response.setContentType("text/html;charset=UTF-8");
      //          out.println(ex.toString());
      ex.printStackTrace();
            }

            
        } catch (Exception e) {
            
        response.setContentType("text/html;charset=UTF-8");
        //                    out.println(e.toString());
        e.printStackTrace();

        }
        /*FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            String mime = cntx.getMimeType(filename);
            if (mime == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            response.setContentType(mime);
            //response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=SampleExcel.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            wb.write(response.getOutputStream());
        } catch (Exception ex) {

            response.setContentType("text/html;charset=UTF-8");
            out.println(ex.toString());
            ex.printStackTrace();
        }*/

    }
}
