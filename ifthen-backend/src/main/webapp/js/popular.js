/**
 * Created by bjeyara on 10/14/16.
 */

function popular_init() {
    gapi.client.load('ifBeanApi', 'v1', populateIfs, 'https://if-then.appspot.com/_ah/api/');
    gapi.client.load('thenBeanApi', 'v1', populateThens, 'https://if-then.appspot.com/_ah/api/');
}

function populateIfs() {
    var request = {};
    gapi.client.ifBeanApi.list(request).execute(function (resp) {
        if (!resp.code) {
            resp.items = resp.items || [];
            var result = "";
            var item = {};
            for (var i = 0; i < resp.items.length; i++) {
                item = resp.items[i];
                if (document.getElementById("hot_" + i)!=null) {
                    var temp = document.getElementById("hot_" + i).innerHTML;
                    document.getElementById("hot_" + i).innerHTML = "If " + item.text + temp;
                } else {
                    result =
                        "<div class='card card-block card-text card-outline-primary card-custom' id='hot_" + i + "'>" +
                        "If " + item.text +
                        "</div>";
                    document.getElementById("hot_if_thens").innerHTML+=result;
                }
            }
        }
    });
}

function populateThens() {
    gapi.client.thenBeanApi.list().execute(function (resp) {
        if (!resp.code) {
            resp.items = resp.items || [];
            var result = "";
            var item = {};
            for (var i = 0; i < resp.items.length; i++) {
                item = resp.items[i];
                if (document.getElementById("hot_" + i)!=null) {
                    var temp = document.getElementById("hot_" + i).innerHTML;
                    document.getElementById("hot_" + i).innerHTML = temp + " then " + item.text;
                } else {
                    result =
                        "<div class='card card-block card-outline-primary card-custom' id='hot_" + i + "'>" +
                        " then " + item.text +
                        "</div>";
                    document.getElementById("hot_if_thens").innerHTML+=result;
                }
            }
        }
    });
}


