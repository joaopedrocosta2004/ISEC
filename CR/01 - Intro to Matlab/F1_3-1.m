close all;
clear;
clc;

% a) Criar uma matriz unidimensional com os números pares até 100
a = 2:2:100;

% b) Criar transposta da matriz anterior
b = transpose(a); % ou então poderia usar b = a'

% c) Criar a seguinte matriz [1 2 3; 4 5 6; 7 8 9]
c = [1 2 3; 4 5 6; 7 8 9];

% d) Na matriz anterior, alterar o elemento (1,3) de 3 para 33
d = c;
d(1,3) = 33;

% e) Criar uma matriz 3 x 3 e preencher essa matriz com 1
e = ones(3,3);

% f) Gere uma matriz com dimensão 8×8 constituída por números aleatórios obtidos com a função randn.
f = rand(8,8);
%    Repita o comando, mas usando a função rand. Repare nas diferenças.
f1 = randn(8,8);

% g) Remova a segunda coluna da matriz anterior.
g = f;
g(:,2) = []; % : significa todas as linhas, e o 2 é a coluna

% h) Desenho o gráfico da função seno para ângulos de 0 a 2pi, com passo de pi/100.
% Use as funções sin e plot.
x = 0 : pi/100 : 2*pi;
y = sin(x);
figure; plot(x,y);

% i) Usando a função hold do matlab acrescente ao gráfico uma linha para o 
% coseno dos valores 0 a 2pi. Use a função cos.
y = cos(x);
hold on; plot(x,y,'g');

% j) Faça as seguintes alterações ao gráfico:
%       a. Use o comando legend para criar uma legenda no gráfico anterior.
    legend('Seno', 'Cosseno');
%       b. Use o comando title para colocar um título no gráfico anterior.
    title('Seno e Cosseno');
%       c. Altere a cor da linha para verde (use %g’ de green). Use o comando plot colocando ‘g’ no
%          argumento color. Faça help plot para mais informações.
    %ja fiz na i
    
