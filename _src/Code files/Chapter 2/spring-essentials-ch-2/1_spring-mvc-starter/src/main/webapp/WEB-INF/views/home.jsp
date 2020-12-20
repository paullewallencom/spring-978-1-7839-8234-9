<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Taskify :: Home</title>
</head>
<body>
<h1>
	Welcome to Taskify!
</h1>
<hr/>
<P>  There are ${totalOpenTasks}(${totalTasks}) open tasks. </P>
<hr/>
<h4>
This is a Spring MVC Starter Project. You can use this to quickly get your application up and running.</h4>
<p> You may start by changing your application name, package names etc. for your convenience.</p>
<p> Don't forget to update &lt;context:component-scan base-package="com.taskify" /&gt; inside servlet-context.xml.</p>
</p>

</body>
</html>
