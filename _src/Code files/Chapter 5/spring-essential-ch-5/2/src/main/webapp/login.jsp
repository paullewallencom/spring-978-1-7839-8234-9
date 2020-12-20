    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
        pageEncoding="ISO-8859-1"%>  
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
    <html>  
    <head>  
    <title>Login Page For Security</title>
     <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <style>  
    .errorblock {  
     color: #ff0000;  
     background-color: #ffEEEE;  
     border: 3px solid #ff0000;  
     padding: 8px;  
     margin: 16px;  
    }  
    </style>  
    </head>  
    <body onload='document.f.username.focus();'>
     <h3>Login with Username and Password (Custom Login Page)</h3>  


<form role="form" action="/login" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div>
        <label for="username">Username</label>
        <input type="text" name="username" id="username" required autofocus>
    </div>
    <div>
        <label for="password">Password</label>
        <input type="password" name="password" id="password" required>
    </div>
    <button type="submit">Sign in</button>
</form>


    </body>  
    </html>  