/**
 * 
 */

//function for import resources in dinamic execution (javascript, css) async
function loadScript(url, funcionRegreso) {
    $.getScript(url, funcionRegreso);
}

//function for import resources in dinamic execution (javascript, css) sync
function loadScript2(url, res){
	$.ajax({
    	async:false,
    	type:'GET',
    	url: url,
    	data:null,
    	success:function(data){
    		res = data;
    	},
    	dataType:'script',
    	error: function(xhr, textStatus, errorThrown) {
        	// Look at the `textStatus` and/or `errorThrown` properties.
        	alert(xhr);
    	}
	});
}

//funcion para obtener parametros en GET de url actual
function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||"en";
}