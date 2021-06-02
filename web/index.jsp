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
        String nombre=(String)session.getAttribute("NOMBRE");
	String tipo=(String)session.getAttribute("TIPO");
        %>
        
        <%=nombre%>
        <%=tipo%>
        
        <a href="<%=request.getContextPath()%>/PanelDeAdmin">Panel de admin</a>
    </body>
</html>
