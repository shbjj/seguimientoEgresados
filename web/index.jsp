<%-- 
    Document   : index
    Created on : 1/04/2021, 11:00:54 PM
    Author     : hbdye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
	String tipo=(String)session.getAttribute("TIPO");
        %>
        
        
        <% if(tipo!=null)
        {
            switch(tipo)
            {
                case "1":
                    response.sendRedirect(request.getContextPath() + "/IndexAlumno");
                    break;
                case "2":
                    response.sendRedirect(request.getContextPath()+"/PanelDeAdmin"); 
                    break;
                case "3":
                    response.sendRedirect(request.getContextPath()+"/CargarPreguntasRespuestas"); 
                    break;
            }
        }
        else
        {
            response.sendRedirect(request.getContextPath()+"/login.jsp"); 
        }  %>
    </body>
</html>
