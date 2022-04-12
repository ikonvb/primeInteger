//=============================================================================
var stompClient = null;

$(document).ready(function(){

    createConnection();

    $("#send").click(function(){
    	sendMessageToSocketWithVal();
    });

    $("#auto").click(function(){
        sendMessageToAuto();
    });

    $("#generate").click(function(){
        sendMessageToGenerate();
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
	        var response = JSON.parse(data.body);

	        $('#receiveMsg').children().remove();
	        $('#receiveMsg').append('<tbody></tbody>');
	        var tbody = $('#receiveMsg').children();

            $.each(response, function(responseIndex, responseValue) {
                $.each(responseValue, function(i, item) {
                    console.log(item);
                    tbody.append( '<tr><td>'+  item + '</td></tr>' );
                })
            });
            $('table').attr('class', 'table table-striped');

    	})

    	stompClient.subscribe('/topic/randmessage', function(data) {

    	    var response = JSON.parse(data.body);

        	$('#receiveRandomMsg').children().remove();
        	$('#receiveRandomMsg').append('<tbody></tbody>');
        	var tbody = $('#receiveRandomMsg').children();

            $.each(response, function(responseIndex, responseValue) {
                $.each(responseValue, function(i, item) {
                    console.log(item);
                    tbody.append( '<tr><td>'+  item + '</td></tr>' );
                })
            });
            $('table').attr('class', 'table table-striped');
        })
	})
}

function sendMessageToGenerate() {
	var clientMessage = $("#generate").val();
	stompClient.send("/app/autoarray", {}, JSON.stringify({'clientMessage': clientMessage }));
    console.log(clientMessage);
}

function sendMessageToAuto() {
	var clientMessage = $("#auto").val();
	stompClient.send("/app/autoarray", {}, JSON.stringify({'clientMessage': clientMessage }));
    console.log(clientMessage);
}

function sendMessageToSocketWithVal() {
	var clientMessage = $("#message").val();
	stompClient.send("/app/numarray", {}, JSON.stringify({'clientMessage': clientMessage }));
    //stompClient.send("/app/numarray", {}, clientMessage);
    console.log(clientMessage);
    $("#message").val('');
}

