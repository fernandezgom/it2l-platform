		$(document).ready(function() { 
			$.ajax({
				type: 'GET',
				url: "/italk2learn/sequence/getUser",
				success: function (data) {
					$('#user').html(data);
					getCondition(data);
					//changeLabels(getURLParameter("lang"));
				},
				error: function (jqXHR, status, error) {
					$(document).trigger('error');
				}
			});
		});
		
		function getCondition(user){
			$.ajax({
		        type: 'GET',
		        url: "/italk2learn/sequence/getCondition",
		        success: function(data, textStatus, jqXHR){
		        	$('#condition').html(" "+data);
		        	if (data==4){
		        		generateRamdomQuizCondition4(user);
		        	} else {
		        		generateRamdomQuiz(user);
		        	}
		        },
		        error : function(jqXHR, status, error) {
		           alert('Sorry!, no condition retrieved, reloading webpage');
		           window.location.href = "/italk2learn/exercise/main";
		        },
		        complete : function(jqXHR, status) {
		        }
		    });
			
		}
		
		function generateRamdomQuizCondition4(user) {
			var str = user; 
		    var res = str.slice(7, str.length);
		    document.getElementById("pre").innerHTML = '';
		    document.getElementById("post").innerHTML = '';
		    var ques="Getting Started";
		    var ques2="My evaluation";
		    if (getLocale().indexOf("es")>-1) {
		    	ques = "Empezar";
		    	ques2 = "Mi evaluacion";
			} else if (getLocale().indexOf("de")>-1) {
				$("#q3").html("Los geht’s!");
				ques = "Aufwärmübungen";
				ques2 = "Übungen";
			}
		    if (isEven(res)){
		    	$('#pre').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q1' href='/italk2learn/exercise/preCond4'>"+ques+"</a>");
		    	$('#post').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q2' href='/italk2learn/exercise/postCond4'>"+ques2+"</a>");	
			} else if (isOdd(res)){
				$('#post').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q1' href='/italk2learn/exercise/preCond4'>"+ques2+"</a>");
		    	$('#pre').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q2' href='/italk2learn/exercise/postCond4'>"+ques+"</a>");
			} else{
				var hs= hashCode(user);
				if (isEven(hs)){
					$('#pre').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q1' href='/italk2learn/exercise/preCond4'>"+ques+"</a>");
			    	$('#post').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q2' href='/italk2learn/exercise/postCond4'>"+ques2+"</a>");		
				} else if (isOdd(hs)){
					$('#post').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q1' href='/italk2learn/exercise/preCond4'>"+ques2+"</a>");
			    	$('#pre').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q2' href='/italk2learn/exercise/postCond4'>"+ques+"</a>");
				}
			}
		}

		function generateRamdomQuiz(user) {
			var str = user; 
		    var res = str.slice(7, str.length);
		    document.getElementById("pre").innerHTML = '';
		    document.getElementById("post").innerHTML = '';
		    var ques="Getting Started";
		    var ques2="My evaluation";
		    if (getLocale().indexOf("es")>-1) {
		    	ques = "Empezar";
		    	ques2 = "Mi evaluacion";
			} else if (getLocale().indexOf("de")>-1) {
				$("#q3").html("Los geht’s!");
				ques = "Aufwärmübungen";
				ques2 = "Übungen";
			}
		    if (isEven(res)){
		    	$('#pre').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q1' href='/italk2learn/exercise/preB'>"+ques+"</a>");
		    	$('#post').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q2' href='/italk2learn/exercise/postA'>"+ques2+"</a>");		
			} else if (isOdd(res)){
				$('#pre').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q1' href='/italk2learn/exercise/preA'>"+ques+"</a>");
		    	$('#post').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q2' href='/italk2learn/exercise/postB'>"+ques2+"</a>");
			} else{
				var hs= hashCode(user);
				if (isEven(hs)){
					$('#pre').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q1' href='/italk2learn/exercise/preB'>"+ques+"</a>");
			    	$('#post').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q2' href='/italk2learn/exercise/postA'>"+ques2+"</a>");	
				} else if (isOdd(hs)){
					$('#pre').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q1' href='/italk2learn/exercise/preA'>"+ques+"</a>");
			    	$('#post').append("<a style='color: rgb(2, 117, 164); font-size: 15px;' id='q2' href='/italk2learn/exercise/postB'>"+ques2+"</a>");	
				}
			}
		}

		function isEven(n) {
		   return isNumber(n) && (n % 2 == 0);
		}

		function isOdd(n) {
		   return isNumber(n) && (Math.abs(n) % 2 == 1);
		}

		function isNumber(n) {
		   return n == parseFloat(n);
		}

		function hashCode(val){
			var hash = 0;
			if (val.length == 0) return hash;
			for (i = 0; i < val.length; i++) {
				char = val.charCodeAt(i);
				hash = ((hash<<5)-hash)+char;
				hash = hash & hash; // Convert to 32bit integer
			}
			return hash;
		}
	
		function changeLabels(lang){
			var q1 = "Pre-Questionnaire";
			var q2 = "Post-Questionnaire";
			if(lang == "es"){
				q1 = "Pre-Cuestionario";
			    q2 = "Post-Cuestionario";
			}
			else if(lang == "de"){
				q1 = "Pre-Fragebogen";
			    q2 = "Post-Fragebogen";
			}
			$("#q1").html(q1);
			$("#q2").html(q2);
		}