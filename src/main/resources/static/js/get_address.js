$(function() {

	$("#searchAddress").on("click", function() {

		AjaxZip3.zip2addr('zipcode', '', 'address', 'address');
		
	});
	
	$("#searchAddress2").on("click", function() {

		AjaxZip3.zip2addr('destinationZipcode', '', 'destinationAddress', 'destinationAddress');
		

	});
});
