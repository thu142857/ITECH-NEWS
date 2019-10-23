$(document).ready(function (e) {

	const TIMEOUT_DISPLAY_ALERT_MESSAGE = 7000;
	//effect alert message
	$('.message-alert').addClass('message-alert-effect');
	setTimeout(function(){
		$('.message-alert').removeClass('message-alert-effect');
	}, TIMEOUT_DISPLAY_ALERT_MESSAGE);
});