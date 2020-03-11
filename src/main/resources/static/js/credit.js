$(function() {
	$("#money").on("change", function() {
		console.log("aa")
		if($("#money").val() == 1) {
			$("#creditField").css("display","none")
			console.log("1")
		}
	})
	$("#credit").on("change", function() {
		console.log("aa")
		if ($("#credit").val() == 2){
			$("#creditField").css("display","block")			
			console.log("2")
		}
	})
	
})