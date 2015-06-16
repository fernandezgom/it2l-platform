		$(document).ready(function() { 
			$.ajax({
				type: 'GET',
				url: "/italk2learn/sequence/getUser",
				success: function (data) {
					$('#user').html(data);
					generateRamdomQuiz(data);
					changeLabels(getURLParameter("lang"));
				},
				error: function (jqXHR, status, error) {
					$(document).trigger('error');
				}
			});
		});

		function generateRamdomQuiz(user) {
			var str = user; 
		    var res = str.slice(7, str.length);
		    document.getElementById("wrapper_light_gray").innerHTML = '';
		    var text="<a id='q1' href='/italk2learn/exercise/";
		    if (isEven(res)){
		    	text=text.concat("preB'>Cuestionario1</a><br/><a id='q3' href='/italk2learn/exercise'>iTalk2Learn</a><br/><a id='q2' href='/italk2learn/exercise/postA'>Cuestionario2</a><br/>");
			} else if (isOdd(res)){
				text=text.concat("preA'>Cuestionario1</a><br/><a id='q3' href='/italk2learn/exercise'>iTalk2Learn</a><br/><a id='q2' href='/italk2learn/exercise/postB'>Cuestionario2</a><br/>");
			} else{
				var hs= hashCode(user);
				if (isEven(hs)){
			    	text=text.concat("preB'>Cuestionario1</a><br/><a id='q3' href='/italk2learn/exercise'>iTalk2Learn</a><br/><a id='q2' href='/italk2learn/exercise/postA'>Cuestionario2</a><br/>");
				} else if (isOdd(hs)){
					text=text.concat("preA'>Cuestionario1</a><br/><a id='q3' href='/italk2learn/exercise'>iTalk2Learn</a><br/><a id='q2' href='/italk2learn/exercise/postB'>Cuestionario2</a><br/>");
				}
			}
		    $('#wrapper_light_gray').append(text);
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