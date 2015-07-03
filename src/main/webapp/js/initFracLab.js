				var maincontainer = document.getElementById('mainContainer');
				maincontainer.style.width = "815px";
				var idTask;
				var lowMessage="";
				var isEnabledLightBulb=true;
				$("#help").show();
				helpButtonEnable(false);
				if (helpadded==false){
					$("#next").click(function() {
						arrowButtonPressed();
					});
				}
				if (helpadded==false){
					$("#help").unbind("click").click(function() {
						helpButtonPressed();
					});
				}
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

	                textToSpeech($('#task').text(), true);
	                
	                if ($('#idTask')){
	                	idTask=$('#idTask').html();
	                	$('#idTask').remove();
	                }
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
					setFractionsLabinUse(true);
					if (idTask!=null && idTask.length>0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/"+idTask+".tip"+"&idtask="+idTask+userName);
						sendLanguageEvent();
					}
					else if (body.localeCompare("Show how you could make this fraction by adding two fractions. Show 3/5 using the rectangles.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/task3aPlus1setAarea.tip"+"&idtask=task3aPlus.1.setA.area"+userName);
						sendLanguageEvent();
					}
					else if (body.localeCompare("Make a fraction that equals 3/4 and has 12 as denominator.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/EQUIValence1.tip"+"&idtask=EQUIValence1"+userName);
					}
					else if (body.localeCompare("Make a fraction that equals 1/2 and has 4 as denominator.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/EQUIValence2.tip"+"&idtask=EQUIValence2"+userName);
					}
					else if (body.localeCompare("Use the same representations to show whether 1/3 is bigger or smaller than 1/5.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/task11setA.tip"+"&idtask=task1.1setA"+userName);
					}
					else if (body.localeCompare("Make a fraction using each of the representations.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/task21.tip"+"&idtask=task2.1"+userName);
					}
					else if (body.localeCompare("Make a fraction and right click it. Select 'Find equivalent' and partition the fraction into 2, 3, 4 and 5.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/task22.tip"+"&idtask=task2.2"+userName);
						sendLanguageEvent();
					}
					else if (body.localeCompare("Machen Sie eine Fraktion und klicken Sie rechts. Wählen Sie Suche gleichwertig und partitioniert die Fraktion in 2, 3, 4 und 5.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/task22.tip"+"&idtask=task2.2"+userName);
						sendLanguageEvent();
					}
					else if (body.localeCompare("Make a fraction that is equivalent to 1/2, using liquid measures. Check they are equivalent.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/task24setAliqu.tip"+"&idtask=task2.4.setA.liqu"+userName);
					}
					else if (body.localeCompare("Michel says '3/4 = 1/12 because 3 times 4 equals 12'. Do you agree or disagree with Michel?.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/task26setA.tip"+"&idtask=task2.6.setA"+userName);
					}
					else if (body.localeCompare("Make a fraction that equals 1/6 and has 18 as denominator.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/task27setA.tip"+"&idtask=task2.7.setA"+userName);
					}
					else if (body.localeCompare("Show how you could make 3/5 by adding two fractions.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/task3aPlus1setAarea.tip"+"&idtask=task3aPlus.1.setA.area"+userName);
					}
					else if (body.localeCompare("Make a fraction that is equivalent to 3/4, using liquid measures. Check they are equivalent.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/task3aPlus1setAarea.tip"+"&idtask=task2.4.setB.liqu"+userName);
					}
					else if (body.localeCompare("Make a fraction that is equivalent to 1/2, using area. Check they are equivalent.")==0){
						arrowButtonEnable(false);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/task3aPlus1setAarea.tip"+"&idtask=task2.4.setA.area"+userName);
					}
					else {
						arrowButtonEnable(true);
						u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale+"&username="+userName+"&tip=/italk2learn/tip/Default.tip");
					}
				});
				
				
				function Flstarted() {
					if (chTIS==true){
						sendEventEnabledTIStoTDS(true);
					} else {
						sendEventEnabledTIStoTDS(false);
					}
				}
				
				function getFLTaskID() {
					$.ajax({
						type: 'GET',
						url: "sequence/getFLTask",
						success: function (data) {
							u.initPlugin(jQuery("#unityPlayer")[0], "/italk2learn/sequence/FractionsLab.unity3d?showStartPage=false&language="+nLocale()+"&idtask="+data);
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
				
				
				function saveEvent(event){
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
				        	//window.location.href = "/italk2learn/login";
				        },
				    });
				}
				
				function sendMessageToTIS(feedbackText, currentFeedbackType, feedbackID, level, followed, viewed){
					var evt = {
					       	 "feedbackText": feedbackText,
					       	 "currentFeedbackType": currentFeedbackType,
					       	 "feedbackID": feedbackID,
					       	 "level": level,
					       	 "followed": followed.toLowerCase(),
					       	 "viewed": viewed.toLowerCase()
					        };
					$.ajax({
						type: 'POST',
				        contentType : 'application/json; charset=utf-8',
				        dataType : 'json',
				        url: "tis/callTIS",
				        data: JSON.stringify(evt),
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
				
				function sendDoneButtonPressedToTIS(value){
					if (value==true || value=="true" || value=="True"){
						isPopupOpened=false;
						managePopup(false);
					}
					else {
						isPopupOpened=true;
						managePopup(true);
					}
					var evt = {
					       	 "donePressed": value.toLowerCase()
					        };
					$.ajax({
						type: 'POST',
				        contentType : 'application/json; charset=utf-8',
				        dataType : 'json',
				        url: "tis/sendDoneButtonPressed",
				        data: JSON.stringify(evt),
				        success: function(data){
				        },
				        error : function(jqXHR, status, error) {
				        },
				    });
					
				}
				
				function sendRepresentationTypeToSNA(value) {
					
					var evt = {
					       	 "representationType": value
					        };
					$.ajax({
						type: 'POST',
				        contentType : 'application/json; charset=utf-8',
				        dataType : 'json',
				        url: "sna/sendRepresentationTypeToSNA",
				        data: JSON.stringify(evt),
				        success: function(data){

				        },
				        error : function(jqXHR, status, error) {
				        	//window.location.href = "/italk2learn/login";
				        },
				    });
					
				}
				
				function sendFeedbackTypeToSNA(feedbackType){
					var evt = {
					       	 "feedbackType": feedbackType
					        };
					$.ajax({
						type: 'POST',
				        contentType : 'application/json; charset=utf-8',
				        dataType : 'json',
				        url: "sna/sendFeedbackTypeToSNA",
				        data: JSON.stringify(evt),
				        success: function(data){

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
					isPopupOpened=true;
					managePopup(true);
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
					textToSpeech(message,true);
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
					if (aEnabled==false) {
						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*doneButtonPressed*\"}}";
						u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
                    }
				}
				
				function helpButtonPressed(){
					if (isEnabledLightBulb==true){
						textToSpeech(lowMessage,true);
						SendHighMessage(lowMessage);
						//lowMessage="";
						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*lightBulbPressedON*\"}}";
	                    u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
	                    helpButtonEnable(false);
					}
//					else {
//						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*lightBulbPressedOFF*\"}}";
//	                    u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
//					}
				}
				
				function enableTIS(enable){
					checkTIS(enable);
					sendEventEnabledTIStoTDS(enable);
				}
				
				function sendEventEnabledTIStoTDS(enable){
					if (enable==false){
						$("#recording").hide();
						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*switchTISOFF*\"}}";
	                    u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
					}
					else {
						$("#recording").show();
						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*switchTISON*\"}}";
	                    u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
					}
				}
				
				function sendLanguageEvent(){
					var len=nLocale;
					if (len.indexOf("en") > -1) {
						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*switchEnglishON*\"}}";
	                    u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
					} else if (len.indexOf("de") > -1){
						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*switchGermanON*\"}}";
	                    u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);						
					} else if (len.indexOf("es") > -1) {
						var json = "{\"method\": \"PlatformEvent\", \"parameters\": {\"eventName\": \"*switchSpanishON*\"}}";
                    	u.getUnity().SendMessage("ExternalInterface", "SendEvent", json);
                    }
				}
				
