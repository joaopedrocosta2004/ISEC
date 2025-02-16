`use strict';`

//------------------------------------------------------------------------------
// Exercício 1.a)

// const num1 = 5;
// const num2 = 10;

// if (num1 > num2) {
//     console.log(`O maior entre ${num1} e o ${num2} é ${num1}!`);
// } else if (num1 < num2) {
//     console.log(`O maior entre ${num1} e o ${num2} é ${num2}!`);
// } else {
//     console.log(`Os números ${num1} e ${num2} são iguais.`);
// }

//------------------------------------------------------------------------------
// Exercício 1.b)

// const num1 = 5;
// const num2 = 10;
// const num3 = 10;

// if (num1 > num2 && num2 > num3) {
//     console.log(`O maior entre ${num1}, ${num2} e o ${num3} é ${num1}!`);
// } else if (num1 < num2 && num2 > num3) {
//     console.log(`O maior entre ${num1}, ${num2} e o ${num3} é ${num2}!`);
// } else if (num1 > num2 && num1 < num3) {
//     console.log(`O maior entre ${num1}, ${num2} e o ${num3} é ${num3}!`);
// }else {
//     console.log(`Os números ${num1}, ${num2} e o ${num3} são iguais.`);
// }

//------------------------------------------------------------------------------
// Exercício 2

// const numeros = [5, 10, -12, 2, 15, -5, -2, -3]


// 2.a)
// console.log(numeros.length);

//2.b)
// var max = 0;
// for (let i = 0; i < numeros.length; i++) {
//     if (numeros[i] > max || i == 0) {
//         max = numeros[i];
//     }
// }

// console.log(`O maior número é ${max}`);

// 2.c)
// var soma = 0;

// for (let i = 0; i < numeros.length; i++) {
//     if (numeros[i] > 0) {
//         soma = numeros[i] + soma;
//     }
// }

// console.log(`A soma dos números positivos é ${soma}`);

//------------------------------------------------------------------------------
// Exercício 3

// 3.a)
// function compara(a, b) {
    
    // if (a == b) {
        // return true;
    // } else {
        // return false;
    // }
// }

// 3.b)
// function parOuImpar(a) {
//     if (a % 2 == 0) {
//         console.log('par');
//     } else {
//         console.log('impar');
//     }
// }

// 3.c)
// var frase = "Ola eu sou o joao"; 
// function contaVogais (frase) {
//     let vogais = ['a', 'e', 'i', 'o', 'u'];
//     let quantidadeVogais = 0;

//     for (let i = 0; i < frase.length; i++) {
//         if (vogais.includes(frase[i].toLowerCase())) {
//             quantidadeVogais++;
//         }
//     }

//     console.log(`O numero de vogais é ${quantidadeVogais}!`);
// }

// contaVogais(frase);

//3.d)
const palavras = ['angular','bootstrap','javascript','vue','svelte','react'];
var palavra = "Joao";

function imprimeArray(palavras) {
    for (let i = 0; i < palavras.length; i++) {
        console.log(palavras[i]);
    }
}

function insertBegin(palavras, palavra) {
    let newArray = [palavra, ...palavras];
    return newArray;
}

let newArray = insertBegin(palavras, palavra);
console.log(newArray[0]);