let socket = new WebSocket("ws://"+window.location.host+"/progressWS");

socket.onopen = e => {
    console.log("WebSocket connection established");
};

socket.onmessage = event => {
    let response = JSON.parse(event.data);
    console.log(`[message] Data received from server: ${response.progress}`);
    document.getElementById("progress").innerText = `${response.progress}`;
    if (response.progress==100){
        alert("Insert eolic plant"
                +"\n ID: "+response.id
                +"\n city: "+response.city
                +"\n planning: "+response.planning+"");
        location.reload();
    }
};

socket.onclose = event => {
    if (event.wasClean) {
        console.log(`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}`);
    } else {
        console.log('[close] Connection died');
    }
};

socket.onerror = error => {
    console.log(`[error] ${error.message}`);
};

function sendCity() {    
    socket.send(document.getElementById("city").value);
    console.log("WebSocket message sent");
}