<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IN ADMIN PANEL | Powered by INDEZINER</title>
<link rel="stylesheet" type="text/css" href="/static/css/style.css" />
<script type="text/javascript" src="/static/js/clockp.js"></script>
<script type="text/javascript" src="/static/js/clockh.js"></script> 
<script type="text/javascript" src="/static/js/jquery.min.js"></script>
<script type="text/javascript" src="/static/js/ddaccordion.js"></script>
<script type="text/javascript">
ddaccordion.init({
	headerclass: "submenuheader", //Shared CSS class name of headers group
	contentclass: "submenu", //Shared CSS class name of contents group
	revealtype: "click", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
	mouseoverdelay: 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
	collapseprev: true, //Collapse previous content (so only one open at any time)? true/false 
	defaultexpanded: [], //index of content(s) open by default [index1, index2, etc] [] denotes no content
	onemustopen: false, //Specify whether at least one header should be open always (so never all headers closed)
	animatedefault: false, //Should contents open by default be animated into view?
	persiststate: true, //persist state of opened contents within browser session?
	toggleclass: ["", ""], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
	togglehtml: ["suffix", "<img src='images/plus.gif' class='statusicon' />", "<img src='images/minus.gif' class='statusicon' />"], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
	animatespeed: "fast", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
	oninit:function(headers, expandedindices){ //custom code to run when headers have initalized
		//do nothing
	},
	onopenclose:function(header, index, state, isuseractivated){ //custom code to run whenever a header is opened or closed
		//do nothing
	}
})
</script>

<script type="text/javascript" src="/static/js/jconfirmaction.jquery.js"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		$('.ask').jConfirmAction();
	});
	
</script>

<script language="javascript" type="text/javascript" src="/static/js/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="/static/css/niceforms-default.css" />

</head>
<body>
<div id="main_container">

	<div class="header">
    <div class="logo"><a href="#"><img src="" alt="" title="" border="0" /></a></div>
    
    <div class="right_header">Welcome Admin, <a href="#">Visit site</a> | <a href="/logout" class="logout">Logout</a></div>
    <div id="clock_a"></div>
    </div>
    
    <div class="main_content">
    
                    <div class="menu">
                    <ul>
                    <li><a class="current" href="#">Admin Home</a></li>
                    <li><a href="/blocked-users">Blocked Users<!--[if IE 7]><!--></a><!--<![endif]--></li>
                    <li><a class="" href="/admin/activate-monitor">Demon Monitor</a></li>                   
                    </ul>
                    </div> 
                    
                    
                    
                    
    <div class="center_content">  
    <div class="right_content">            
    
    <div >    
    <h2 style="display: inline-block; float: left;">Add New Server</h2> <a style="display: inline-block; float: right;" href="admin/add-new-server"></a> 
    </div>                
    
    
    <form action="/admin/update-server">
    <table id="rounded-corner" summary="Blocked Users">
    
    <tbody>        
        <tr>   	 
        	<td>Name</td>
            <td><input type="text" name="name" value="${server.name}"/></td>
            
        </tr>
        <tr>   	 
        	<td>IP Address</td>
            <td><input type="text" name="ip" value="${server.ip}"/></td>
            
        </tr>
        <tr>   	 
        	<td>Port Number</td>
            <td><input type="text" name="portNumber" value="${server.portNumber}"/></td>
            
        </tr>
        <tr>   	 
        	<td>Request Capacity</td>
            <td><input type="text" name="requestCapacity" value="${server.requestCapacity}"/></td>
            
        </tr>
        <tr>   	 
        	<td>Request Capacity Threshold</td>
            <td><input type="text" name="capacityThreshold" value="${server.capacityThreshold}"/></td>
            
        </tr>
        <tr>   	 
        	<td>Status</td>
            <td><select name="status">
          		<option value="ACTIVE">Active</option>
          		<option value="INACTIVE">Inactive</option>
          		<option value="SUSPENDED">Suspended</option>
          	</select></td>
            
        </tr>
        <tr>   	 
        	<td>Request Migration</td>
            <td><select name="migrationActive">
          		<option value="true">Active</option>
          		<option value="false">Inactive</option>          		
          	</select></td>
            
        </tr>  
        <tr>   	
        	<td><input type="hidden" name="id" value="${server.id}"/>
        	<input type="submit" value="update"/></td>           
        </tr>         
         
    </tbody>
</table>
</form>   	
          	
     </div><!-- end of right content-->
            
                    
  </div>   <!--end of center content -->               
                    
                    
    
    
    <div class="clear"></div>
    </div> <!--end of main content-->
	
    
    <div class="footer">
    
    	<div class="left_footer">IN ADMIN PANEL | Powered by <a href="http://indeziner.com">No body</a></div>
    	
    
    </div>

</div>		
</body>
</html>