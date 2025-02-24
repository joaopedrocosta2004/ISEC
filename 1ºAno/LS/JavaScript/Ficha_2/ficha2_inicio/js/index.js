"use strict";

const panelControl = document.getElementById("panel-control");
const panelGame = document.getElementById("panel-game");
const btLevel = document.getElementById("btLevel");
const btPlay = document.getElementById("btPlay");
const message = document.getElementById("message");
// Encontra todas a classes list-item
const elementos = document.querySelectorAll(".list-item");
const cards = panelGame.querySelectorAll(".card");

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
  const newCardLogos = cardsLogos.slice(0, 3);
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
    card.addEventListener("click", flipCard);
  });
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

  hideCards();
  reset();
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
  this.classList.toggle("flipped");
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

reset();
