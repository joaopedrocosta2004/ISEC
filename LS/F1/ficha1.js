'use strict';

console.log("Ficha 1 - LS");
console.log("\nEX - 2");
// 2a
console.log("EX - 2a");
const num1 = 10;
const num2 = 8;

if (num1 > num2){
    console.log(`O maior entre ${num1} e ${num2} é ${num1}`);
} else if (num1 < num2){
    console.log(`O maior entre ${num1} e ${num2} é ${num2}`);
} else {
    console.log("Os números são iguais!")
}


// 2b
console.log("\nEX - 2b");
const num3 = 9;

if (num1 > num2 && num1 > num3){
    console.log(`O maior entre ${num1}, ${num2} e ${num3} é ${num1}`)
} else if (num2 > num1 && num2 > num3){
    console.log(`O maior entre ${num1}, ${num2} e ${num3} é ${num2}`)
} else if (num3 > num2 && num3 > num2){
    console.log(`O maior entre ${num1}, ${num2} e ${num3} é ${num3}`)
} else {
    console.log("Os números são todos iguais!")
}


// 2c
console.log("\nEX - 2c");
let min;
let max;
let soma;

if (num1 > num2 && num1 > num3){
    max = num1;
    if (num2 < num3){
        min = num2;
    } else {
        min = num3;
    }
} else if (num2 > num1 && num2 > num3){
    max = num2;
    if (num1 < num3){
        min = num1;
    } else {
        min = num3;
    }
} else if (num3 > num1 && num3 > num2){
    max = num3;
    if (num1 < num2){
        min = num1;
    } else {
        min = num2;
    }
} else {
    console.log("Os números são todos iguais!");
}
soma = min + max;
console.log(`A soma entre ${max} e ${min} é ${soma}`);

// 3
console.log("\nEX - 3");
const numeros = [5,10,-12,2,10,-5,-2,-3]

// 3a
console.log("EX - 3a");
console.log(numeros.length);

// 3b
console.log("\nEX - 3b");
let max_numeros = 0;
for (let i = 0; i < numeros.length; i++){
    if (i > max_numeros){
        max_numeros = numeros[i];
    }
}
console.log(max_numeros);

// 3c
console.log("\nEX - 3c");
let soma_numeros = 0;
for (let i = 0; i < numeros.length; i++){
    if (numeros[i] > 0){
        console.log(numeros[i]);
        soma_numeros = soma_numeros + numeros[i];
    }
}
console.log(`A soma dos numeros é ${soma_numeros}`);

//Exercicio 5
//Exercicio 5a

function compara1(num1, num2) {
    return num1===num2;
}

function compara2(num1, num2) {
    if (num1 === num2) {
        return true;
    } else {
        return false;
    }
}
console.log(compara1(5,5));
console.log(compara2(5,5));

//Exercicio 5b

function parOUimpar(num){
    let resto;
    resto = num % 2;
    if (resto === 0) {
        console.log("O número é par!");
    } else {
        console.log("O número é impar!");
    }
}
parOUimpar(2)

//Exercicio 5c

function obtemQuadrado(num){
    let res;
    res = num * num;
    return res;
}

console.log(obtemQuadrado(2));
console.log(obtemQuadrado(4));

// Exercicio 5d

function areaRetangulo(larg, comp=larg){
    let area;
    area = larg * comp;
    return area;
}

console.log(areaRetangulo(5,10));
console.log(areaRetangulo(5));

//Exercicio 5e

function contaVogais(userWord) {
    let count = 0;
    let vogals = ["a", "e", "i", "o", "u"];
    let word = userWord.toLowerCase();
    for (let i = 0; i < word.length; i++) {
        let letter = word.charAt(i);
        for (let j = 0; j < vogals.length; j++){
            if (letter === vogals[j]){
                count += 1;
            }
        }
    }

    return count;
}
console.log(contaVogais("Ola"));

//Exercicio 5f

const palavras=['angular','bootstrap','javascript','vue','svelte','react'];

function imprimeArray(palavras) {
    for (let i = 0; i < palavras.length; i++) {
        console.log(palavras[i]);
    }
}

function insertBegin(palavras, palavra) {
    for (let i = palavras.length - 1; i >= 0; i--) {
        palavras[i + 1] = palavras[i];
    }

    palavras[0] = palavra;

    for (let i = 0; i < palavras.length; i++) {
        console.log(palavras[i]);
    }

}

console.log("Array original: ")
imprimeArray(palavras)
console.log(" ")
console.log("Array modificado: ")
insertBegin(palavras, "java")