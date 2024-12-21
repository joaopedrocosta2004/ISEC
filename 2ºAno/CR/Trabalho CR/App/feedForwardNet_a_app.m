function stats = feedForwardNet_a_app(Ficheiro,Neuronios)

    % Carregar os dados do arquivo
    S = readmatrix(strcat(Ficheiro,".csv"), 'Delimiter',';','DecimalSeparator','.');
    inputs = S(:, [1, 3:end])'; % Todas as colunas exceto a segunda
    target = S(:, 2)'; % Segunda coluna - Category

    % Inicializar a rede feedforward com uma camada de 10 neurônios
    net = feedforwardnet(Neuronios);

    % Treinar a rede
    tic;
    net = train(net, inputs, target);
    trainingTime = toc;

    % Simular nos dados de treino
    out = sim(net, inputs);

    % Calcular a precisão total nos dados de treino
    correctPredictions = (out >= 0.5) == target;
    accuracy_train = sum(correctPredictions) / length(target) * 100;

    % Calcular o erro médio quadrático
    error = mse(net, target, out);

    % Armazenar as variáveis em uma estrutura
    stats.TotalAccuracy = accuracy_train;
    stats.Error = error;
    stats.ExecutionTime = trainingTime;

    % Exibir as estatísticas
    disp(['Total Accuracy: ' num2str(accuracy_train) '%']);
    disp(['Error: ' num2str(error)]);
    disp(['Execution Time: ' num2str(trainingTime) ' segundos']);

end
