/**
 * Created by bjeyara on 10/14/16.
 */

function ifs_init() {
    gapi.client.load('ifBeanApi', 'v1', populateIfs, 'https://if-then.appspot.com/_ah/api/');
}

function thens_init() {
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
                result =
                    "<div class='card card-block card-outline-primary card-custom'>" +
                    "<p>" + item.text + "</p>" +
                    "<div class='card-footer' style='background-color: transparent'>" +
                    "<a class='btn btn-danger' onclick='minusCount(" + item.id + ",0)' style='margin-right: 5px'>-</a>" +
                    "<a id='if_count" + item.id + "'>" + item.count + "</a>" +
                    "<a class='btn btn-success' onclick='plusCount(" + item.id + ",0)' style='margin-left: 5px'>+</a>" +
                    "<a class='pull-lg-right'> - " + item.sessionId + "</a>" +
                    "</div> " +
                    "</div>";
                document.getElementById("if_card_deck").innerHTML+=result;
            }
            // document.getElementById("if_card_deck").innerHTML = result;
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
                result =
                    "<div class='card card-block card-outline-primary card-custom'>" +
                    "<p>" + item.text + "</p>" +
                    "<div class='card-footer' style='background-color: transparent'>" +
                    "<a class='btn btn-danger' onclick='minusCount(" + item.id + ",1)' style='margin-right: 5px'>-</a>" +
                    "<a id='then_count" + item.id + "'>" + item.count + "</a>" +
                    "<a class='btn btn-success' onclick='plusCount(" + item.id + ",1)' style='margin-left: 5px'>+</a>" +
                    "<a class='pull-lg-right'> - " + item.sessionId + "</a>" +
                    "</div> " +
                    "</div>";
                document.getElementById("then_card_deck").innerHTML += result;
            }
            // document.getElementById("then_card_deck").innerHTML = result;
        }
    });
}

function plusCount(id, flag) {
    var element;
    if (flag === 1) {
        element = document.getElementById("then_count" + id);
    } else if (flag === 0) {
        element = document.getElementById("if_count" + id);
    }
    var count = parseInt(element.text);
    count++;
    element.text = count;
    var request = {};
    request.id = id;
    request.count = count;
    if (flag === 1) {
        gapi.client.thenBeanApi.updateCount(request).execute(function (resp) {
            if (!resp.code) {

            }
        });
    } else if (flag === 0) {
        gapi.client.ifBeanApi.updateCount(request).execute(function (resp) {
            if (!resp.code) {

            }
        });
    }
}

function minusCount(id, flag) {
    var element;
    if (flag === 1) {
        element = document.getElementById("then_count" + id);
    } else if (flag === 0) {
        element = document.getElementById("if_count" + id);
    }
    var count = parseInt(element.text);
    count--;
    element.text = count;
    var request = {};
    request.id = id;
    request.count = count;
    if (flag === 1) {
        gapi.client.thenBeanApi.updateCount(request).execute(function (resp) {
            if (!resp.code) {

            }
        });
    } else if (flag === 0) {
        gapi.client.ifBeanApi.updateCount(request).execute(function (resp) {
            if (!resp.code) {

            }
        });
    }
}
