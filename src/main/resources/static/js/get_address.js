$(function() {

	$("#searchAddress").on("click", function() {

		AjaxZip3.zip2addr('zipcode', '', 'address', 'address');
		
	});
	
	$("#searchAddress2").on("click", function() {

		AjaxZip3.zip2addr('destinationZipcode', '', 'destinationAddress', 'destinationAddress');
		

	});
});

$("#orderinfo").fadeOut();
$("#order").on('click',()=>{
	$("#orderinfo").fadeIn();
});
$("#card").fadeOut();
$('[name="paymentMethod"]:radio').on("change", function(){
	if($('[id=1]').prop('checked')){
		$("#card").fadeOut();
	}else if($('[id=2]').prop('checked')){
		$("#card").fadeIn();
	}


});

