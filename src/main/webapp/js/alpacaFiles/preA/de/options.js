var scale = ["<br/>Stimme &uuml;ber-<br/>haupt nicht zu",             "<br/>&nbsp;&nbsp;&nbsp;Stimme&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>&nbsp;&nbsp;&nbsp;nicht zu&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
             "<br/>Weder noch&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/><br/>", 
             "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Stimme zu&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/><br/>", 
             "<br/>Stimme voll<br/>und ganz zu"];

var options = {
    "fields": {
    	"question1": {
        	"label": "1. Ich benutze häufig einen Computer.",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": scale,
        	"vertical": false
    	},
    	"question2": {
        	"label": "2. Ich lerne häufig am Computer Mathe. ",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": scale,
        	"vertical": false
    	},
    	"question3": {
        	"label": "3. Mit dem Computer zu lernen macht Spaß.",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": scale,
        	"vertical": false
    	},
    	"question4": {
        	"label": "4. Ich mag Mathematik.",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": scale,
        	"vertical": false
    	},
    	"question5": {
        	"label": "5. Ich mag Brüche.",
        	"type": "radio",
        	"helper": "----------------------------------------------------------------------------------------------------------------",
        	"optionLabels": scale,
        	"vertical": false
    	},
    	"question6" : {
            "label": "6. Welcher Zahlenstrahl zeigt 3/4?",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": ["../images/alpacaImages/questionary1/q1/1.png", "../images/alpacaImages/questionary1/q1/2.png", "../images/alpacaImages/questionary1/q1/3.png", "../images/alpacaImages/questionary1/q1/4.png"],
        	"vertical": false
        },
        "question7": {
        	"label": "7. Wähle ALLE Darstellungen aus, die 1/3 zeigen. Du kannst mehr als eine Darstellung auswählen!",
        	"type": "checkbox",
        	"helper": "",
        	"optionLabels": ["../images/alpacaImages/questionary1/q2/1.png", "../images/alpacaImages/questionary1/q2/2.png", "../images/alpacaImages/questionary1/q2/3.png", "../images/alpacaImages/questionary1/q2/4.png"]
    	},
    	"question8": {
        	"label": "8. Welcher Bruch passt nicht zu den anderen?",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": ["../images/alpacaImages/questionary1/q3/1.png", "../images/alpacaImages/questionary1/q3/2.png", "../images/alpacaImages/questionary1/q3/3.png", "../images/alpacaImages/questionary1/q3/4.png"],
        	"vertical": false
    	},
    	"question9": {
        	"label": "9. Welcher Bruch passt nicht zu den anderen?",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": ["../images/alpacaImages/questionary1/q4/1.png", "../images/alpacaImages/questionary1/q4/2.png", "../images/alpacaImages/questionary1/q4/3.png", "../images/alpacaImages/questionary1/q4/4.png", "../images/alpacaImages/questionary1/q4/5.png"],
        	"vertical": false
    	},
    	"question10": {
        	"label": "10. Was gehört in den Kasten?",
        	"type": "select",
        	"helper": "",
        	"optionLabels": ["Bitte auswählen","4","8","9","12"]
    	},
    	"question11": {
        	"label": "11. Welcher Bruch ist wertgleich mit 5/6 und hat 18 als Nenner?",
        	"type": "radio",
        	"helper": "",
        	"optionLabels": ["../images/alpacaImages/questionary1/q6/1.png", "../images/alpacaImages/questionary1/q6/2.png", "../images/alpacaImages/questionary1/q6/3.png", "../images/alpacaImages/questionary1/q6/4.png", "../images/alpacaImages/questionary1/q6/5.png"],
        	"vertical": false
    	}
    }
};