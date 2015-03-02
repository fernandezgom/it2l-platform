				var lowMessage="";
				var isEnabledLightBulb=true;
				$("#help").show();
				helpButtonEnable(false);
				$("#next").click(function() {
					arrowButtonPressed();
				});
				$("#help").click(function() {
					helpButtonPressed();
				});
				var config = {
					width: 800,
					height: 600,
					params: { enableDebugging:"0" }

				};
				config.params["disableContextMenu"] = true;
				var u = new UnityObject2(config);

				jQuery(function() {

					var $missingScreen = jQuery("#unityPlayer").find(".missing");
					var $brokenScreen = jQuery("#unityPlayer").find(".broken");
					$missingScreen.hide();
					$brokenScreen.hide();

	                textToSpeech($('#task').text().substring(0,110));
	                
	                $('#exercisePrompt').html($('#taskContainer').html());
	                $('#taskContainer').remove();

					u.observeProgress(function (progress) {
						switch(progress.pluginStatus) {
							case "broken":
								$brokenScreen.find("a").click(function (e) {
									e.stopPropagation();
									e.preventDefault();
									u.installPlugin();
									return false;
								});
								$brokenScreen.show();
							break;
							case "missing":
								$missingScreen.find("a").click(function (e) {
									e.stopPropagation();
									e.preventDefault();
									u.installPlugin();
									return false;
								});
								$missingScreen.show();
							break;
							case "installed":
								$missingScreen.remove();
							break;
							case "first":
							break;
						}
					});
					var body=$('#task').text();
					if (body.localeCompare("Make a fraction that equals 3/4 and has 12 as denominator.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+getLocale()+"&username="+userName+"&tip=http://it2l.dcs.bbk.ac.uk/italk2learn/tip/EQUIValence1.tip"+"&idtask=EQUIValence1"+userName);
					}
					else if (body.localeCompare("Make a fraction that equals 1/2 and has 4 as denominator.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+getLocale()+"&username="+userName+"&tip=http://it2l.dcs.bbk.ac.uk/italk2learn/tip/EQUIValence2.tip"+"&idtask=EQUIValence2"+userName);
					}
					else if (body.localeCompare("Use the same representations to show whether 1/3 is bigger or smaller than 1/5.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+getLocale()+"&username="+userName+"&tip=http://it2l.dcs.bbk.ac.uk/italk2learn/tip/task11setA.tip"+"&idtask=task1.1setA"+userName);
					}
					else if (body.localeCompare("Make a fraction using each of the representations.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+getLocale()+"&username="+userName+"&tip=http://it2l.dcs.bbk.ac.uk/italk2learn/tip/task21tip"+"&idtask=task2.1"+userName);
					}
					else if (body.localeCompare("Make an interesting fraction (not 1/2!),")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+getLocale()+"&username="+userName+"&tip=http://it2l.dcs.bbk.ac.uk/italk2learn/tip/task22.tip"+"&idtask=task2.2"+userName);
					}
					else if (body.localeCompare("Make a fraction that is equivalent to 1/2, using liquid measures. Check they are equivalent.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+getLocale()+"&username="+userName+"&tip=http://it2l.dcs.bbk.ac.uk/italk2learn/tip/task24setAliqu.tip"+"&idtask=task2.4.setA.liqu"+userName);
					}
					else if (body.localeCompare("Michel says '3/4 = 1/12 because 3 times 4 equals 12'. Do you agree or disagree with Michel?.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+getLocale()+"&username="+userName+"&tip=http://it2l.dcs.bbk.ac.uk/italk2learn/tip/task26setA.tip"+"&idtask=task2.6.setA"+userName);
					}
					else if (body.localeCompare("Make a fraction that equals 1/6 and has 18 as denominator.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+getLocale()+"&username="+userName+"&tip=http://it2l.dcs.bbk.ac.uk/italk2learn/tip/task27setA.tip"+"&idtask=task2.7.setA"+userName);
					}
					else if (body.localeCompare("Show how you could make 3/5 by adding two fractions.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+getLocale()+"&username="+userName+"&tip=http://it2l.dcs.bbk.ac.uk/italk2learn/tip/task3aPlus1setAarea.tip"+"&idtask=task3aPlus.1.setA.area"+userName);
					}
					else {
						arrowButtonEnable(true);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+getLocale()+"&username="+userName+"&tip=http://it2l.dcs.bbk.ac.uk/italk2learn/tip/Default.tip");
					}
				});
				
				
				function getFLTaskID() {
					$.ajax({
						type: 'GET',
						url: "sequence/getFLTask",
						success: function (data) {
							u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+getLocale()+"&idtask="+data);
						},
						error: function (jqXHR, status, error) {
						}
					});
				}

				function initFractionsLab(data)
				{
					$.ajax({
						type: 'GET',
				        contentType : 'application/json; charset=utf-8',
				        dataType : 'json',
				        url: "setNewStudentInfo",
				        success: function(data, textStatus, jqXHR){
				        	//doSomething(data.Language,data.StundentInfo,data.TaskInfo)
				        },
				        error : function(jqXHR, status, error) {
				           alert('Sorry!, there was a problem');
				        },
				        complete : function(jqXHR, status) {
				        }
				    });
				}
				
				function setFractionsLabinUse(data)
				{
					var evt = {
					       	 "flEnable": data.toLowerCase()
					        };
					$.ajax({
						type: 'POST',
				        contentType : 'application/json; charset=utf-8',
				        dataType : 'json',
				        url: "tis/setFractionsLabinUse",
				        data: JSON.stringify(evt),
				        success: function(data){
				        	alert('sendMessageToTIS successfully called!');
				        },
				        error : function(jqXHR, status, error) {
				        	//window.location.href = "/italk2learn/login";
				        },
				    });
				}
				
				function saveEvent(event){
					//alert(event);
					var evt = {
					       	 "event": event 
					        };
					$.ajax({
						type: 'POST',
				        contentType : 'application/json; charset=utf-8',
				        dataType : 'json',
				        url: "sequence/saveFLEvent",
				        data: JSON.stringify(evt),
				        success: function(data){
				        	//alert('Change submitted!');
				        },
				        error : function(jqXHR, status, error) {
				        	window.location.href = "/italk2learn/login";
				        },
				    });
				}
				
				function sendMessageToTIS(feedbackText, currentFeedbackType, previousFeedbackType, followed){
					var evt = {
					       	 "feedbackText": feedbackText,
					       	 "currentFeedbackType": currentFeedbackType,
					       	 "previousFeedbackType": previousFeedbackType,
					       	 "followed": followed.toLowerCase()
					        };
					$.ajax({
						type: 'POST',
				        contentType : 'application/json; charset=utf-8',
				        dataType : 'json',
				        url: "tis/callTIS",
				        data: JSON.stringify(evt),
				        success: function(data){
				        	alert('sendMessageToTIS successfully called!');
				        },
				        error : function(jqXHR, status, error) {
				        	//window.location.href = "/italk2learn/login";
				        },
				    });
					
				}
				
				function sendMessageToLightBulb(message){
					helpButtonEnable(true);
					lowMessage=message;
				}
				
				
				function SendHighMessage(message)
				{
					var json = "{\"method\": \"HighFeedback\", \"parameters\": {\"message\": \"" + message +"\"}}";
					u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
				}
				
				function SendLowMessage(message)
				{
					var json = "{\"method\": \"LowFeedback\", \"parameters\": {\"message\": \"" + message +"\"}}";
					u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
				}

				function EnableHelpButton(message)
				{
					if (message.charAt(0)==='x'){
						helpButtonEnable(false);						
					}
					else {
						helpButtonEnable(true);
						lowMessage=message;
					}
				}
				
				function SendMessageToSupport(message)
				{
					var json = "{\"method\": \"SendMessageToSupport\", \"parameters\": {\"message\": \"" + message +"\"}}";
					u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
				}
				
				function playSound(message)
				{
					textToSpeech(message);
				}
				
				
				function arrowButtonEnable(value){
					if (value==true || value=="true" || value=="True") {
						$("#next").removeClass("it2lNextbuttonOFF");
						$("#next").addClass("it2lNextbuttonON");
						aEnabled=true;
					}	
					else {
						$("#next").removeClass("it2lNextbuttonON");
						$("#next").addClass("it2lNextbuttonOFF");
						aEnabled=false;
					}	
				}
				
				function helpButtonEnable(value){
					if (value==true || value=="true" || value=="True"){
						$("#help").removeClass("it2lHelpbuttonOFF");
						$("#help").addClass("it2lHelpbuttonON");
						isEnabledLightBulb=true;
					}
					else {
						$("#help").removeClass("it2lHelpbuttonON");
						$("#help").addClass("it2lHelpbuttonOFF");
						isEnabledLightBulb=false;
					}
				}
				
				
				function SetNewStudentInfo(data)
				{
					$.ajax({
						type: 'POST',
				        contentType : 'application/json; charset=utf-8',
				        dataType : 'json',
				        url: "setNewStudentInfo",
				        data: JSON.stringify(sub),
				        success: function(data, textStatus, jqXHR){
				        	//doSomething()
				        },
				        error : function(jqXHR, status, error) {
				           alert('Sorry!, there was a problem');
				        },
				        complete : function(jqXHR, status) {
				        }
				    });
				}
				
				function isHelpButtonEnable(){
					return isEnabledLightBulb;
				}
				
				function arrowButtonPressed(){
                    var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*doneButtonPressed*\"}}";
                    u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
				}
				
				function helpButtonPressed(){
					if (isEnabledLightBulb==true){
						textToSpeech(lowMessage);
						SendHighMessage(lowMessage);
						//lowMessage="";
						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*lightBulbPressedON*\"}}";
	                    u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
	                    helpButtonEnable(false);
					}
					else {
						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*lightBulbPressedOFF*\"}}";
	                    u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
					}
				}
				
				function enableTIS(enable){
					if (enable==true){
						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*switchTISOFF*\"}}";
	                    u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
					}
					else {
						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*switchTISON*\"}}";
	                    u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
					}
				}
				
