/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author hbdye
 */
public class ConvertirUTF8 {
    private byte[] tempBytes;
    public ConvertirUTF8()
    {
        
    }
    
    public String convertToUTF8(String temp)
    {
       tempBytes = temp.getBytes();
        temp = new String(tempBytes, StandardCharsets.UTF_8);
       tempBytes=null;
        return temp;
    }
}
