"use strict";
const panelControl = document.querySelector("#panel-control");
const panelGame = document.querySelector("#game");
const btLevel = document.querySelector("#btLevel");
const btPlay = document.querySelector("#btPlay");
const message = document.querySelector("#message");
const infoGame = panelControl.querySelectorAll(".list-item");
let flippedCards = [];
let totalFlippedCards = 0;
let cards = document.querySelectorAll(".card");
let cardsLogos = [
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

// Algoritmo Fisher-Yates -  Algoritmo que baralha um array.
const shuffleArray = (array) => {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    const temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }
};

function reset() {
  btLevel.value = 1;
  btLevel.disabled = true;
  cards.forEach((card) => {
    card.classList.remove("flipped", "inactive");
    card.querySelector(".card-front").classList.remove("grayscale");
    card.removeEventListener("click", flipCard);
  });
  panelControl.querySelectorAll(".list-item").forEach((elemento) => {
    elemento.classList.remove("gameStarted");
  });
}
function flipCard() {
  if (!this.classList.contains("flipped")) {
    this.classList.add("flipped");
    flippedCards.push(this);
    if (flippedCards.length === 2) checkPair();
  }
}
const gameOver = () => totalFlippedCards === cards.length;

function checkPair() {
  let [card1, card2] = flippedCards;
  const isMatch = card1.dataset.logo === card2.dataset.logo;
  if (isMatch) {
    setTimeout(() => {
      card1.classList.add("inactive");
      card2.classList.add("inactive");
      card1.querySelector(".card-front").classList.add("grayscale");
      card2.querySelector(".card-front").classList.add("grayscale");
      totalFlippedCards += 2;
      if (gameOver()) stopGame();
    }, 500);
  } else {
    setTimeout(() => {
      card1.classList.remove("flipped");
      card2.classList.remove("flipped");
    }, 500);
  }
  flippedCards = [];
}
const createAndShuffleCards = (array) => {
  shuffleArray(array);
  array.splice(cards.length / 2, Number.MAX_VALUE);
  array.push(...array);
  shuffleArray(array);
};

function startGame() {
  btPlay.textContent = "Terminar Jogo";
  let [indice, newCardLogos] = [0, [...cardsLogos]];
  createAndShuffleCards(newCardLogos);
  panelControl
    .querySelectorAll(".list-item")
    .forEach((elemento) => elemento.classList.add("gameStarted"));
  for (let card of cards) {
    let cardFront = card.querySelector(".card-front");
    cardFront.src = `images/${newCardLogos[indice]}.png`;
    card.dataset.logo = newCardLogos[indice++];
    card.addEventListener("click", flipCard);
  }
  flippedCards = [];
  totalFlippedCards = 0;
}
//---

function stopGame() {
  btPlay.textContent = "Iniciar Jogo";
  message.classList.remove("hide");
  modalGameOver.showModal();
}

btPlay.addEventListener("click", () =>
  btPlay.textContent === "Terminar Jogo" ? stopGame() : startGame()
);
panelGame.addEventListener(
  "click",
  () =>
    (message.textContent =
      message.textContent === "" ? "Clique em Iniciar o Jogo!" : "")
);
reset();
