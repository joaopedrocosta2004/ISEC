import React from "react";
import { PLACEHOLDER_CARD_PATH, PLACEHOLDER_CARDBACK_PATH } from "../../constants";
import "./card.css"

export default function ({name}) {
  return (
    <div className="card" data-logo= {name}>
      <img src={PLACEHOLDER_CARDBACK_PATH} className="card-back" alt="card placeholder" />
      <img src={PLACEHOLDER_CARD_PATH + name + ".png"} className="card-front" alt={name} />
    </div>
  );
}
