
$(function(){
	
	$("#searchAddress").on("click", function(){
		AjaxZip3.zip2addr('zipcode','','address','address');
		
	});
	
	$("#searchAddress2").on("click", function(){
		AjaxZip3.zip2addr('zip2','','address2','address2');
	});
});


/*
$('#get_address_btn').on("click", function(){
	$.ajax({
		url: "http://zipcoda.net/api", 
		dataType: "jsonp", 
		data: { 
			zipcode: $('#zipcode').val()
		 },
		async: true 
		
	}).done(function(data){
		console.dir(JSON.stringify(data));
		$("#address").val(data.items[0].address);
		
	}).fail(function(XMLHttpRequest, textStatus, errorThrown){
		alert('正しい結果を得られませんでした。');
		console.log("XMLHttpRequest : " + XMLHttpRequest.status);
		console.log("textStatus : " + textStatus);
		console.log("errorThrown : " + errorThrown.message);
		
	});
  });
  */



