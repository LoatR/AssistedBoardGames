/*** Global Vars ***/
var id_game;

function createNewBoard() {
    createNewGameReq().then(function(res){
        id_game = res;
        getGameReq(res).then(function(resp){
            displayBoard(resp);
        });
    });

}

function getSuggestedMoves() {
	document.getElementById("suggestions").innerHTML = servletGetHelp();
}

function newMove() {
    var from = document.getElementById("from").value;
    var to = document.getElementById("to").value;

    addMoveReqBIS("1", from, to).then(function(res){ //TODO replace 1 par id_game
        console.log("New move, new board res : ",res);
        displayBoard(res);
    });
}

function loadGame() {
	idGameToLoad = document.getElementById("idGame").value;
    getGameReq(idGameToLoad).then(function(res){
        displayBoard(res);
    });
}

function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds){
            break;
        }
    }
}

function displayBoard(board){
	board = board.replace(/\n/g, "");
	var pieces = board.split('');
	var position;
	for(var i=0; i<64; i++){
		position = getPosition(i);
		var htmlCode = getHtmlCode(pieces[i]);
		if(htmlCode == "0"){
			continue;
		}else if (htmlCode == "-1"){
			break;
		}else{
			document.getElementById(position).innerHTML = htmlCode ;
		}
	}
}

function getPosition(n){
	var number = 8 - Math.trunc(n/8);
	var rest = n%8;
	var letter;
	switch(rest){
	case 0:
		letter = "a";
		break;
	case 1:
		letter = "b";
		break;
	case 2:
		letter = "c";
		break;
	case 3:
		letter = "d";
		break;
	case 4:
		letter = "e";
		break;
	case 5:
		letter = "f";
		break;
	case 6:
		letter = "g";
		break;
	case 7:
		letter = "h";
		break;
	default:
		letter = "";
	}

	return letter + number.toString();
}

function getHtmlCode(piece){
	switch(piece){
	case 'r':
		return "&#9814;";
	case 'R':
		return "&#9820;";
	case 'n':
		return "&#9816;";
	case 'N':
		return "&#9822;";
	case 'b':
		return "&#9815;";
	case 'B':
		return "&#9821;";
	case 'q':
		return "&#9813;";
	case 'Q':
		return "&#9819;";
	case 'k':
		return "&#9812;";
	case 'K':
		return "&#9818;";
	case 'p':
		return "&#9817;";
	case 'P':
		return "&#9823;";
	case '':
		return "";
	case 'S':
		return "-1";
	default:
		return "0";
	}
}

/********* REQUEST PART ***********/
function createNewGameReq () {
      return fetch("http://localhost:8080/mychessgame/v1/game", {
        method: "GET",
        mode: "cors",
        cache: "no-cache",
        headers: {
            "Content-Type": "text/plain",
        },
        redirect: "follow",
        referrer: "no-referrer",
        //body: JSON.stringify(data), // body data type must match "Content-Type" header
    })
        .then(response => response.text())
        .then(function(response){
            return response.toString();
        }).catch(function (error){
            console.log("An error occurend : ", error);
        });
}

function getGameReq(idGame){
    return fetch("http://localhost:8080/mychessgame/v1/game/getGame?game="+idGame, {
        method: "GET",
        mode: "cors",
        cache: "no-cache",
        headers: {
            "Content-Type": "text/plain",
        },
        redirect: "follow",
        referrer: "no-referrer",
    })
        .then(response => response.text())
        .then(function(response){
            console.log("getGame Req : ", response);
            return response.toString();
        })
        .catch(function (error){
            console.log("An error occurend : ", error);
        });
}

function getHelpReq(idGame){
    return fetch("http://localhost:8080/mychessgame/v1/game/getGame?game="+idGame, {
        method: "GET",
        mode: "no-cors",
        cache: "no-cache",
        headers: {
            "Content-Type": "text/plain",
        },
        redirect: "follow",
        referrer: "no-referrer",
    })
        .then(response => response.text())
        .then(function(response){
            return response.toString();
        }).catch(function (error){
            console.log("An error occurend : ", error);
        });
}

function addMoveReq(idGame, move){
    return fetch("http://localhost:8080/mychessgame/v1/game/move?game="+idGame, {
        method: "POST",
        mode: "no-cors",
        cache: "no-cache",
        headers: {
            "Content-Type": "text/plain",
        },
        redirect: "follow",
        referrer: "no-referrer",
        body:move,
    })
        .then(response => response.text())
        .then(function(response){
            sleep(10000);
            return response.toString();
        }).catch(function (error){
            console.log("An error occurend : ", error);
        });
}

function addMoveReqBIS(idGame,from,to){
    return fetch("http://localhost:8080/mychessgame/v1/game/addMove?game="+idGame+"&from="+from+"&to="+to, {
        method: "POST",
        mode: "no-cors",
        cache: "no-cache",
        headers: {
            "Content-Type": "text/plain",
        },
        redirect: "follow",
        referrer: "no-referrer",
        })
        .then(response => response.text())
        .then(function(response){
            return response.toString();
        }).catch(function (error){
            console.log("An error occurend : ", error);
        });
}

