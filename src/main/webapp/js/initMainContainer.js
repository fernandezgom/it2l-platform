		var iloadedjs=0;
		var loadedjs=[];
		var userName;
		var chTIS=false;
		var sEnabled=false;
		var aEnabled=true;
		var helpadded=false;
		var isPopupOpened=false;
		var nLocale = getUrlVars()["locale"];
		if (nLocale ==null || nLocale =='' ){
			nLocale=getLocale();
		} 
		setInterval(function(){checkTDSWrapper()},3000);
		history.pushState(null, null, 'exercise');
		window.addEventListener('popstate', function(event) {
		  history.pushState(null, null, 'exercise');
		});

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
			
		function removejscssfile(filename, filetype){
			var targetelement=(filetype=="js")? "script" : (filetype=="css")? "link" : "none" //determine element type to create nodelist from
			var targetattr=(filetype=="js")? "src" : (filetype=="css")? "href" : "none" //determine corresponding attribute to test for
			var allsuspects=document.getElementsByTagName(targetelement)
			for (var i=allsuspects.length; i>=0; i--){ //search backwards within nodelist for matching elements to remove
			if (allsuspects[i] && allsuspects[i].getAttribute(targetattr)!=null && allsuspects[i].getAttribute(targetattr).indexOf(filename)!=-1)
				allsuspects[i].parentNode.removeChild(allsuspects[i]) //remove element by calling parentNode.removeChild()
			}
		}
		
		function alreadyInDOM(filename, filetype){
			var val=false;
			var targetelement=(filetype=="js")? "script" : (filetype=="css")? "link" : "none" //determine element type to create nodelist from
			var targetattr=(filetype=="js")? "src" : (filetype=="css")? "href" : "none" //determine corresponding attribute to test for
			var allsuspects=document.getElementsByTagName(targetelement)
			for (var i=allsuspects.length; i>=0; i--){ //search backwards within nodelist for matching elements to remove
			if (allsuspects[i] && allsuspects[i].getAttribute(targetattr)!=null && allsuspects[i].getAttribute(targetattr).indexOf(filename)!=-1)
				val=true; //remove element by calling parentNode.removeChild()
			}
			return val;
		}
		
		function replacejscssfile(oldfilename, newfilename, filetype){
			var targetelement=(filetype=="js")? "script" : (filetype=="css")? "link" : "none" //determine element type to create nodelist using
			var targetattr=(filetype=="js")? "src" : (filetype=="css")? "href" : "none" //determine corresponding attribute to test for
			var allsuspects=document.getElementsByTagName(targetelement)
			for (var i=allsuspects.length; i>=0; i--){ //search backwards within nodelist for matching elements to remove
				if (allsuspects[i] && allsuspects[i].getAttribute(targetattr)!=null && allsuspects[i].getAttribute(targetattr).indexOf(oldfilename)!=-1){
					var newelement=createjscssfile(newfilename, filetype)
					allsuspects[i].parentNode.replaceChild(newelement, allsuspects[i])
				}
			}
		}
		
		$(document).ready(function() {
			$("#next").hide();
			//$("#done").hide();
			$("#help").hide();
			$("#connectedON").hide();
			$("#connectedOFF").hide();
			$("#recording").hide();
			soundButtonEnable(true);
			getCondition();
			setInEngland();
			setLanguageBrowser();
			//$("#initContainer").click(function() {
			$.ajax({
				type: 'GET',
				url: "sequence/getUser",
				success: function (data) {
					//JLF: Call connect WOZ. If it's connected or authfail initialises the container.
					$('#user').html(data);
					userName=data;
					initContainer();
					connectWOZ (data);
				},
				error: function (jqXHR, status, error) {
					$(document).trigger('error');
				}
			});
			//});
			$("#next").click(function() {
				if (aEnabled) {
					isPopupOpened=true;
	            	managePopup(true);
	            	var maincontainer = document.getElementById('mainContainer');
	                maincontainer.style.visibility = "hidden";
					principalCtl.makePop();
					$('.modal').toggleClass('active');
				}
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
					iloadedjs=0;
					loadedjs=[];
		            jQuery.each(reponseScript, function(idx, val) {
						loadedjs[iloadedjs]=val.src;
						iloadedjs++;						
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
			isPopupOpened=false;
        	managePopup(false);
        	var maincontainer = document.getElementById('mainContainer');
            maincontainer.style.visibility = "visible";
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
					removejscssfile("initFracLab.js", "js");
					removejscssfile("initWhizz.js", "js");
					removejscssfile("initWhizzTest.js", "js");
					removejscssfile("initCTAT.js", "js");
					$("#flscript").remove();
					for (var i=0;i<iloadedjs;i++){
						removejscssfile(loadedjs[i], "js")
					}
					iloadedjs=0;
					loadedjs=[];
		            jQuery.each(reponseScript, function(idx, val) {
						if (val.src.indexOf("initFracLab.js")>-1){
							helpadded=true;
						}
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
		
		function setInEngland(){
			var val=true;
			var len=nLocale;
			if (len.indexOf("de") > -1)
				val=false;
			var evt = {
			       	 "english": val
			        };
			$.ajax({
				type: 'POST',
		        contentType : 'application/json; charset=utf-8',
		        dataType : 'json',
		        url: "sna/setInEngland",
		        data: JSON.stringify(evt),
		        success: function(data){

		        },
		        error : function(jqXHR, status, error) {
		        	//window.location.href = "/italk2learn/login";
		        },
		    });			
		}
		
		function setLanguageBrowser(){
			var evt = {
			       	 "idLanguage": nLocale
			        };
			$.ajax({
				type: 'POST',
		        contentType : 'application/json; charset=utf-8',
		        dataType : 'json',
		        url: "sequence/setLanguageBrowser",
		        data: JSON.stringify(evt),
		        success: function(data){

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
			        	if (isPopupOpened==false) {
				        	if (data.fromTDS == true) {
					        	if (data.popUpWindow ==true) {
									if (data.message.length>0) {
										textToSpeech(data.message, true);
										SendHighMessage(data.message);
									}
								}
								else {
									if (data.message.length>0) {
										sendMessageToLightBulb(data.message);
									}
								}
				        	} else {
				        		if (data.message.length>0) {
				        			textToSpeech(data.message, false);
				        			Alert.render(data.message);
				        		}
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
			$.ajax({
				type: 'GET',
		        url: "tis/initialiseTISWrapper?enable="+chTIS,
		        success: function(data){
		        },
		        error : function(jqXHR, status, error) {
		        	//window.location.href = "/italk2learn/login";
		        },
		    });
		}
		
		function managePopup(enable){
			$.ajax({
				type: 'GET',
		        url: "tis/managePopup?enable="+enable,
		        success: function(data){
		        },
		        error : function(jqXHR, status, error) {
		        	//window.location.href = "/italk2learn/login";
		        },
		    });
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
		        success: function(data, textStatus, jqXHR){
		        	$('#condition').html(" "+data);
		        	if (data==2){
		        		soundButtonEnable(false);
		        		$("#sButton").hide();
		        		$("#speechComponent").hide();
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

		function textToSpeech(message, voice) {
			var l_lang=getParameterByName("locale");
			if (l_lang=="" && navigator.userLanguage) // Explorer
			  l_lang = navigator.userLanguage;
			else if (l_lang=="" && navigator.language) // FF
			  l_lang = navigator.language;
			else if (l_lang=="")
			  l_lang = "en";
			reproduceAudioMaryTTS(message, voice);
				//play_sound("http://translate.google.com/translate_tts?ie=UTF-8&q="+encodeURIComponent(message)+"&tl="+l_lang+"&total=1&idx=0prev=input");
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
    	
    	function play_sound_marytts_cache(message){
    		if (sEnabled == true) {
				var data = {
				       	 "message": message
				        };
				$.ajax({
					type: 'POST',
			        contentType : 'application/json; charset=utf-8',
			        dataType : 'json',
			        url: "/italk2learnsp/speechProduction/getHash",
			        data: JSON.stringify(data),
			        success: function(data){
			        	$.ajax({
			    		    url:"wavFiles/"+ data+".wav",
			    		    type:'HEAD',
			    		    error: function()
			    		    {
			    		    	reproduceAudio(message);
			    		    },
			    		    success: function()
			    		    {
			    		    	var audio = new Audio();
								audio.src = "wavFiles/"+data+".wav";
								audio.load();                                
								audio.play();
			    		    }
			    		});
			        },
			        error : function(jqXHR, status, error) {
			        	//window.location.href = "/italk2learn/login";
			        },
			    });
			}
    	}
    	
    	function reproduceAudioMaryTTS(message, voice){
    		if (sEnabled == true) {
				var mes = {
				       	 "language": nLocale,
				       	 "message": message,
				       	 "voiceType" : voice
				        };
				$.ajax({
					type: 'POST',
			        contentType : 'application/json; charset=utf-8',
			        dataType : 'json',
			        url: "/italk2learnsp/speechProduction/generateAudioFile",
			        data: JSON.stringify(mes),
			        success: function(data){
			        	if (data.length>0){
							var audio = new Audio();
							audio.src = "wavFiles/"+data;
							audio.load();                                
							audio.play();
							
						}
			        },
			        error : function(jqXHR, status, error) {
						if (jqXHR.responseText.length>0){
							var audio = new Audio();
							audio.src = "wavFiles/"+jqXHR.responseText;
							audio.load();                                
							audio.play();
						}
			        },
			    });
			}
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
    	
    	function getUrlVars()
    	{
    	    var vars = [], hash;
    	    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    	    for(var i = 0; i < hashes.length; i++)
    	    {
    	        hash = hashes[i].split('=');
    	        vars.push(hash[0]);
    	        vars[hash[0]] = hash[1];
    	    }
    	    return vars;
    	}
    	
    	function ShowMessage(){
			
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
        
        function CustomAlert(){
            this.render = function(dialog){
            	isPopupOpened=true;
            	managePopup(true);
                var winW = window.innerWidth;
                var winH = window.innerHeight;
                var dialogoverlay = document.getElementById('dialogoverlay');
                var dialogbox = document.getElementById('dialogbox');
                dialogoverlay.style.display = "block";
                dialogoverlay.style.height = winH+"px";
                dialogbox.style.left = (winW/2) - (700 * .5)+"px";
                dialogbox.style.top = "35%";
                dialogbox.style.display = "block";
                document.getElementById('dialogboxhead').innerHTML = '<div id="dialogboxbody"> <table id="tableAlign"><tr><td><span id="verticalSpanLeft"><img th:src="@{/resources/images/frobot.png}" src="/italk2learn/images/frobot.png"></img></span></td><td> <span id="verticalSpanRight">' + dialog + '</span></td></tr></table></div><div style="margin-top: 7px; margin-bottom:2px; display:flex; "> <button style="margin-left:auto; margin-right:auto; " class="it2lbutton" onclick="Alert.ok()">OK</button></div>';
             
            }
            this.ok = function(){
                document.getElementById('dialogbox').style.display = "none";
                document.getElementById('dialogoverlay').style.display = "none";
                isPopupOpened=false;
                managePopup(false);
                var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*closeFeedbackPopup*\"}}";
                u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
            }
            window.onresize = function()
            {
                var winW = window.innerWidth;
                var winH = window.innerHeight;
                var dialogoverlay = document.getElementById('dialogoverlay');
                var dialogbox = document.getElementById('dialogbox');
                dialogoverlay.style.display = "none";
                dialogoverlay.style.height = winH+"px";
                dialogbox.style.left = (winW/2) - (550 * .5)+"px";
                dialogbox.style.top = "35%";
                dialogbox.style.display = "none";

            }
        }
    var Alert = new CustomAlert();