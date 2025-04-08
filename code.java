<%@ page language="java" contentType="text/html; 
pageEncoding="ISO-8859-1"%> 
charset=ISO-8859-1" 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd"> 
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<title>LOGIN PAGE</title> 
<link href="common/style.css" rel="stylesheet"> 
</head> <body bgcolor="#CCFF99"> 
<div><jsp:include age="common/header.jsp"></jsp:include></div> 
46 
<div class="container" 
style="margin-top: 25%; margin-left: 
25%; color: black; border: 1px solid black; width: 50%; height: 50%; padding: 10px"> 
<form action="./login" method="post"> 
<center> 
<h2>Sign In</h2> 
<h5 style="color: red"> <b><i>${alert}</i></b> 
</h5> </center> 
<hr> 
<label><b>USER NAME</b></label> 
<input type="text" style="width:100%; height:37px" 
name="uname" placeholder="Enter User Name" required> 
<label><b>PASSWORD</b></label> 
<input type="password" style="width:100%; height:37px" 
name="pass" placeholder="Enter User Password" required><br> 
<div class="clearfix"> 
<a href="forget_pass.jsp">Forget Your password??</a><br> 
<button type="submit" class="signupbtn">Sign In</button> 
</div> </form> 
</body> 
</html> 
</div> 
47 
Register.jsp 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd"> 
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<title>REGISTER FORM</title> 
<link rel="stylesheet" href="common/style.css"> 
<!-- both jquery is for validating form --> 
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> 
<script 
src="https://cdnjs.cloudflare.com/ajax/libs/jquery- 
validate/1.19.0/jquery.validate.js"></script> 
</head> 
<body bgcolor="#CCFF99"> 
<jsp:include page="common/header.jsp"></jsp:include> 
<div class="container" style="margin-top: 18%;margin-left:25%; color: black; border: 
1px solid black; width: 50%; height: 45%;padding: 10px"> 
48 
<form action="./register" method="post" id="registration"> 
<center> <h2> Sign Up </h2> 
<p style="color:black;background-color:green"><b><i>${alert}</i></b></p></center> 
<hr> <label for="firstName"><b>FIRST NAME</b></label> 
<input style="width:100%; height:37px" type="text" 
placeholder="Enter First Name" name="firstname" id="firstname" ><br> 
<label for="lastName"><b>LAST NAME</b></label> 
<input style="width:100%; height:37px" type="text" 
placeholder="Enter Last Name" name="lastname" id="lastname" ><br> 
<label for="uname"><b>USER NAME</b></label> 
<input style="width:100%; height:37px;border: 1px solid 
black;border-radius:10px;" type="text" placeholder="sam@email.com" name="email" 
id="email" ><br> <label for="phone"><b>PHONE NUMBER</b></label> 
<input style="width:100%; height:37px" type="text" 
placeholder="Enter Number" name="phone" id="phone" title="Contact number should be 
enter 10 digits only and start with 6 or 7 or 8 or 9" style="width:100%;height:37px" 
><br> 
<label for="password"><b>PASSWORD</b></label> 
<input style="width:100%; height:37px" type="password" 
placeholder="Enter Password" name="password" id="password" > 
class="clearfix"> <button type="submit" class="signupbtn">Sign Up</button> 
</div> </form></div><script> 
$(document).ready(function(){ 
$("#registration").validate({ 
<div 
49 
// Specify validation rules 
rules: { 
firstname: "required", 
lastname: "required", 
email: { 
required: true, 
email: true 
}, 
phone: { 
required: true, 
digits: true, 
minlength: 10, 
maxlength: 10, 
}, 
password: { 
required: true, 
minlength: 5, 
} }, messages: { 
firstname: { 
required: "Please Enter First Name", 
}, 
lastname: { 
required: "Please Enter Last Lame", 
50 
}, 
phone: { 
required: "Please Enter Phone Number", 
digits: "Please enter valid phone number", 
minlength: "Phone number field accept only 10 digits", 
maxlength: "Phone number field accept only 10 digits", 
}, 
email: { 
required: "Please enter email address", 
email: "Please enter a valid email address.", 
}, }, 
});}); 
</script> 
</body> 
</html> 
LoginController.java 
package com.controller; 
import java.io.IOException; 
import java.sql.SQLException; 
51 
import javax.servlet.ServletException; 
import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession; 
import com.bean.RegisterBean; 
import com.dao.RegisterDao; 
@WebServlet("/login") 
public class LoginController extends HttpServlet { 
@Override 
HttpServletResponse resp) 
protected void doGet(HttpServletRequest req, 
throws ServletException, IOException { 
RegisterBean bean = new RegisterBean(); 
bean.setEmail(req.getParameter("uname")); 
bean.setPassword(req.getParameter("pass")); 
System.out.println("---para" + bean.getEmail() + bean.getPassword()); 
RegisterDao dao = new RegisterDao(); 
try { 
String result = dao.login(bean); 
if (result.equalsIgnoreCase("ADMIN_ROLE")) { 
52 
System.out.println("Admin Home"); 
HttpSession session = req.getSession(); 
session.setAttribute("Admin", bean.getEmail()); 
session.setAttribute("status", "login"); 
req.getRequestDispatcher("viewNetwork.jsp").forward(req, resp); 
} else if (result.equalsIgnoreCase("USER_ROLE")) { 
System.out.println("User Home"); 
HttpSession session = req.getSession(); 
session.setAttribute("User", bean.getEmail()); 
session.setAttribute("status", "login"); 
req.setAttribute("name", "Welcome " + bean.getEmail()); 
req.getRequestDispatcher("send_file.jsp").forward(req, resp); 
}else if(result.equalsIgnoreCase("ATTACK_ROLE")){ 
System.out.println("Attacker Home"); 
HttpSession session = req.getSession(); 
session.setAttribute("Attack", bean.getEmail()); 
session.setAttribute("status", "login"); 
req.setAttribute("name", "Welcome " + bean.getEmail()); 
req.getRequestDispatcher("unotherize.jsp").forward(req, resp); 
53 
} else { 
req.setAttribute("alert", "Login Denied Check Username/password"); 
req.getRequestDispatcher("login.jsp").forward(req, resp); 
}} catch (SQLException e) { 
e.printStackTrace(); 
} 
@Override 
} 
protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
throws ServletException, IOException { 
doGet(req, resp); 
} 
} 
ProcessTimeControll.java 
package com.controller; 
import java.io.IOException; 
import java.sql.SQLException; 
import java.util.List; 
import javax.servlet.ServletException; 
import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServlet; 
54 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession; 
import com.bean.SendFileBean; 
import com.dao.CommonDao; 
@WebServlet("/processTime") 
public class ProcessTimeControll extends HttpServlet { 
private static final long serialVersionUID = 605084673217463714L; 
@Override 
protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
throws ServletException, IOException { 
HttpSession session = req.getSession(); 
String user = (String) session.getAttribute("User"); 
CommonDao dao = new CommonDao(); 
try { 
List<SendFileBean> list = dao.getProcessTime(user); 
if (list != null) { 
req.setAttribute("list", list); 
req.getRequestDispatcher("processTime.jsp").forward(req, resp); 
} 
else 
{ 
resp.sendRedirect("processTime.jsp"); 
} 
55 
} catch (SQLException se) { 
se.printStackTrace(); 
} 
} 
} 
AdminUserControll.java 
package com.controller; 
import java.io.IOException; 
import java.sql.SQLException; 
import java.util.List; 
import javax.servlet.ServletException; 
import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import com.bean.RegisterBean; 
import com.dao.CommonDao; 
56 
@WebServlet("/adminUsers") 
public class AdminUserControll extends HttpServlet { 
private static final long serialVersionUID = -2367636934845778430L; 
@Override 
protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
throws ServletException, IOException { 
CommonDao dao = new CommonDao(); 
try { 
List<RegisterBean> list = dao.getAllUser(); 
if (list != null) { 
req.setAttribute("list", list); 
req.getRequestDispatcher("admin_users.jsp").forward(req, resp); 
} 
else 
{ 
req.getRequestDispatcher("admin_users.jsp").forward(req, resp); 
} 
} catch (SQLException se) { 
se.printStackTrace(); 
} 
}
