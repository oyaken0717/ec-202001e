/**
 * 
 */

$(function() {
	$('#passcheck').change(function() {
		if ($(this).prop('checked')) {
			$('#inputPassword').attr('type', 'text');
		} else {
			$('#inputPassword').attr('type', 'password');
		}
	});
});