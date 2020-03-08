
$(function(){
	

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

$("#searchAddress").on("click",function(){
	AjaxZip3.zip2addr('zip11','','addr11','addr11');
});

});