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
    
       <input type="button" id="bulkRequests" value="Hit Bulk Request"></input>  
     </div><!-- end of right content-->
            
                    
  </div>   <!--end of center content -->               
                    
                    
    
    
    <div class="clear"></div>
    </div> <!--end of main content-->
	
    
    <div class="footer">
    
    	<div class="left_footer">IN ADMIN PANEL | Powered by <a href="http://indeziner.com">No body</a></div>
    	
    
    </div>

</div>		

<script type="text/javascript">


var requestCount=0;

$( document ).ready(function() {
	$("#createRequest").click(function(){
		$.ajax({
			url:"/api/simple-service",
			async:true,
			success:function(result){
		    
		  }
		
		});		
	
		alert("Request hitted");
	});
	
	$("#bulkRequests").click(function(){		
					
			hitBulkReq();		
			
		
	});	

});

function hitBulkReq(){
	
	$.ajax({
		url:"/api/simple-service?id=1",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});	
	setTimeout(hitReq2, 1000)
}

function hitReq2(){
	$.ajax({
		url:"/api/simple-service?id=2",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});	
	 setTimeout(hitReq3, 1000)
}

function hitReq3(){
	$.ajax({
		url:"/api/simple-service?id=3",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});	
	 setTimeout(hitReq4, 1000)
}
function hitReq4(){
	$.ajax({
		url:"/api/simple-service?id=4",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});	
	 setTimeout(hitReq5, 1000);
}
function hitReq5(){
	$.ajax({
		url:"/api/simple-service?id=5",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});	
	 setTimeout(hitReq6, 1000);
}
function hitReq6(){
	$.ajax({
		url:"/api/simple-service?id=6",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});	
	 setTimeout(hitReq7, 1000);
}
function hitReq7(){
	$.ajax({
		url:"/api/simple-service?id=7",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});	
	 setTimeout(hitReq8, 1000);
}
function hitReq8(){
	$.ajax({
		url:"/api/simple-service?id=8",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});		 
	 setTimeout(hitReq9, 1000);
}
function hitReq9(){
	$.ajax({
		url:"/api/simple-service?id=9",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});		 
	 setTimeout(hitReq10, 1000);
}
function hitReq10(){
	$.ajax({
		url:"/api/simple-service?id=10",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});		 
	 setTimeout(hitReq11, 1000);
}
function hitReq11(){
	$.ajax({
		url:"/api/simple-service?id=11",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});		 
	 setTimeout(hitReq12, 1000);
}
function hitReq12(){
	$.ajax({
		url:"/api/simple-service?id=12",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});		 
	 setTimeout(hitReq13, 1000);
}
function hitReq13(){
	$.ajax({
		url:"/api/simple-service?id=13",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});		 
	 setTimeout(hitReq14, 1000);
}
function hitReq14(){
	$.ajax({
		url:"/api/simple-service?id=14",
		async:true,
		timeout: 2000,
		success:function(result){
	    
	  }
	
	});		 
	 setTimeout(hitBulkReq, 30000);
}

</script>
</body>
</html>