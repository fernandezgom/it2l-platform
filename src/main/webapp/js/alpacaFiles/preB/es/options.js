var scale = ["<br/><spam class='eti'>Strongly<br/> disagree</spam>&nbsp;&nbsp;&nbsp;", "<br/>&nbsp;&nbsp;Disagree&nbsp;&nbsp;<br/><br/>", "<br/>&nbsp;&nbsp;Neither&nbsp;&nbsp;<br/><br/>", "<br/>&nbsp;&nbsp;&nbsp;Agree&nbsp;&nbsp;&nbsp;<br/><br/>", "<br/>&nbsp;&nbsp;&nbsp;<spam class='eti'>Strongly<br/> agree</spam>"];
var options = {
    "fields": {
    	"question1": {
        	"label": "1. I often use a computer.",
        	"type": "radio",
        	"helper": "&nbsp;",
        	"optionLabels": scale,
        	"vertical": false
        	
    	},
    	"question2": {
        	"label": "2. I often learn maths with a computer.",
        	"type": "radio",
        	"helper": "&nbsp;",
        	"optionLabels": scale,
        	"vertical": false
    	},
    	"question3": {
        	"label": "3. Learning with a computer is fun",
        	"type": "radio",
        	"helper": "&nbsp;",
        	"optionLabels": scale,
        	"vertical": false
    	},
    	"question4": {
        	"label": "4. I like maths.",
        	"type": "radio",
        	"helper": "&nbsp;",
        	"optionLabels": scale,
        	"vertical": false
    	},
    	"question5": {
        	"label": "5. I like fractions.",
        	"type": "radio",
        	"helper": "----------------------------------------------------------------------------------------------------------------",
        	"optionLabels": scale,
        	"vertical": false
    	},
    	"question6" : {
            "label": "6. Which number line correctly shows ½?",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": ["../images/alpacaImages/imagesB/q1/1.png", "../images/alpacaImages/imagesB/q1/2.png", "../images/alpacaImages/imagesB/q1/3.png", "../images/alpacaImages/imagesB/q1/4.png"],
        	"vertical": false
        },
        "question7": {
        	"label": "7. Select the representations that show ⅘. There are more than one.",
        	"type": "checkbox",
        	"helper": "",
        	"optionLabels": ["../images/alpacaImages/imagesB/q2/1.png", "../images/alpacaImages/imagesB/q2/2.png", "../images/alpacaImages/imagesB/q2/3.png", "../images/alpacaImages/imagesB/q2/4.png"]
    	},
    	"question8": {
        	"label": "8. Which fraction is the odd one out?",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": ["../images/alpacaImages/imagesB/q3/1.png", "../images/alpacaImages/imagesB/q3/2.png", "../images/alpacaImages/imagesB/q3/3.png", "../images/alpacaImages/imagesB/q3/4.png"],
        	"vertical": false
    	},
    	"question9": {
        	"label": "9. Which fraction is the odd one out?",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": ["../images/alpacaImages/imagesB/q4/1.png", "../images/alpacaImages/imagesB/q4/2.png", "../images/alpacaImages/imagesB/q4/3.png", "../images/alpacaImages/imagesB/q4/4.png", "../images/alpacaImages/imagesB/q4/5.png"],
        	"vertical": false
    	},
    	"question10": {
        	"label": "10. What goes in the box?",
        	"type": "select",
        	"helper": "",
        	"optionLabels": ["Choose one","5","10","12","20"]
    	},
    	"question11": {
        	"label": "11. Which of these is equivalent to ¾ and has 12 as the denominator?",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": ["../images/alpacaImages/imagesB/q6/1.png", "../images/alpacaImages/imagesB/q6/2.png", "../images/alpacaImages/imagesB/q6/3.png", "../images/alpacaImages/imagesB/q6/5.png"],
        	"vertical": false
    }
  }
};
