"use strict";

let panelControl = document.getElementById("panel-control");
let panelGame = document.getElementById("panel-game");
let btLevel = document.getElementById("btLevel");
let btPlay = document.getElementById("btPlay");

function reset() {
    panelGame.style.display = "none";
    btPlay.disabled = true;

    if (btLevel.value != "0") {
        btPlay.disabled = false;
        panelGame.style.display = "grid";
    }
}

btLevel.addEventListener("change", function() {
    reset();
});
