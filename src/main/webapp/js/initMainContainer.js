		var userName;
		var chTIS=false;
		var sEnabled=false;
		var aEnabled=true;
		setInterval(function(){checkTDSWrapper()},3000);

		window.onbeforeunload = function(){
			  $.ajax({
			        type: 'POST',
			        url: "speechRecognition/closeEngine",
			        success: function(data, textStatus, jqXHR){
			        	//window.location.href = "/italk2learn/login";
			        },
			        error : function(jqXHR, status, error) {
			        	window.location.href = "/italk2learn/login";
			        },
			        complete : function(jqXHR, status) {
			        }
			    });
			  return "Do you want to leave?"
			};

		//var timer=setInterval(safeexit(),5000);
		
		$(window).unload(function(){
			$.ajax({
		        type: 'GET',
		        url: "speechRecognition/closeEngine",
		        success: function(data, textStatus, jqXHR){
		        	
		        },
		        error : function(jqXHR, status, error) {
		        	window.location.href = "/italk2learn/login";
		        },
		        complete : function(jqXHR, status) {
		        }
		    });
		});

		function safeexit(){
			$.ajax({
		        type: 'GET',
		        url: "sequence/getUser",
		        success: function(data, textStatus, jqXHR){
		        	
		        },
		        error : function(jqXHR, status, error) {
		        	window.location.href = "/italk2learn/login";
		        },
		        complete : function(jqXHR, status) {
		        }
		    });
		} 

		function getParameterByName(name) {
		    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		        results = regex.exec(location.search);
		    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
		}

		function loadjscssfile(filename, filetype){
			 if (filetype=="js"){ //if filename is a external JavaScript file
			  var fileref=document.createElement('script')
			  fileref.setAttribute("type","text/javascript")
			  fileref.setAttribute("src", filename)
			 }
			 else if (filetype=="css"){ //if filename is an external CSS file
			  var fileref=document.createElement("link")
			  fileref.setAttribute("rel", "stylesheet")
			  fileref.setAttribute("type", "text/css")
			  fileref.setAttribute("href", filename)
			 }
			 if (typeof fileref!="undefined")
			  document.getElementsByTagName("head")[0].appendChild(fileref)
			}
		
		$(document).ready(function() {
			$("#next").hide();
			//$("#done").hide();
			$("#help").hide();
			$("#connectedON").hide();
			$("#connectedOFF").hide();
			soundButtonEnable(true);
			getCondition();
			//$("#initContainer").click(function() {
			$.ajax({
				type: 'GET',
				url: "sequence/getUser",
				success: function (data) {
					//JLF: Call connect WOZ. If it's connected or authfail initialises the container.
					$('#user').html(data);
					userName=data;
					connectWOZ (data);
				},
				error: function (jqXHR, status, error) {
					$(document).trigger('error');
				}
			});
			//});
			$("#next").click(function() {
				if (aEnabled)
					nextExercise();
			});	
			$("#submitEx").click(function() {
				submitExercise();
			});
			$("#sButton").click(function() {
				if (sEnabled==false){
					soundButtonEnable(true);
				} else{ 
					soundButtonEnable(false);
				}
			});
		});

		function initContainer(){
		    $.ajax({
		        type: 'GET',
		        url: "sequence/",
		        data: {
		            
		            },
		        success: function(data, textStatus, jqXHR) {
		        	startNewExercise();
		        	$("#next").show();
		        	document.getElementById("mainContainer").innerHTML=jqXHR.responseText;
		            var reponse = jQuery(jqXHR.responseText);
		            var reponseScript = reponse.filter("script");
		            jQuery.each(reponseScript, function(idx, val) { 
		            	loadjscssfile(val.src, "js");
				    } );
		        },
		        error : function(jqXHR, status, error) {
		           alert('Sorry!, there was a problem');
		        },
		        complete : function(jqXHR, status) {
		        }
		    });
		}

		function backExercise(){
			document.getElementById("mainContainer").innerHTML = '';
		    $.ajax({
		        type: 'GET',
		        url: "sequence/backexercise",
		        data: {
		            
		            },
		        success: function(data, textStatus, jqXHR){
		        	document.getElementById("mainContainer").innerHTML=jqXHR.responseText;
		            var reponse = jQuery(jqXHR.responseText);
		            var reponseScript = reponse.filter("script");
		            jQuery.each(reponseScript, function(idx, val) { 
		            	loadjscssfile(val.src, "js");
				    } );
		        },
		        error : function(jqXHR, status, error) {
		           alert('Sorry!, there was a problem');
		        },
		        complete : function(jqXHR, status) {
		        }
		    });
		}

		function nextExercise(){
			$('#exercisePrompt').html("");
			//$("#done").hide();
			$("#help").hide();
			$("#next").hide();
			document.getElementById("mainContainer").innerHTML = '';
		    $.ajax({
		        type: 'GET',
		        url: "sequence/nextexercise",
		        data: {
		            
		            },
		        success: function(data, textStatus, jqXHR){
		        	startNewExercise();
		        	$("#next").show();
		        	document.getElementById("mainContainer").innerHTML=jqXHR.responseText;
		            var reponse = jQuery(jqXHR.responseText);
		            var reponseScript = reponse.filter("script");
		            jQuery.each(reponseScript, function(idx, val) { 
		            	loadjscssfile(val.src, "js");
				    } );
		            safeexit();
		        },
		        error : function(jqXHR, status, error) {
		           alert('Sorry!, there was a problem');
		        },
		        complete : function(jqXHR, status) {
		        }
		    });
		    //JLF: Commented by the moment
//		    $.ajax({
//				type: 'POST',
//		        contentType : 'application/json; charset=utf-8',
//		        dataType : 'json',
//		        url: "speechRecognition/callPTD",
//		        success: function(data){
//		        	//alert('PTD successfully called');
//		        },
//		        error : function(jqXHR, status, error) {
//
//		        },
//		    });
		}
		
		function setFractionsLabinUse(val)
		{
			var evt = {
			       	 "flEnable": val
			        };
			$.ajax({
				type: 'POST',
		        contentType : 'application/json; charset=utf-8',
		        dataType : 'json',
		        url: "tis/setFractionsLabinUse",
		        data: JSON.stringify(evt),
		        success: function(data){
		        	//alert('sendMessageToTIS successfully called!');
		        },
		        error : function(jqXHR, status, error) {
		        	//window.location.href = "/italk2learn/login";
		        },
		    });
		}
		
		function checkTDSWrapper(){
			if (chTIS==true) {
				$.ajax({
					type: 'GET',
			        url: "tis/checkTISWrapper",
			        success: function(data){
			        	if (data.popUpWindow ==true) {
							if (data.message.length>0) {
								textToSpeech(data.message);
								SendHighMessage(data.message);
							}
						}
						else {
							if (data.message.length>0) {
								sendMessageToLightBulb(data.message);
							}
						}
			        },
			        error : function(jqXHR, status, error) {
			        	//window.location.href = "/italk2learn/login";
			        },
			    });
			}
			
		}
		
		function startNewExercise(){
			$.ajax({
				type: 'GET',
			    url: "tis/startNewExercise",
			    success: function(data){
			    },
			    error : function(jqXHR, status, error) {
			    	//window.location.href = "/italk2learn/login";
			    },
			});
		}
		
		function checkTIS(enable){
			chTIS=enable;
		}

		function submitExercise(){
			$('#exercisePrompt').html("");
			document.getElementById("mainContainer").innerHTML = '';
			$("#next").hide();
			//$("#done").hide();
			$("#help").hide();
			var sub = {
		       	 "idExercise": $('#exList').val(), 
		        };
		    $.ajax({
		        type: 'POST',
		        contentType : 'application/json; charset=utf-8',
		        url: "exercise/getSpecificExercise",
		        data: JSON.stringify(sub),
		        success: function(data, textStatus, jqXHR){
		        	document.getElementById("mainContainer").innerHTML=jqXHR.responseText;
		            var reponse = jQuery(jqXHR.responseText);
		            var reponseScript = reponse.filter("script");
		            jQuery.each(reponseScript, function(idx, val) { 
		            	loadjscssfile(val.src, "js");
				    } );
		        },
		        error : function(jqXHR, status, error) {
		           alert('Sorry!, there was a problem');
		        },
//		        complete : function(jqXHR, status) {
//		           alert('Done!');
//		        }
		    });
		}
		
		function getCondition(){
			$.ajax({
		        type: 'GET',
		        url: "sequence/getCondition",
		        data: {
		            
		            },
		        success: function(data, textStatus, jqXHR){
		        	$('#condition').html(data);
		        	if (data==2){
		        		soundButtonEnable(false);
		        		$("#sButton").hide();
		        		$("#speechComponent").hide();
		        	} else if (data==3){
		        		alert("no fractions lab exercise");
		        	}
		        },
		        error : function(jqXHR, status, error) {
		           alert('Sorry!, no condition retrieved, reloading webpage');
		           window.location.href = "/italk2learn/exercise";
		        },
		        complete : function(jqXHR, status) {
		        }
		    });
			
		}

		function textToSpeech(message) {
			var l_lang=getParameterByName("locale");
			if (l_lang=="" && navigator.userLanguage) // Explorer
			  l_lang = navigator.userLanguage;
			else if (l_lang=="" && navigator.language) // FF
			  l_lang = navigator.language;
			else if (l_lang=="")
			  l_lang = "en";
			if (l_lang=="de_DE")
				play_soundGerman(message);
			else	
				play_sound("http://translate.google.com/translate_tts?ie=UTF-8&q="+encodeURIComponent(message)+"&tl="+l_lang+"&total=1&idx=0prev=input");
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

		function html5_audio(){
    	    var a = document.createElement('audio');
    	    return !!(a.canPlayType && a.canPlayType('audio/mpeg;').replace(/no/, ''));
    	}
    	 
    	var play_html5_audio = false;
    	if(html5_audio()) 
        	play_html5_audio = true;
    	 
    	function play_sound(url){
    		if (sEnabled == true) {
	    		document.getElementById("player").innerHTML = '';
	    		//playS(url);
			    if(play_html5_audio){
			    	//playS(url);
			    	var sound = $("<embed id='sound' type='audio/mpeg'/>");
			    	sound.attr('src', url);
			    	sound.attr('loop', false);
			    	sound.attr('hidden', true);
			    	sound.attr('autostart', true);
			    	sound.attr('class', 'hiddenPlayer');
			    	$('#player').append(sound);
			    } else {
			        $("#sound").remove();
			        var sound = $("<embed id='sound' type='audio/mpeg' />");
			        sound.attr('src', url);
			        sound.attr('loop', false);
			        sound.attr('hidden', true);
			        sound.attr('autostart', true);
			        $('body').append(sound);
			    }
		    }
        }
    	
    	function play_soundGerman(url){
    		var audio = new Audio();
			audio.src = "wavFiles/"+url+".wav";
			audio.load();                                
			audio.play();
    	}
    	
    	
    	function soundButtonEnable(value){
			if (value==true || value=="true" || value=="True"){
				$("#sButton").removeClass("it2lSoundOffbutton");
				$("#sButton").addClass("it2lSoundOnbutton");
				sEnabled=true;
			}
			else {
				$("#sButton").removeClass("it2lSoundOnbutton");
				$("#sButton").addClass("it2lSoundOffbutton");
				sEnabled=false;
			}
		}

    	function playS(url){
			var speechProductionPlayer=document.getElementById("speechProductionPlayer");
    	    speechProductionPlayer.src=url;
        	speechProductionPlayer.load();
    		speechProductionPlayer.play();
		}
    	
		function playS2(url){
    	    sourceAudio.src=url;
        	speechProductionPlayer.load();
    		speechProductionPlayer.play();
		}
		
		function playS3(url){
			var audio = document.createElement("audio");
			var source = document.createElement("source");
			source.src = url;
			audio.appendChild(source);                                
			audio.play();
		}
		
		function playS4(url){
			var audio = new Audio();
			audio.src = url;
			audio.load();                                
			audio.play();
		}
		
        function playS5(url){
			var audio = document.createElement("audio");
			audio.src = url;
			audio.load();				
			audio.play();
		}