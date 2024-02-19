"use strict";

// Exercicio 1a - ficha_1
const panelControl = document.getElementById('panel-control');
const panelGame = document.getElementById('panel-game');
const btLevel = document.getElementById('btLevel');
const btPlay = document.getElementById('btPlay');
const message = document.getElementById('message');
const listItems = document.querySelectorAll('#painelControl .list-item');

//Exercicio 1b - ficha_1
function reset() {
    
    

    if (btLevel.value === '0') {
        btPlay.disabled = true;        
        panelGame.style.display = 'none';
        message.textContent = '';
        message.classList.remove('hide');
    } else {
        btPlay.disabled = false;
        panelGame.style.display = 'grid';
        message.textContent = 'Clique em Iniciar o Jogo!';
    }

    listItems.forEach(item => {
        item.classList.remove('gameStarted');
    });    
}

function startGame() {
    btPlay.textContent = 'Terminar Jogo';
    message.style.display = 'hide';
    btLevel.disabled = true;

    listItems.forEach(item => {
        item.classList.add('gameStarted');
    });
}

function stopGame() {
    btPlay.textContent = 'Iniciar Jogo';
    btLevel.disabled = false;
    reset();
}

btPlay.addEventListener('click', function() {
    if (btPlay.textContent === 'Iniciar Jogo') {
        startGame();
    } else {
        stopGame();
    }
});

btLevel.addEventListener('change', function() {
    reset();
});

panelGame.addEventListener('click', function() {
    if (message.textContent === 'Clique em Iniciar o Jogo!') {
        message.textContent = '';
        message.classList.remove('hide');
    }
});