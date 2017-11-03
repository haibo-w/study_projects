<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
      var wsocket;
      var send = false;
      function connect() {
          wsocket = new WebSocket("ws://localhost:8080/servlet3.x_websocket/receive/"+new Date().getTime());
          //wsocket.onmessage = onMessage;
          wsocket.onopen = function () {
        	  setInterval(function() {
        		  if(send){
        	    	if (wsocket.bufferedAmount == 0)
        	    		wsocket.send(getUpdateData());
        	  	}
        	  }, 1000);
        	};
      }
      function onMessage(evt) {
          var arraypv = evt.data.split("/");
          document.getElementById("price").innerHTML = arraypv[0];
          document.getElementById("volume").innerHTML = arraypv[1];
      }
      
      function getUpdateData(){
    	  var val = document.getElementById("sendTxt").value;
    	  return val;
      }
      
     window.addEventListener("load", connect, false);
  </script>
</head>
<body>
    <h1>Duke's WebSocket ETF</h1>
    
    <table>
        <tr>
            <td style="width:100px">Ticker</td>
            <td style="text-align:center">Price</td>
            <td id="price" style="font-size:24pt;font-weight:bold;">--.--</td>
        </tr>
        <tr>
            <td style="font-size:18pt;font-weight:bold;width:100px">DKEJ</td>
            <td style="text-align:center">Volume</td>
            <td id="volume" align="right">--</td>
        </tr>
    </table>
    <hr/>
    <input type="text" id="sendTxt"> <input type="button" value="start" onclick="window.send=true"><input type="button" value="stop" onclick="window.send=false">
</body>
</html>