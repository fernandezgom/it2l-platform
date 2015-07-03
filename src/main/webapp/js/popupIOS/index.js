$(document).ready(function() {
   	//iniciar el controlador y llamar a la funcion loadPage
   	principalCtl = new IndexController();
	principalCtl.loadPage();
});

function IndexController() {
	//variables
	var scope =  this;
	this.lang =  ""; // variable del idioma
	
	
	this.faceRadio = false; ///**********************
	
	this.dataLang = null;
	
	this.loadPage =  function (){
		scope.lang = getLocale();//obtener el idioma de la url, si es 'en' es ingles, otro caso (sp o null) es español
		
		
		
		scope.faceRadio = getURLParameter2("faceRadio");///**********************
		if(scope.faceRadio == null || scope.faceRadio=="false")///**********************
			scope.faceRadio = false;///**********************
			
			
		
		var theLangScript = "js/popupIOS/labels_en.js";// etiquetas en español
		if (scope.lang.indexOf("es")>-1) {
			theLangScript = "js/popupIOS/labels_sp.js";// etiquetas en ingles
		} else if (scope.lang.indexOf("de")>-1) {
			theLangScript = "js/popupIOS/labels_de.js";// etiquetas en aleman,
			// supongo
		}
		loadScript(theLangScript, scope.prepareLabels); // cargar script de
		// idioma
		$("#sendJson").click(function() {
			var json = scope.buildJSONResponse();
			if (json != null && json != undefined) {
				scope.cleanForm();
				$('.modal').removeClass('active');
				// JLF: Call the HTTP service
				var evt = {
					"data" : JSON.stringify(json),
					"typeQuiz" : 2
				};
				$.ajax({
					type : 'POST',
					contentType : 'application/json; charset=utf-8',
					dataType : 'json',
					url : "sequence/storeExerciseQuiz",
					data : JSON.stringify(evt),
					success : function(data) {
						nextExercise();
					},
					error : function(jqXHR, status, error) {
						nextExercise();
					},
				});
			}
		});
		$("#closeBton").click(function() {
			isPopupOpened=false;
        	managePopup(false);
        	var maincontainer = document.getElementById('mainContainer');
            maincontainer.style.visibility = "visible";
			scope.cleanForm();
			$('.modal').removeClass('active');
		});
	};
	
	this.checkIfNumberExistInArr = function(arr, numAns){
		if(arr!=null && arr.length >0){
			for(var i=0; i< arr.length ;i++){
				if(arr[i]==numAns)
					return true;
			}
		}
		return false;
	};
	
	/*******************************/
	
	this.getAnswersDontNeedsStaticPosition = function(idQ){
		var arr = [];
		$.map(scope.dataLang, function(obj, index) {
			if(index!="title" && index!="responseDialog"){
				$.map(obj.answers, function(obj2, index2) {
					if(idQ==index){
						if(obj2.random!=undefined && obj2.random == true){
							var jsonV = { "index": index2, "obj": obj2 }
							arr.push(jsonV);
						}
					}
				});
			}
		});
		return arr;
	}
	
	this.getAnswersNeedsStaticPosition = function(idQ){
		var arr = [];
		$.map(scope.dataLang, function(obj, index) {
			if(index!="title" && index!="responseDialog"){
				$.map(obj.answers, function(obj2, index2) {
					if(idQ==index){
						
						if(obj2.random==undefined || obj2.random == false){
							var jsonV = { "index": index2, "obj": obj2 }
							arr.push(jsonV);
						}
					}
				});
			}
		});
		return arr;
	}
	
	this.getJSONObjectPerArr = function(arr, requireIndex){
		if(arr != null && arr!=[]){
			for(var i=0; i< arr.length;i++){
				var item = arr[i];
				if(arr[i].index == requireIndex)
					return arr[i].obj;
			}
		}
		return null;
	};
	
	this.checkStaticCollision = function(arrStatic, index){
		for(var i=0; i< arrStatic.length ;i++){
			var it = arrStatic[i];
			if(it.index==index)
				return true;
		}
		return false;
	};
	
	this.checkIndexCollision = function(arrStatic, index){
		for(var i=0; i< arrStatic.length ;i++){
			var it = arrStatic[i];
			if(it==index)
				return true;
		}
		return false;
	};
	
	this.randomize2 = function(min, max, arrDinamic, arrStatic, arrIndexUsed){
		var index= Math.floor(Math.random() * (max - min + 1)) + min;
		while(true){
			if(scope.checkStaticCollision(arrDinamic,index) && !scope.checkStaticCollision(arrStatic,index) && !scope.checkIndexCollision(arrIndexUsed,index)){
				return index;
			}
			index= Math.floor(Math.random() * (max - min + 1)) + min;
		}
	};
	
	/*******************************/
	
	this.permutateComponents = function(arr, numAns, objJSON, index){
		if(!scope.checkIfNumberExistInArr(arr,numAns)){
			if(scope.faceRadio){
				$("#questionsDialog").append("<div class='item'><img title='"+objJSON.label+"' id='r"+index+numAns+"' src='"+objJSON.srcIma+"' width='45' height='45' hspace='10' class='imOpt'><span class='textImg'>"+objJSON.label+"</span></div>");
				$("#r"+index+numAns).click(function() {
					scope.facePressed(index, numAns);
				});
			}
			else{
				$("#questionsDialog").append("<div class='item'><input type='radio' value='"+objJSON.label+"' title='"+objJSON.label+"' id='ra"+index+numAns+"'><span class='textImg'>"+objJSON.label+"</span></div>");
				$("#ra"+index+numAns).click(function() {
					scope.radioPressed(index, numAns);
				});
			}
			arr.push(numAns);
		}
	};
	
	this.radioPressed = function(index1, index2){
		var numberQues = scope.countAnswersPerQuestion(index1);
		for(var i=1; i<= numberQues; i++){
			scope.removeSelect(index1, i);
		}
		$("#ra"+index1+index2).attr('checked',true);
	};
	
	this.randomize = function(min, max){
		return Math.floor(Math.random() * (max - min + 1)) + min;
	};
	
	this.makePop = function(){
		$("#questionsDialog").html("");
		$.map(scope.dataLang, function(obj, index) {
			if(index=="title"){
				$("#titlePop").html(obj);
			}
			else{
				if(index!="responseDialog"){
					$("#questionsDialog").append("<label id='laQu"+index+"'>"+ obj.labelQues+"</label><br><br>");
					var numAns = scope.countAnswersPerQuestion(index);
					var arrResStatic = scope.getAnswersNeedsStaticPosition(index);
					var arrResDina = scope.getAnswersDontNeedsStaticPosition(index);
						
					var arrQues = [];
					
					var arrIndexUsed = [];
					if(arrResStatic != [] && arrResStatic.length > 0){
						for(var i=1; i <= numAns ; i++){
							var indGene = i;
							if(scope.getJSONObjectPerArr(arrResStatic,i)!=null){
								var objArr = scope.getJSONObjectPerArr(arrResStatic,i);
								scope.permutateComponents(arrQues, i, objArr, index);
								
							}else{
								var ranNu = scope.randomize2(1,numAns,arrResDina, arrResStatic, arrIndexUsed);
								var objArr = scope.getJSONObjectPerArr(arrResDina,ranNu);
								scope.permutateComponents(arrQues, ranNu, objArr, index);
								indGene = ranNu;
							}
							arrIndexUsed.push(indGene);
						}
						$("#questionsDialog").append("<hr>");
					}
					else{
						var b=true;
						while(b){
							var ranNu = scope.randomize(1,numAns);
							scope.permutateComponents(arrQues, ranNu, obj.answers[ranNu], index);
							if(arrQues.length==numAns)
								b=false;
						}
						$("#questionsDialog").append("<hr>");
					}
					
					/*
					var b=true;
					while(b){
						var ranNu = scope.randomize(1,numAns);
						scope.permutateComponents(arrQues, ranNu, obj.answers[ranNu], index);
						if(arrQues.length==numAns)
							b=false;
					}
					$("#questionsDialog").append("<hr>");
					
					/*$.map(obj.answers, function(obj2, index2) {
						$("#questionsDialog").append("<div class='item'><img title='"+obj2.label+"' id='r"+index+index2+"' src='"+obj2.srcIma+"' width='45' height='45' hspace='10' class='imOpt'><span class='textImg'>"+obj2.label+"</span></div>");
						console.log(index2);
						$("#r"+index+index2).click(function() {
							scope.facePressed(index, index2);
						});
					});*/
					$("#questionsDialog").append("<br><br>");
				}
			}
		});
	};
	
	/*******************************************************************/
	//funcion que se pasa como parametro a la funcion general loadScript
	//obtiene las etiquetas en un idioma y se ponen a cada componente de la pagina
	this.prepareLabels = function(data, textStatus, jqxhr){
		scope.dataLang = labels;
	};
	
	this.buildJSONResponse = function(){
		var json = null;
		var arr = [];
		$.map(scope.dataLang, function(obj, index) {
			if(index!="title" && index!="responseDialog"){
				var ind = scope.returnSelectedAns(index);
				if(ind!=null && ind!="" && ind!=undefined){
					var res = $(ind).attr("title");
					arr.push(res);
				}
			}
		});
		
		if(arr.length!=scope.countAnswers()){
			var msg = scope.dataLang.responseDialog;
			arr = null;
			alert(msg);
		}
		
		if(arr!=null){
			var jsonStr = "{ ";
			for(var i=0; i<arr.length;i++){
				jsonStr += '"'+(i+1)+'": { "labelQuestion":"'+ scope.dataLang[i+1].labelQues +'", "response":"'+arr[i]+'"},';
			}
			jsonStr = jsonStr.substring(0, jsonStr.length-1);
			jsonStr+=" }";
			json = JSON.parse(jsonStr);
		}
		return json;
	}
	
	this.cleanForm = function(){
		$.map(scope.dataLang, function(obj, index) {
			if(index!="title" && index!="responseDialog"){
				$.map(obj.answers, function(obj2, index2) {
					if(scope.faceRadio)
						$("#r"+index+index2).removeClass('selected');
					else
						$("#ra"+index+index2).attr('checked',false);
				});
			}
		});
	}
	
	this.countAnswers = function(){
		var cont = 0;
		$.map(scope.dataLang, function(obj, index) {
			if(index!="title" && index!="responseDialog"){
				cont++;
			}
		});
		return cont;
	}
	
	this.countAnswersPerQuestion = function(idQ){
		var cont = 0;
		$.map(scope.dataLang, function(obj, index) {
			if(index!="title" && index!="responseDialog"){
				$.map(obj.answers, function(obj2, index2) {
					if(idQ==index){
						cont++;
					}
				});
			}
		});
		return cont;
	}
	
	this.returnSelectedAns = function(id1){
		var res= "";
		var numberQues = scope.countAnswersPerQuestion(id1);
		for(var i=1;i<= numberQues;i++){
			if(scope.askSelect(id1,i)){
				if(scope.faceRadio)
					res = "#r"+id1+i;
				else
					res = "#ra"+id1+i;
				break;
			}
		}
		return res;
	};
	
	this.askSelect = function(id1, id2){
		if(scope.faceRadio){
			return $("#r"+id1+id2).hasClass('selected');
		}else{
			return $("#ra"+id1+id2).attr('checked');
		}
	}
	
	this.removeSelect = function(id1, id2){
		if(scope.askSelect(id1,id2)){
			if(scope.faceRadio){
				$("#r"+id1+id2).removeClass('selected');
			}else{
				$("#ra"+id1+id2).attr('checked',false);
			}
		}
	}
	
	this.facePressed = function(index1, index2){
		var numberQues = scope.countAnswersPerQuestion(index1);
		for(var i=1; i<= numberQues; i++){
			scope.removeSelect(index1, i);
		}
		$("#r"+index1+index2).addClass('selected');
	};
}