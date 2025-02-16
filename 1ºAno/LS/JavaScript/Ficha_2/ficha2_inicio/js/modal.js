"use strict";
/*---------------------------------------------------------------
Janelas Modais - TOP 10 & Terminar Jogo
---------------------------------------------------------------*/
const modalCloseButtons = document.querySelectorAll(".closeModal");
const modalTop10 = document.getElementById("modal-top10");
const modalGameOver = document.getElementById("modal-gameOver");

function closeModal(element) {
  document.getElementById("modal-" + element.dataset.modalid).close();
  if (element.dataset.modalid == "gameOver") reset();
}
modalCloseButtons.forEach((x) =>
  x.addEventListener("click", () => {
    document.getElementById("modal-" + x.dataset.modalid).close();
    if (x.dataset.modalid == "gameOver") reset();
  })
);

btTop.addEventListener("click", () => modalTop10.showModal());
document.body.addEventListener("keydown", (e) => {
  if (e.key === "Escape" && modalGameOver.open) reset();
});
