var schema = {
    "type": "object",
    "properties": {
    	"question1": {
            "required": true,
        	"enum": ["1", "2", "3", "4", "5"]
        },
        "question2": {
            "required": true,
        	"enum": ["1", "2", "3", "4", "5"]
        },
        "question3": {
            "required": true,
        	"enum": ["1", "2", "3", "4", "5"],
        	"needsToBeTable" : true,
        },
        "question4": {
            "required": true,
        	"enum": ["1", "2", "3", "4", "5"]
        },
        "question5": {
            "required": true,
        	"enum": ["1", "2", "3", "4", "5"]
        },
    	"question6": {
            "required": true,
        	"enum": ["1", "2", "3", "4"]
        },
        "question7": {
        	"required": true,
        	"enum": ["1", "2", "3", "4"]
    	},
    	"question8": {
        	"required": true,
        	"enum": ["1", "2", "3", "4"]
    	},
    	"question9": {
        	"required": true,
        	"enum": ["1", "2", "3","4"]
    	},
    	"question10": {
        	"required": true,
        	"enum": ["0", "1", "2", "3","4"],
        	"titleImaSrc" : "../images/alpacaImages/imagesB/q5/2.png"
    	},
    	"question11": {
        	"required": true,
        	"enum": ["1", "2", "3", "4"]
    	}
    }
};
