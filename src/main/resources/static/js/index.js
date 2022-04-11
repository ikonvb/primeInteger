
//let webSocket = new WebSocket("ws://localhost:5000/message-socket");
//"use strict";
//var connection = null;
//var serverUrl;
//var scheme = "ws";
//serverUrl = scheme + "://" + document.location.hostname + ":5000" + "/message-socket";
//console.log(serverUrl);
//connection = new WebSocket(serverUrl);
//connection.onopen = function(evt) {
//    console.log(evt);
////    document.getElementById("text").disabled = false;
////    document.getElementById("send").disabled = false;
//};

//  connection.onmessage = function(evt) {
//    console.log("***ONMESSAGE");
//    var f = document.getElementById("chatbox").contentDocument;
//    var text = "";
//    var msg = JSON.parse(evt.data);
//    console.log("Message received: ");
//    console.dir(msg);
//    var time = new Date(msg.date);
//    var timeStr = time.toLocaleTimeString();
//
//    switch(msg.type) {
//      case "id":
//        clientID = msg.id;
//        setUsername();
//        break;
//      case "username":
//        text = "<b>User <em>" + msg.name + "</em> signed in at " + timeStr + "</b><br>";
//        break;
//      case "message":
//        text = "(" + timeStr + ") <b>" + msg.name + "</b>: " + msg.text + "<br>";
//        break;
//      case "rejectusername":
//        text = "<b>Your username has been set to <em>" + msg.name + "</em> because the name you chose is in use.</b><br>";
//        break;
//      case "userlist":
//        var ul = "";
//        var i;
//
//        for (i=0; i < msg.users.length; i++) {
//          ul += msg.users[i] + "<br>";
//        }
//        document.getElementById("userlistbox").innerHTML = ul;
//        break;
//    }
//
//    if (text.length) {
//      f.write(text);
//      document.getElementById("chatbox").contentWindow.scrollByPages(1);
//    }
//  };
//  console.log("***CREATED ONMESSAGE");
//}

//function send() {
//  console.log("***SEND");
//  var msg = {
//    text: document.getElementById("text").value,
//    type: "message",
//    id: clientID,
//    date: Date.now()
//  };
//  connection.send(JSON.stringify(msg));
//  document.getElementById("text").value = "";
//}

//function handleKey(evt) {
//  if (evt.keyCode === 13 || evt.keyCode === 14) {
//    if (!document.getElementById("send").disabled) {
//      send();
//    }
//  }
//}





//=============================================================================
//var stompClient = null;
//
//$(document).ready(function(){
//
//    createConnection();
//
//    $("#send").click(function(){
//    	sendMessageToSocketWithVal();
//    });
//
//    $("#auto").click(function(){
//        sendMessageToSocket();
//    });
//
//    $("#generate").click(function(){
//        sendMessageToSocket();
//    });
//
//});
//
//function createConnection() {
//	if(stompClient!=null) {
//	    stompClient.disconnect();
//	}
//
//	var socket = new SockJS('/message-socket');
//	stompClient = Stomp.over(socket);
//
//	stompClient.connect({}, function(connectionData) {
//	    stompClient.subscribe('/topic/message', function(data) {
//	       $.each(data, function (key, val) {
//	           $("<tr><td>" + val + "</td></tr>").appendTo("#receiveMsg");
//           });
////           data = Object.entries(data);
////           for(var i = 0;i<data.length;i++) {
////               $("<tr><td>" + data + "</td></tr>").appendTo("#receiveMsg");
////           }
//	        //$("#receiveMsg").append('<table> : '+ data.body +  '</table>');
//    	})
//	})
//
//}
//
//function sendMessageToAutoSocket() {
//    var message = 2;
//    stompClient.send("/app/autoarrays", {}, message);
//}
//
//function sendMessageToSocket() {
//    var message = 1;
//    stompClient.send("/app/genarrays", {}, message);
//}
//
//function sendMessageToSocketWithVal() {
//	var clientMessage = $("#message").val();
////	stompClient.send("/app/scorecard", {}, JSON.stringify({'clientMessage': clientMessage }));
//    stompClient.send("/app/numarray", {}, clientMessage);
//    $("#message").val('');
//}
