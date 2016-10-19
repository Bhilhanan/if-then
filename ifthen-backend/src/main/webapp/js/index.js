/**
 * Created by bjeyara on 10/17/16.
 */
var flag = false;

function init() {
    gapi.client.load('ifBeanApi', 'v1',getPopularPublicIfs, 'https://if-then.appspot.com/_ah/api/');
    gapi.client.load('thenBeanApi', 'v1', getPopularPublicThens, 'https://if-then.appspot.com/_ah/api/');

    document.getElementById('submit').onclick = function () {
        submit();
    }

    document.getElementById('random_if').onclick = function () {
        choose('random_if');
    }

    document.getElementById('random_then').onclick = function () {
        choose('random_then');
    }

}

function getPopularPublicIfs() {
    var request = {};
    request.limit = 3;
    gapi.client.ifBeanApi.list(request).execute(function (resp) {
        if (!resp.code) {
            var temp;
            var max = Math.min(3, resp.items.length);
            for (var i = 0; i < max; i++) {
                temp = document.getElementById("popular-" + i).innerHTML;
                document.getElementById("popular-" + i).innerHTML = "If " + resp.items[i].text + temp;
            }
        }
    });
}
function getPopularPublicThens() {
    var request={};
    request.limit=3;
    gapi.client.thenBeanApi.list(request).execute(function (resp) {
        if(!resp.code){
            var temp;
            var max = Math.min(3, resp.items.length);
            for (var i = 0; i < max; i++) {
                temp = document.getElementById("popular-" + i).innerHTML;
                document.getElementById("popular-" + i).innerHTML = temp+" then " + resp.items[i].text;
            }
        }
    });
}
function choose(id) {
    if (flag === false) {
        flag = true;
        var choosen = document.getElementById("choosen_ifthen");
        var text = document.getElementById(id).innerHTML;
        choosen.innerHTML = "<p>" + text + "</p>";
        choosen.parentNode.parentNode.parentNode.style.display = "block";
    }
}

function getRandomIf(sessionKey, thenText) {
    var request = {};
    request.sessionId = sessionKey;
    gapi.client.ifBeanApi.random(request).execute(function (resp) {
        if (!resp.code) {
            document.getElementById('random_if').innerHTML = "If " + resp.text + " then " + thenText;
            document.getElementById("loader").style.display = "none";
            document.getElementById("randomResult").style.display = "block";
        }
    });
}

function getRandomThen(sessionKey, ifText) {
    var request = {};
    request.sessionId = sessionKey;
    gapi.client.thenBeanApi.random(request).execute(function (resp) {
        if (!resp.code) {
            document.getElementById('random_then').innerHTML = "If " + ifText + " Then " + resp.text;
            document.getElementById("loader").style.display = "none";
            document.getElementById("randomResult").style.display = "block";
        }
    });
}

function submit() {
    var sessionKey = document.getElementById('sessionKey').value;
    if (!sessionKey) {
        return;
    }
    var ifText = document.getElementById('ifText').value;
    if (!ifText) {
        return;
    }
    var thenText = document.getElementById('thenText').value;
    if (!thenText) {
        return
    }
    var isPublic = document.getElementById("isPublic").checked;
    document.getElementById("loader").style.display = "block";
    document.getElementById("randomResult").style.display = "none";

    var ifData = {};
    ifData.sessionId = sessionKey;
    ifData.text = ifText;
    ifData.public = isPublic;

    var thenData = {};
    thenData.sessionId = sessionKey;
    thenData.text = thenText;
    thenData.pubic = isPublic;

    gapi.client.ifBeanApi.insert(ifData).execute(function (resp) {
        if (!resp.code) {
            getRandomIf(sessionKey, thenText);
        }
    });

    gapi.client.thenBeanApi.insert(thenData).execute(function (resp) {
        if (!resp.code) {
            getRandomThen(sessionKey, ifText);
        }
    });
}