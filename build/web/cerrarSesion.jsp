<%-- 
    Document   : cerrarSesion
    Created on : 1/07/2021, 09:55:30 PM
    Author     : hbdye
--%>

<%
   String tipoS=(String)session.getAttribute("TIPO");
   if (tipoS!=null)//Hay una sesion activa
   {
     session.setAttribute("MATRICULA",null);
     session.setAttribute("NOMBRE",null);
     session.setAttribute("USUARIO",null);
     session.setAttribute("ROL",null);
     session.setAttribute("ID_ENCUESTA",null);
     session.setAttribute("TIPO",null);
   }
   response.sendRedirect("login.jsp");
%>  
