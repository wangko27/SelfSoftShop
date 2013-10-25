<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes.jsp"%>
<%@ include file="/WEB-INF/view/header.jsp"%>


<div class="easyui-layout" style="width:700px;height:350px;margin-right:auto;margin-left:auto;">
		<div data-options="region:'north',border:false" style="height:50px"></div>
		<div data-options="region:'south',border:false" style="height:50px;"></div>
	
		<div data-options="region:'center',title:'Provision Console System'">
			<div style="padding:50px 0 10px 200px">
	    <form id="ff" method="post">
	    	<table>
	    		<tr>
	    			<td>Name:</td>
	    			<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>Email:</td>
	    			<td><input class="easyui-validatebox" type="text" name="email" data-options="required:true,validType:'email'"></input></td>
	    		</tr>
	    		<tr>
	    			<td></td>
	    			<td><a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
	    			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
		</div>
	</div>
<%@ include file="/WEB-INF/view/footer.jsp"%>