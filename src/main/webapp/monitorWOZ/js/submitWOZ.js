$(document).ready(function() {
	   $("#testConn").hide();
	   $("#nextBHandler").hide();
	   //$("#doneBHandler").hide();
	   $("#submit").click(function() {
		   submitWOZ();
	   });
	   $("#stCondition").click(function() {
		   submitCondition();
	   });
	   $("#submitUser").click(function() {
		   $("#testConn").hide();
		   $("#nextBHandler").hide();
		   //$("#doneBHandler").hide();
		   $.ajax({
				type: 'GET',
				url: "/italk2learn/sequence/getUser",
				success: function (data) {
					//JLF: Call connect WOZ. If it's connected or authfail initialises the container.
					connectWOZ($('#usList').val(),data);
				},
				error: function (jqXHR, status, error) {
					$(document).trigger('error');
				}
			});
	   });
	   $("#testConn").click(function() {
		   sendMessage($('#usList').val(),"SYN");
	   });
	   $("#nextBHandler").click(function() {
		   if ($("#nextBHandler").attr('value').localeCompare("Disable Next")==0)
		   {
			   $("#nextBHandler").attr('value', 'Enable Next');
			   sendMessage($('#usList').val(),"d*n");
		   } 
		   else{
			   $("#nextBHandler").attr('value', 'Disable Next');
			   sendMessage($('#usList').val(),"e*n");
		   }
	   });
//	   $("#doneBHandler").click(function() {
//		   if ($("#doneBHandler").attr('value').localeCompare("Disable Done")==0)
//		   {
//			   $("#doneBHandler").attr('value', 'Enable Done');
//			   sendMessage($('#usList').val(),"d*d");
//		   } 
//		   else{
//			   $("#doneBHandler").attr('value', 'Disable Done');
//			   sendMessage($('#usList').val(),"e*d");
//		   }
//		   
//	   });
	   
	 });

function submitWOZ(){
	var sub = {
       	 "sequence": $('#exList').val().replace(/\s+/g, '').split(","), 
    	 "user": $('#user').val() 	
        };
    $.ajax({
        type: 'POST',
        contentType : 'application/json; charset=utf-8',
        dataType : 'json',
        url: "insertSequenceByUser",
        data: JSON.stringify(sub),
        success: function(data){
        	alert('Change submitted!');
        },
        error : function(jqXHR, status, error) {
           alert('Sorry!, there was a problem');
        },
//        complete : function(jqXHR, status) {
//           alert('Done!');
//        }
    });
}

function submitCondition(){
	var sub = {
       	 "condition": $('#coList').val(), 
    	 "user": $('#user2').val(),
    	 "lang": getLocale()
        };
    $.ajax({
        type: 'POST',
        contentType : 'application/json; charset=utf-8',
        dataType : 'json',
        url: "insertConditionByUser",
        data: JSON.stringify(sub),
        success: function(data){
        	alert('Change submitted!');
        },
        error : function(jqXHR, status, error) {
           alert('Sorry!, there was a problem');
        },
//        complete : function(jqXHR, status) {
//           alert('Done!');
//        }
    });
    
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
}