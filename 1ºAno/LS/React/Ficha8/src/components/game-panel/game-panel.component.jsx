import React from "react";
import "./game-panel.css";
import { Card } from "../index";
import { CARDS_LOGOS } from "../../constants";

export default function GamePanel() {
  return (
    <section id="panel-game">
      <h3 className="sr-only">Peças do Jogo</h3>
      <div id="game">
        {CARDS_LOGOS.slice(0, 6).map((index, name) => (
          <Card key={index} name={name} />
        ))}
      </div>
    </section>
  );
}
