/**
 * Created by bjeyara on 10/14/16.
 */

function popular_init() {
    gapi.client.load('ifBeanApi', 'v1', popular_init_then, 'https://if-then.appspot.com/_ah/api/');

}

function popular_init_then(){
    gapi.client.load('thenBeanApi', 'v1', prepareDivs, 'https://if-then.appspot.com/_ah/api/');
}

var limit=20;
var ifNextPageToken=null;
var thenNextPageToken=null;
var runningIndex=0;
var hotIfIndex=0;
var hotThenIndex=0;
var empty=false;

function getMore(){
    document.getElementById("loadMoreBtn").innerHTML="Loading ....";
    prepareDivs();
}

function disableGetMore(){
    document.getElementById("loadMoreBtn").removeAttribute("onclick");
    for(var i=hotIfIndex;i<runningIndex;i++){
        var element=document.getElementById("hot_"+i);
        element.parentElement.removeChild(element);
    }
}

function prepareDivs(){
    var hotdiv=document.getElementById("hot_if_thens");
    for(var i=0;i<limit;i++){
        hotdiv.innerHTML+="<div class='card card-block card-outline-primary card-custom' id='hot_"+runningIndex+"'></div>"
        runningIndex+=1;
    }
    populateIfs();
    populateThens();
}

function removeExtraDivs(start){
    for(var i=runningIndex-limit+start;i<runningIndex;i++){
        var element=document.getElementById("hot_"+i);
        element.parentElement.removeChild(element);
    }
    runningIndex=runningIndex-limit+start;
}

function populateIfs() {
    var request = {};
    request.limit=limit;
    request.cursor=ifNextPageToken;
    gapi.client.ifBeanApi.list(request).execute(function (resp) {
        if (!resp.code) {
        ifNextPageToken=resp.nextPageToken;
            resp.items = resp.items || [];

            if(resp.items.length===0){
                document.getElementById("loadMoreBtn").innerHTML="No more cards to load";
                disableGetMore();
            }else{
                document.getElementById("loadMoreBtn").innerHTML="Load More";
            }

            var result = "";
            var item = {};
            for (var i = 0; i < resp.items.length; i++,hotIfIndex++) {
                item = resp.items[i];
//                if (document.getElementById("hot_" + runningIfIndex)!=null) {
                    var temp = document.getElementById("hot_" + hotIfIndex).innerHTML;
                    document.getElementById("hot_" + hotIfIndex).innerHTML = "If " + item.text + temp;
//                }
//                else {
//                    result =
//                        "<div class='card card-block card-outline-primary card-custom' id='hot_" + runningIfIndex + "'>" +
//                        "If " + item.text +
//                        "</div>";
//                    document.getElementById("hot_if_thens").innerHTML+=result;
//                }
//                runningIfIndex+=1;
            }
        }
    });
}

function populateThens() {
    var request={};
    request.limit=limit;
    request.cursor=thenNextPageToken;
    gapi.client.thenBeanApi.list(request).execute(function (resp) {
        if (!resp.code) {
            thenNextPageToken=resp.nextPageToken
            resp.items = resp.items || [];

            if(resp.items.length===0){
                document.getElementById("loadMoreBtn").innerHTML="No more cards to load";
                disableGetMore();
            }else{
                document.getElementById("loadMoreBtn").innerHTML="Load More";
            }

            if(resp.items.length<limit){
                removeExtraDivs(resp.items.length);
            }

            var result = "";
            var item = {};
            for (var i = 0; i < resp.items.length; i++,hotThenIndex++) {
                item = resp.items[i];
//                if (document.getElementById("hot_" + runningThenIndex)!=null) {
                    var temp = document.getElementById("hot_" + hotThenIndex).innerHTML;
                    document.getElementById("hot_" + hotThenIndex).innerHTML = temp + " then " + item.text;
//                } else {
//                    result =
//                        "<div class='card card-block card-outline-primary card-custom' id='hot_" + runningThenIndex + "'>" +
//                        " then " + item.text +
//                        "</div>";
//                    document.getElementById("hot_if_thens").innerHTML+=result;
//                }
//                runningThenIndex+=1;
            }
        }
    });
}


