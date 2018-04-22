<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta charset="UTF-8">
    <title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"/>
  </head>
  <body>
  
 	  <h1>index.jsp</h1><p/><p/><p/>
 	  
 	  <a href="<%=request.getContextPath()%>/testSpringMvc">測試 SpringMVC 導頁</a><p/>
 	  
 	  <button type="button" id="broadcast_Start_Btn" class="btn btn-danger">開始廣播</button>&nbsp;&nbsp;
 	  <button type="button" id="broadcast_Stop_Btn"  class="btn btn-default">停止廣播</button>
 	  <p/><p/>
 	  
 	  <b>WebSocket connected ?</b> &nbsp;&nbsp;
 	  <button type="button" id="disconnect" class="btn btn-secondary">Disconnected</button>&nbsp;&nbsp;
      <button type="button" id="connect" class="btn btn-success">Connected</button>
  		
  	  <b>您的名子是?</b><input id="userName" type="text"/>&nbsp;&nbsp;
  	  <button type="button" id="send" class="btn btn-primary">Send</button> <p/><p/><p/><hr/>
  
  	  <div id="conversation" class="container">
	  	  <h4>訊息 Greetings</h4>
	  	  <table id="greetings_table" class="table table-striped">
	  	  	
	  	  </table>
  	  </div>
  
      <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
      <script type="text/javascript">
	      var stompClient = null;  
	      
	      function setConnected(connected) {  
	          $("#connect").prop("disabled", connected);  
	          $("#disconnect").prop("disabled", !connected);  
	          if (!!connected) {  
	              $("#conversation").show();  
	          }  
	          else {  
	              $("#conversation").hide();  
	          }  
	          $("#greetings_table").html("");  
	      }  
	        
	      function connect() {  
	          var socket = new SockJS('<%=request.getContextPath()%>/myStompEndPoint');  
	          stompClient = Stomp.over(socket);  // 建立 Socket 連線，到 StompEndpoints
	          stompClient.connect({}, function (frame) {  
	              setConnected(true);  
	              console.log('Connected: ' + frame);  
	              stompClient.subscribe('/topic/greetings', function ( incomingMsg ) {  
	            	  console.log( 'incomingMsg >>> ' , incomingMsg );
	                  // showGreeting( JSON.parse( incomingMsg.body ) );  
	                  showGreeting( incomingMsg.body );  
	              });  
	          });  
	      }
	      
	        
	      function disconnect() {  
	          if (stompClient != null) {  
	              stompClient.disconnect();  
	          }  
	          setConnected(false);  
	          console.log("Disconnected");  
	      }  
	        
	      function sendName() {  
	          stompClient.send("/appFuck/hello", {}, JSON.stringify( { 'userName': $("#userName").val() } ));  
	      }   
	        
	      function showGreeting( messageIncoming ) {  
	          $("#greetings_table").append("<tr><td>" + messageIncoming + "</td></tr>");  
	      }  
	        
	      $(function () {  
	          $( "#connect" ).click(function() { connect(); });  
	          $( "#disconnect" ).click(function() { disconnect(); });   
	          $( "#send" ).click(function() { sendName(); });  
	          
	          $('#broadcast_Start_Btn').click(function (){
	           	  $.get("<%=request.getContextPath()%>/chatController/broadcast" , { status : "start" } )
	           	  	  .done( function( cbData ){
	           	  		  console.log( 'cbData = ' , cbData );
	           	  	   }
	           	  );
			  });
	          
	          $('#broadcast_Stop_Btn').click(function (){
	           	  $.get('<%=request.getContextPath()%>/chatController/broadcast?status=stop', function( cbData ){
	        		  console.log( 'cbData = ' , cbData );
	        	  });
			  });
	          
	      });  
      </script>
      
  </body>
</html>
