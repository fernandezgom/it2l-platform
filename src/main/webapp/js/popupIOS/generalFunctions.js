/**
 * 
 */

//funcion para importar otros recursos (javascript, css) dinamicamente cuando se necesiten
function loadScript(url, funcionRegreso) {
    $.getScript(url, funcionRegreso);
}

//funcion para obtener parametros en GET de url actual
function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||"en";
}

//funcion para obtener parametros en GET de url actual
function getURLParameter2(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
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

function getLocale(){
	var l_lang=getParameterByName("locale");
	if (l_lang=="" && navigator.userLanguage) // Explorer
	  l_lang = navigator.userLanguage;
	else if (l_lang=="" && navigator.language) // FF
	  l_lang = navigator.language;
	else if (l_lang=="")
	  l_lang = "en";
	return l_lang;
}



function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
