'use-strict'
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
                    tbody.append( '<tr><td>'+  item + '</td></tr>' );
                })
            });
            $('table').attr('class', 'table table-striped');
    	})

    	stompClient.subscribe('/topic/randmessage', function(data) {

        	$('#receiveRandomMsg').children().remove();
        	$('#receiveRandomMsg').append('<tbody></tbody>');
        	var tbody = $('#receiveRandomMsg').children();
            var response = JSON.parse(data.body);

            $.each(response, function(responseIndex, responseValue) {
                $.each(responseValue, function(i, item) {
                    tbody.append( '<tr><td>'+  item + '</td></tr>' );
                })
            });
            $('table').attr('class', 'table table-striped');
        })

        stompClient.subscribe('/topic/automessage', function(data) {

            $('#receiveAutoMsg').children().remove();
            $('#receiveAutoMsg').append('<tbody></tbody>');
            var tbody = $('#receiveAutoMsg').children();
            var response = JSON.parse(data.body);
            tbody.append( '<tr><td>'+  response + '</td></tr>' );
            $('table').attr('class', 'table table-striped');
        })
	})
}

function sendMessageToGenerate() {
	var clientMessage = $("#generate").val();
	stompClient.send("/app/randarray", {}, JSON.stringify({'clientMessage': clientMessage }));
}

function sendMessageToAuto() {
	var clientMessage = $("#auto").val();
	stompClient.send("/app/autoarray", {}, JSON.stringify({'clientMessage': clientMessage }));
}

function sendMessageToSocketWithVal() {
	var clientMessage = $("#message").val();
	stompClient.send("/app/numarray", {}, JSON.stringify({'clientMessage': clientMessage }));
    $("#message").val('');
}

