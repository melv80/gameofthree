
function newGame() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/newgame', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
}

function receiveEvents () {

    this.source = null;

    this.start = function () {

        var eventTable = document.getElementById("events");

        this.source = new EventSource("/ui/eventstream");

        count = 0;
        this.source.addEventListener("message", function (event) {

            // These events are JSON, so parsing and DOM fiddling are needed
            var uiEvent = JSON.parse(event.data);

            var row = eventTable.getElementsByTagName("tbody")[0].insertRow(0);

            var cell0 = row.insertCell(0);
            var cell1 = row.insertCell(1);
            var cell2 = row.insertCell(2);

            cell0.className     = "text-left";
            cell0.innerHTML = count++;

            cell1.className     = "text-left";
            cell1.innerHTML = uiEvent.player;

            cell2.className     = "text-left";
            cell2.innerHTML = uiEvent.message;

        });

        this.source.onerror = function () {
            this.close();
        };

    };

    this.stop = function() {
        this.source.close();
    }

}

events = new receiveEvents();

/*
 * Register callbacks for starting and stopping the SSE controller.
 */
window.onload = function() {
    events.start();
};
window.onbeforeunload = function() {
    events.stop();
}