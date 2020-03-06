/**
 * 
 */

$(function() {
	calc_price();
	
	$(".size").on("change",function() {
		calc_price();
	});

	$(".checkbox").on("change",function() {
		calc_price();
	});

	$("#ramennum").on("change",function() {
		calc_price();
	});

	// 値段の計算をして変更する関数
	function calc_price() {
		var size = $('input[name="size"]:checked').val();
		var topping_count = $("#topping input:checkbox:checked").length;
		var ramen_num = $("#ramennum option:selected").text();
		if (size == "M") {
			var size_price = $("#sizeM").text();
			var topping_price = 200 * topping_count;
		} else {
			var size_price = $("#sizeL").text();
			var topping_price = 300 * topping_count;
		}
		
		var price = (parseInt(size_price) + topping_price) * ramen_num;
		$("#totalprice").text(price.toLocaleString());
	}
	;
});