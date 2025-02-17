"use strict";

let panelControl = document.getElementById("panel-control");
let panelGame = document.getElementById("panel-game");
let btLevel = document.getElementById("btLevel");
let btPlay = document.getElementById("btPlay");
let message = document.getElementById("message");

function reset() {
    panelGame.style.display = "none";
    btPlay.disabled = true;

    // Habilita o botão Iniciar Jogo caso o level seja diferente de 0
    if (btLevel.value != "0") {
        // Habilita o botão Iniciar Jogo 
        btPlay.disabled = false;
        // Mostra o painel das cartas do jogo
        panelGame.style.display = "grid";
    }

    // Encontra todas a classes list-item
    let itens = document.querySelectorAll('.list-item');

    // Quando termina o jogo retira a classe gameStarted
    itens.forEach(item => {
        item.classList.remove('gameStarted');
    });
}

function startGame() {
    // Desabilita o dropdown
    btLevel.disabled = true;
    // Muda o texto do botão Iniciar Jogo para Terminar Jogo
    btPlay.innerHTML = "Terminar Jogo";

    // Encontra todas a classes list-item
    let itens = document.querySelectorAll('.list-item');

    // Quando começa o jogo adiciona a classe gameStarted
    itens.forEach(item => {
        item.classList.add('gameStarted');
    });

    // Para que o elemento com o texto “Clique em Iniciar Jogo” não seja visível quando o jogo tiver iniciado
    message.classList.add('hide');
}

function stopGame() {
    // Habilita o dropdown
    btLevel.disabled = false;
    // Muda o texto do botão Iniciar Jogo para Iniciar Jogo
    btPlay.innerHTML = "Iniciar Jogo";
    // Para que o elemento com o texto “Clique em Iniciar Jogo” seja visível quando o jogo tiver terminado
    message.classList.remove('hide');
    reset();
}

btLevel.addEventListener("change", function() {
    reset();
});

btPlay.addEventListener("click", () => {
    if (btPlay.innerHTML === "Iniciar Jogo") {
        startGame();
    } else {
        stopGame();
    }
})

panelGame.addEventListener("click", function() {
    message.classList.toggle('hide');
});
