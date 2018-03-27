// $(function() {}); = $(document).ready(function() {....});
$(function(){
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	var registerUrl = '/o2o/shopadmin/registershop';
	function getShopInitInfo(){
		$.getJSON(initUrl, function(data){
			if(data.success){
				var categoryHtml = "<option>";
				
			}
		});
	}
})