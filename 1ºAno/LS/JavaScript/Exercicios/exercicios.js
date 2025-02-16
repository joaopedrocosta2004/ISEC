`use strict';`

let jogadorAtual = '0';

let tabuleiro = [
    [" ", " ", " "],
    [" ", " ", " "],
    [" ", " ", " "],
    ];


function imprimeTabuleiro(tabuleiro) {
    console.log("Tabuleiro:");
    for (let i = 0; i < tabuleiro.length; i++) {
        console.log(tabuleiro[i].join(" | "));
    }
}

function verificaFimJogo(tabuleiro) {
    for (let i = 0; i < tabuleiro.length; i++) {
        if ((tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2] && tabuleiro[i][0]!='') ||
            (tabuleiro[0][i] == tabuleiro[1][i] && tabuleiro[1][i] == tabuleiro[2][i] && tabuleiro[0][i]!='') ||
            (tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2] && tabuleiro[0][0]!='') ||
            (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0] && tabuleiro[0][2]!='')) {
                console.log(`Jogador ${tabuleiro[i][0]} venceu!`);
                return 1;
        }
    }
}

function jogada(linha, coluna, tabuleiro, jogadorAtual) {
    
    do {
        console.log(`Jogador Atual - ${jogadorAtual}`);
    
        imprimeTabuleiro(tabuleiro);
        
        if (jogadorAtual == '0') {
            if (tabuleiro[linha][coluna] == ' '){
                tabuleiro[linha][coluna] = 'X';
                jogadorAtual = '1';
            } else {
                console.log("Jogada inválida! Posição ocupada.");
                return;
            }
        } else if (jogadorAtual == '1') {
            if (tabuleiro[linha][coluna] == ' '){
                tabuleiro[linha][coluna] = 'O';
                jogadorAtual = '1';
            } else {
                console.log("Jogada inválida! Posição ocupada.");
                return;
            }
        }
    } while (verificaFimJogo(tabuleiro) == 0);
}


jogada(2,2, tabuleiro, jogadorAtual);