"use strict";

const panelControl = document.getElementById("panel-control");
const panelGame = document.getElementById("panel-game");
const btLevel = document.getElementById("btLevel");
const btPlay = document.getElementById("btPlay");
const message = document.getElementById("message");
const elementos = document.querySelectorAll(".list-item");
let cards = panelGame.querySelectorAll(".card");
const messageGameOver = document.getElementById("messageGameOver");
const nickname = document.getElementById("nickname");
// Variaveis de pontuação
let labelPoints = document.getElementById("points");
let totalPoints;
// Variaveis de verificação de jogo
let totalFlippedCards;
let flippedCards;
// Variaveis de Tempo
const TIMEOUTGAME_BASICO = 20;
const TIMEOUTGAME_INTERMEDIO = 60;
const TIMEOUTGAME_AVANCADO = 180;
const labelGameTime = document.getElementById("gameTime");
let timer;
let timerId;
// Variaveis de topGamers
let topGamers = [
  { nickname: 'Ze', points: 331},
  { nickname: 'Maria', points: 321}
];
const infoTop = document.getElementById("infoTop");

// Array de cartas
const cardsLogos = [
  "angular",
  "bootstrap",
  "html",
  "javascript",
  "vue",
  "svelte",
  "react",
  "css",
  "backbone",
  "ember",
];

function getLastPoints() {
  if (topGamers.length > 0) {
    return topGamers[topGamers.length - 1].points; 
  } else {
    return 0;
  }
}

const shuffleArray = (array) => {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    const temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }
};

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

  // Quando termina o jogo retira a classe gameStarted
  elementos.forEach((elemento) => {
    elemento.classList.remove("gameStarted");
  });

  cards.forEach((card) => {
    card.classList.remove("flipped");
    card.classList.remove("inactive");
    card.querySelector(".card-front").classList.remove("grayscale");
  });

  labelGameTime.removeAttribute("style");

  createPanelGame();
}

function startGame() {
  // Desabilita o dropdown
  btLevel.disabled = true;
  // Muda o texto do botão Iniciar Jogo para Terminar Jogo
  btPlay.innerHTML = "Terminar Jogo";

  // Quando começa o jogo adiciona a classe gameStarted
  elementos.forEach((elemento) => {
    elemento.classList.add("gameStarted");
  });

  // Para que o elemento com o texto “Clique em Iniciar Jogo” não seja visível quando o jogo tiver iniciado
  message.classList.add("hide");

  // console.table(cardsLogos);
  shuffleArray(cardsLogos);
  // console.table(cardsLogos);

  // Obter os primeiros 3 elementos do array inicial
  const newCardLogos = cardsLogos.slice(0, cards.length/2);
  // Duplicar o array para criar os pares
  newCardLogos.push(...newCardLogos);
  // Embaralha o array
  shuffleArray(newCardLogos);

  cards.forEach((card, index) => {
    // Altera o data-logo da carta
    card.dataset.logo = newCardLogos[index];

    // Altera a imagem da carta
    const img = card.querySelector(".card-front");
    img.src = `images/${newCardLogos[index]}.png`;
    // Adiciona o evento a todas as cartas do jogo
    card.addEventListener("click", flipCard, { once: true });
  });

  flippedCards = [];
  totalFlippedCards = 0;
  timer = getTime();
  labelGameTime.textContent = `${timer}s`;
  timerId = setInterval(updateGameTime, 1000);

  totalPoints = 0;
  labelPoints.textContent = totalPoints;

  getTopPoints();
}

function stopGame() {
  // Habilita o dropdown
  btLevel.disabled = false;
  // Muda o texto do botão Iniciar Jogo para Iniciar Jogo
  btPlay.innerHTML = "Iniciar Jogo";
  // Para que o elemento com o texto “Clique em Iniciar Jogo” seja visível quando o jogo tiver terminado
  message.classList.remove("hide");

  // Remove o evento de todas as cartas quando o jogo termina
  cards.forEach((card) => {
    card.removeEventListener("click", flipCard);
  });

  // hideCards();
  // reset();
  clearInterval(timerId);
  messageGameOver.textContent = `Pontuação: ${totalPoints}`;
  nickname.style.display = "none";
  modalGameOver.showModal();
  // Verifica se o player tem uma pontuaçao melhor que os 10 primeiros
  if (checkTop10()){
    nickname.style.display = "block"
    messageGameOver.innerHTML += "<br>Parabéns! Entrou no TOP 10!"
  } else {
    nickname.style.display = "none"
  }
}

function showCards() {
  for (const card of cards) {
    card.classList.add("flipped");
  }
}

function hideCards() {
  for (const card of cards) {
    card.classList.remove("flipped");
  }
}

function flipCard() {
  this.classList.add("flipped");
  flippedCards.push(this);
  // Quando há duas cartas viradas, verifica se são pares
  if (flippedCards.length === 2) {
    checkPair();
  }
}

function checkPair() {
  // Destrutura o array de duas cartas, em duas variaveis com cada carta
  const [card1, card2] = flippedCards;
  // Verifica de os cards-logos são iguais
  if (card1.dataset.logo === card2.dataset.logo) {
    // Se forem pares, mantém as cartas viradas
    setTimeout(() => {
      console.log("São Iguais!");
      card1.classList.add("inactive");
      card2.classList.add("inactive");
      card1.querySelector(".card-front").classList.add("grayscale");
      card2.querySelector(".card-front").classList.add("grayscale");
      flippedCards = [];
      totalFlippedCards = totalFlippedCards + 2;
      updatePoints("+");
      if (gameOver() == true) {
        stopGame();
      }
    }, 200);
  } else {
    // Se não forem pares, volta as cartas para a posição inicial
    setTimeout(() => {
      console.log("Não são iguais!");
      flippedCards = [];
      card1.addEventListener("click", flipCard, { once: true });
      card2.addEventListener("click", flipCard, { once: true });
      card1.classList.remove("flipped");
      card2.classList.remove("flipped");
      updatePoints("-");
    }, 200);
  }
}

function gameOver() {
  if (totalFlippedCards == cards.length) {
    return true;
  } else {
    return false;
  }
}

function updateGameTime() {
  timer = timer - 1;
  if (timer < 10) {
    labelGameTime.style.backgroundColor = "red";
  }
  labelGameTime.textContent = `${timer}s`;
  if (timer == 0) {
    stopGame();
  }
}

function getTime() {
  if (btLevel.value == 1) {
    return TIMEOUTGAME_BASICO;
  } else if (btLevel.value == 2) {
    return TIMEOUTGAME_INTERMEDIO;
  } else if (btLevel.value == 3) {
    return TIMEOUTGAME_AVANCADO;
  }
}

function updatePoints(operation) {
  let nParesEmFalta = (cards.length-totalFlippedCards) / 2;
  if (operation == '+') {
    if (nParesEmFalta > 0){
      totalPoints += timer * nParesEmFalta;
    } else if (nParesEmFalta == 0) {
      totalPoints += timer;
    }
    
  } else if (operation == '-') {
    if (totalPoints > 5) {
      totalPoints -= 5;
    } else {
      totalPoints = 0;
    }
  }
  labelPoints.textContent = `${totalPoints}`;
}

function createPanelGame() {

  // Cria o painel do jogo
  panelGame.innerHTML = "";
  panelGame.className = "";

  let nCards;
  if (btLevel.value == 1) {
    nCards = 6;
  } else if (btLevel.value == 2) {
    nCards = 12;
    panelGame.classList.add("intermedio");
  } else if (btLevel.value == 3) {
    nCards = 20;
    panelGame.classList.add("avancado");
  }

  let newDiv = document.createElement("div");
  newDiv.className = "card";

  let imgBack = document.createElement("img");
  imgBack.setAttribute("src",'images/ls.png');
  imgBack.className = "card-back";

  let imgFront = document.createElement("img");
  imgFront.className = "card-front";
  
  newDiv.appendChild(imgBack);
  newDiv.appendChild(imgFront);
  // panelGame.appendChild(newDiv);

  for (let i = 0; i < nCards; i++) {
    let cardClone = newDiv.cloneNode(true);
    panelGame.appendChild(cardClone);
  }

  cards = panelGame.childNodes;

}

function getTop10() {
  // let gamers = "";
  //   gamers +="<p>" + topGamer.nickname + ' - ' + topGamer.points + "<p/>";
  // infoTop.innerHTML = gamers;
  infoTop.textContent = "";
  // Cria o html base para o cabeçalho
  let newDiv = document.createElement("div");
  let p1 = document.createElement("p");
  let p2 = document.createElement("p");
  p1.textContent = "Nickname";
  p2.textContent = "Pontuação";
  newDiv.appendChild(p1);
  newDiv.appendChild(p2);
  infoTop.appendChild(newDiv);

  topGamers.forEach(topGamer => {
    let newDivPlayer = newDiv.cloneNode(true);
    newDivPlayer.firstChild.textContent = topGamer.nickname;
    newDivPlayer.lastChild.textContent = topGamer.points;
    infoTop.appendChild(newDivPlayer);
  }); 
  console.log("ola4");
}

function getTopPoints() {
  const pointsTop = document.getElementById("pointsTop");
  if (topGamers.length > 0){
    pointsTop.textContent = topGamers[0].points
  } else {
    pointsTop.textContent = 0;
  }
}

function checkTop10() {
  if (topGamers.length < 10){
    return true;
  } else if (getLastPoints() < totalPoints){
    return true;
  } else {
    return false;
  }
}

function saveTop10() {
  let name = document.querySelector('#inputNick').value;
  let exists = false;
  // Verifica se o jogador existe
  topGamers.forEach(topGamer => {
    console.log("topgamers: ", topGamer)
    if (topGamer.name === name) {
      exists = true;
      console.log("exists true")
      if (totalPoints > topGamer.points) {
        topGamer.points = totalPoints;
      }
    }
  });
  // Caso o jogador nao exista no array, adiciona
  if (!exists) {
    topGamers.push({ nickname: name, points: totalPoints });
    console.log("exists false")
    console.log(topGamers)
  }
  topGamers.sort(function (a, b) { return b.points - a.points }); 
    
  if (topGamers.length > 10) {
    // Remove o jogador com a menor pontuação
    topGamers.pop();
    console.log("topGamers after pop: ", topGamers)  
  }

  localStorage.setItem("nickname", name);
  localStorage.setItem("topGamers", JSON.stringify(topGamers));
}

function getLocalStorage() {
  let nickname = localStorage.getItem("nickname");
  let topGamers = JSON.parse(localStorage.getItem("topGamers")) || [];
}

btLevel.addEventListener("change", function () {
  reset();
});

btPlay.addEventListener("click", () => {
  if (btPlay.innerHTML === "Iniciar Jogo") {
    startGame();
  } else {
    stopGame();
  }
});

panelGame.addEventListener("click", function () {
  message.classList.toggle("hide");
});

btTop.addEventListener("click", getTop10);

okTop.addEventListener("click", () => {
  saveTop10();
  modalGameOver.close();
  reset();
});

reset();
getLocalStorage();
