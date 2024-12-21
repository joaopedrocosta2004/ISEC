function feedForwardNet_a()

close all;
clear;
clc;

% Carregar os dados do arquivo START
    S = readmatrix("Start.csv", 'Delimiter',';','DecimalSeparator','.');
    inputs = S(:, [1, 3:end])'; % Todas as colunas exceto a segunda
    target = S(:, 2)'; % Segunda coluna - Category

    % Inicializar a rede feedforward com uma camada de 10 neurônios
    net = feedforwardnet(10);

    % Treinar a rede
    tic;
    [net, tr] = train(net, inputs, target);
    trainingTime = toc;

    % Simular nos dados de treino
    out = sim(net, inputs);

    % Calcular a precisão total nos dados de treino
    correctPredictions = (out >= 0.5) == target;
    accuracy_train = sum(correctPredictions) / length(target) * 100;

    % Calcular o erro médio quadrático
    error = mse(net, target, out);

    disp(['Total Accuracy: ' num2str(accuracy_train) '%']);
    disp(['Error: ' num2str(error)]);
    disp(['Execution Time: ' num2str(trainingTime) ' segundos']);

    plotconfusion(target, out);
    plotperf(tr);


end