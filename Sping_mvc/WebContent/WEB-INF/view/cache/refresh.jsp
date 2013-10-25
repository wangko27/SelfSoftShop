<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Refresh Cache</title>

</head>
<style type="text/css" media=screen>
body {
	text-align: center;
}

#maindiv {
	width: 400px;
	height: 300px;
	border: 1px solid red;
	text-align: center;
	padding-top: 20px;
}
</style>


<div><span>After you do some configuration changes, please refresh related cache model here, otherwise, the latest data can't be effective</span></div>
<div id="maindiv">
	<span>Please choose which cache model to refresh :</span> 
	<form action="./cache/refresh">
	<select id="cacheModel" name="cacheModel" >
		<option value="ALL">ALL</option>
		<option value="LEAD_TIME">LEAD TIME</option>
	</select> 
	
	<span><input type="submit" value="refresh"/></span>
	</form>
</div>



</html>