	function changeText(text, subtext, newSubText){
		var theText = text;
		if(text.indexOf(subtext)>-1){
			theText = text.replace(subtext,newSubText);
		}
		return theText;
	}
	
	function prepareTranslationAlpacaForm() {
		var alpacaMessages = $("div.alpaca-message");
			for(var i=0; i < alpacaMessages.length; i++){
				var jMess = $(alpacaMessages[i]);
				var textMess = jMess.html();
				if(lang == "es"){
					textMess = changeText(textMess,"This field is not optional","Este campo no es opcional");
					textMess = changeText(textMess,"This field should have one of the values in 1, 2, 3, 4.  Current value is: null","Este campo todavía no ha sido contestado")
				}else if(lang == "de"){
					textMess = changeText(textMess,"This field is not optional","Dieses Feld ist nicht optional");
					textMess = changeText(textMess,"This field should have one of the values in 1, 2, 3, 4.  Current value is: null","Dieses Feld ist noch nicht beantwortet worden");
				}else if(lang == "en"){
					textMess = changeText(textMess,"This field should have one of the values in 1, 2, 3, 4.  Current value is: null","This field has not been answered yet!");
				}
				jMess.html(textMess);
			}
	}
	
	//this is only a quick fix to support the problems of alpaca to retrieve data from checkbox!!!!!
	function fixCheckBoxResponse(json){
		allDivs = $("div.alpaca-control");
		var checkParent = "";
		var arrOp = [];
		for(var i=0; i < allDivs.length; i++){
    		var ele = allDivs[i];
    		console.log($(ele));
    		var sub = ele.getElementsByTagName("label");
    		var jsub = $(sub);
    		var check = jsub.find("input:checkbox");
    		if(check.prop('checked')!=undefined){
    			if(check.prop('checked')){
    				checkParent = check.parent().parent().parent().parent().attr('data-alpaca-container-item-name');
    				arrOp.push(check.attr("data-checkbox-value"));
    			}
    		}
    		
    	}
    	if(arrOp!=[] && arrOp.length>0){
    		var arrStr = " { ";
    		$.map(json, function(obj, index) { 
    			arrStr += '"'+index+'":'+'"'+obj+'",';
    		});
    		if(arrStr!=[] && arrStr.length>0)
    			arrStr += '"'+checkParent+'":['+arrOp.toString()+']';
			arrStr += "} ";
			return JSON.parse(arrStr);
		}else
			return json;
	}
	
	function countQuestions(){
		var cont = 0;
		$.map(options.fields, function(obj, index) { 
			cont++;
    	});
    	return cont;
	}
	

    function postRenderCallback(control) {
    	changeRadioLabelPerImage();
    	changeStructureAlpaca();
    	//fixSpaceBetweenResponses("question1");
    	//fixSpaceBetweenResponses("question2");
    	//fixSpaceBetweenResponses("question3");
    	
    	$('#form1-button').click(function() {
			var val = control.getValue(); //objeto json
			if(titles!=[] && titles.length>0)
				val=fixCheckBoxResponse(val);
			cont = 0;
			$.map(val, function(obj, index) { 
    			cont++;
    		});
    		if(cont==countQuestions()){
				var jsonVal = JSON.stringify(val); //string json
				//var alpacaMessages = $("div.alpaca-message");
				var evt = {
						"data" : JSON.stringify(jsonVal),
						"typeQuiz" : typeQ
					};
					$.ajax({
						type : 'POST',
						contentType : 'application/json; charset=utf-8',
						dataType : 'json',
						url : "/italk2learn/sequence/storeExerciseQuiz",
						data : JSON.stringify(evt),
						success : function(data) {
							alert("Answers submitted correctly");
							setTimeout(function(){window.location.href = "/italk2learn/exercise/main"},2000);
						},
						error : function(jqXHR, status, error) {
							alert("Answers submitted correctly");
							setTimeout(function(){window.location.href = "/italk2learn/exercise/main"},2000);
						},
					});

				return true;
				
			}else{
				var retVal = confirm("You have not answered some questions. Do you want to leave them empty?");
   				if( retVal == true ){
   					var jsonVal = JSON.stringify(val); //string json
   					//var alpacaMessages = $("div.alpaca-message");
   					var evt = {
   							"data" : JSON.stringify(jsonVal),
   							"typeQuiz" : typeQ
   						};
   						$.ajax({
   							type : 'POST',
   							contentType : 'application/json; charset=utf-8',
   							dataType : 'json',
   							url : "/italk2learn/sequence/storeExerciseQuiz",
   							data : JSON.stringify(evt),
   							success : function(data) {
   								alert("Answers submitted correctly");
   								setTimeout(function(){window.location.href = "/italk2learn/exercise/main"},2000);
   								return true;
   							},
   							error : function(jqXHR, status, error) {
   								alert("Answers submitted correctly");
   								setTimeout(function(){window.location.href = "/italk2learn/exercise/main"},2000);
   								return true;
   							},
   						});
   				}else{
   					return false;
   				}
			}
		});
    }
    
    function getAllElementsWithAttribute(attribute, criteria){
  		var matchingElements = [];
  		var allElements = document.getElementsByTagName('*');
  		for (var i = 0, n = allElements.length; i < n; i++){
    		if (allElements[i].getAttribute(attribute) == criteria){
      			// Element exists with attribute. Add to array.
      			matchingElements.push($(allElements[i]));
    		}
  		}
  		return matchingElements;
	}
    
    function changeStructureAlpaca(){
    	$.map(schema.properties, function(obj, index){
    		$.map(obj, function(obj2, index2){
    			if(index2 == "titleImaSrc"){
    				if(obj2!=undefined && obj2!=null && obj2!=""){
    					prepareLabelPerImage(index, obj2);
    				}
    			}/*else if(index2 == "needsToBeTable"){
    				if(obj2!=undefined && obj2!=null && obj2== true){
    					var singleRow = false;
    					if(obj2.labelInSingleRow!=undefined && obj2.labelInSingleRow!=null){
    						singleRow = obj.labelInSingleRow;
    					}
    					prepareDivTransformTable(index, singleRow);
    				}
    			}*/
    		});
    	});
    }
    
    function fixSpaceBetweenResponses(criteria){
    	var titlesDiv = getAllElementsWithAttribute("data-alpaca-container-item-name", criteria);
    	for (var i = 0; i < titlesDiv.length; i++){
    		var jdiv = titlesDiv[i];
    		var eles = jdiv.find(".alpaca-control").find("label");
    		for (var j = 0; j < eles.length; j++){
    			console.log($(eles[j]));
    			$(eles[j]).css({ "margin-right": "40px;" });
    		}
  		}
  	}
    
    /*
    function prepareDivTransformTable(criteria, singleRow){
    	var titlesDiv = getAllElementsWithAttribute("data-alpaca-container-item-name", criteria);
    	var elemens = [];
    	for (var i = 0; i < titlesDiv.length; i++){
    		var jdiv = titlesDiv[i];
    		var eles = jdiv.find(".alpaca-control").find("label");
    		for (var j = 0; j < eles.length; j++){
    			var ele = $(eles[j]).html();
    			var arrEle = ele.replace(new RegExp("\n", 'g'), "").split(">");
    			elemens.push({ html : arrEle[0]+">", val: arrEle[1], div: criteria });
    		}
  		}
  		var labelPregunta = [];
  		for (var i = 0; i < titlesDiv.length; i++){
  			var jD = titlesDiv[i];
  			var jdd = jD.find("label");
  			for (var j = 0; j < jdd.length; j++){
  				if($(jdd[j]).hasClass("alpaca-control-label")){
  					labelPregunta.push({html: $(jdd).html(), forLa: $(jdd).attr("for")});
  				}
  			}
  		}
  		
  		for (var i = 0; i < titlesDiv.length; i++){
  			var jD = titlesDiv[i];
  			var str = '<label class=" alpaca-control-label" for="'+labelPregunta[0].forLa+'">'+labelPregunta[0].html+'</label>';
  			for (var i = 0; i < elemens.length; i++){
  				str += '<div class="radio alpaca-control" style="display: inline-block;">';
  				str +='<label>';
  				str += elemens[i].html+elemens[i].val;
  				str += '</label>';
  				str+= "<p>hola</p>"
  				str += '</div>';
  			}
  			str += '<div class="alpaca-message alpaca-message-notOptional"> This field is not optional.</div>';
  			str += '<div class="alpaca-message alpaca-message-invalidValueOfEnum">This field should have one of the values in 1, 2, 3, 4, 5.  Current value is: null';
  			str += '</div></div>';
  			//jD.find(".alpaca-field").html(str);
  		}
  		
  		for (var i = 0; i < elemens.length; i++){
    		//console.log(elemens[i].html+"   "+elemens[i].val+"    "+elemens[i].div);
  		}
    }*/
    
    function prepareLabelPerImage(criteria, src){
    	var titlesDiv = getAllElementsWithAttribute("data-alpaca-container-item-name", criteria);
    	for (var i = 0; i < titlesDiv.length; i++){
    		var jdiv = titlesDiv[i];
    		var eles = jdiv.find("label.alpaca-control-label");
    		for (var j = 0; j < eles.length; j++){
    			var ele = $(eles[j]);
    			ele.append(" <img src='"+src+"' width='60' height='60'/>");
    		}
  		}	
    }
    
    function changeRadioLabelPerImage(){//**************************************************
    	
    	var allDivs = $("div.alpaca-container-item");
		for(var i=0; i < allDivs.length; i++){
    		var ele = allDivs[i];
    		if($(ele).find("input:checkbox").length > 0)
    			titles.push($(ele).attr("data-alpaca-container-item-name"));
    	}
    
    	var allDivs = $("div.alpaca-control");
    	for(var i=0; i < allDivs.length; i++){
    		var ele = allDivs[i];
    		var sub = ele.getElementsByTagName("label");
    		var jsub = $(sub);
    		var rad = jsub.find("input:radio,input:checkbox");
    		var textJsub = jsub.html();
    		var title = textJsub.substring(textJsub.indexOf(">")+1, textJsub.length);
    		if(title.indexOf("png")>0){
    			textJsub = textJsub.substring(0, textJsub.indexOf(">")+1);
    			textJsub += "<img title='"+rad.val()+"' src='"+title+"' width='130' height='110' hspace='10'>"
    			//console.log(textJsub);
    			jsub.html(textJsub);
    		}
    	}
    }