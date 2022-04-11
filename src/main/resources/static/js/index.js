
var stompClient = null;

$(document).ready(function(){

    createConnection();
	
    $("#send").click(function(){
    	sendMessageToSocketWithVal();
    });

    $("#auto").click(function(){
        sendMessageToSocket();
    });

    $("#generate").click(function(){
        sendMessageToSocket();
    });

});

function createConnection() {
	if(stompClient!=null) {
	    stompClient.disconnect();
	}

	var socket = new SockJS('/message-socket');
	stompClient = Stomp.over(socket);

	stompClient.connect({}, function(connectionData) {
	    stompClient.subscribe('/topic/message', function(data) {
//	       $.each(data, function (key, val) {
//	           $("<tr><td>" + val + "</td></tr>").appendTo("#receiveMsg");
//           });
           data = Object.entries(data).;
           for(var i = 0;i<data.length;i++) {
               $("<tr><td>" + data + "</td></tr>").appendTo("#receiveMsg");
           }
	        //$("#receiveMsg").append('<table> : '+ data.body +  '</table>');
    	})
	})

}

function sendMessageToAutoSocket() {
    var message = 2;
    stompClient.send("/app/autoarrays", {}, message);
}

function sendMessageToSocket() {
    var message = 1;
    stompClient.send("/app/genarrays", {}, message);
}

function sendMessageToSocketWithVal() {
	var clientMessage = $("#message").val();
//	stompClient.send("/app/scorecard", {}, JSON.stringify({'clientMessage': clientMessage }));
    stompClient.send("/app/numarray", {}, clientMessage);
    $("#message").val('');
}
