function feedForwardNet_b()

close all;
clear;
clc;

S = readmatrix("Train_Converted_Filled.csv","Delimiter",",","DecimalSeparator",".");

inputs = S(:, [1, 3:end])'; % todas as colunas exceto a segunda
target = S(:, 2)'; % 2ª coluna - Category
category = onehotencode(target,1,'ClassNames',0:4);


% Initialize performance variables
for i = 1:3
    filename = strcat('best_network_', num2str(i), '.mat');
    if exist(filename, 'file') == 2
        load(filename, 'net', 'tr', 'parameters', 'performance');
        best_networks_saved{i} = {net, tr, parameters, performance};
        if i == 1
            best_performance_1 = best_networks_saved{1}{4};
        elseif i == 2
            best_performance_2 = best_networks_saved{2}{4};
        else
            best_performance_3 = best_networks_saved{3}{4};
        end
    else
        best_performance_1 = 0;
        best_performance_2 = 0;
        best_performance_3 = 0;
    end
end

flag_1 = 0;
flag_2 = 0;
flag_3 = 0;

best_networks_1 = cell(4, 1);
best_networks_2 = cell(4, 1);
best_networks_3 = cell(4, 1);

accuracy_total=[];
accuracy_teste=[];

for rep = 1:10

    Neuro = [10,10];
    net = feedforwardnet(Neuro);

    activationFunction = {'tansig','purelin'};
    net.layers{1, 3:end}.transferFcn = activationFunction{1};
    net.layers{2}.transferFcn = activationFunction{2};

    trainingFunction = 'trainlm';
    net.trainFcn = trainingFunction;

    net.divideFcn = 'dividerand';
    trainRatio = 0.70;
    valRatio = 0.15;
    testRatio = 0.15;
    net.divideParam.trainRatio = trainRatio;
    net.divideParam.valRatio = valRatio;
    net.divideParam.testRatio = testRatio;

    % Train the network
    [net, tr] = train(net, inputs, category);
    out = net(inputs);
    performance = perform(net, category, out);

    % Guarda os valores da melhor rede
    if performance > best_performance_1
        best_performance_1 = performance;
        best_networks_1{1} = net;
        best_networks_1{2} = tr;
        best_networks_1{3} = {Neuro, trainingFunction, activationFunction, trainRatio, valRatio, testRatio};
        best_networks_1{4} = performance;
        flag_1 = 1;
    elseif performance > best_performance_2 && performance < best_performance_1
        best_performance_2 = performance;
        best_networks_2{1} = net;
        best_networks_2{2} = tr;
        best_networks_2{3} = {Neuro, trainingFunction, activationFunction, trainRatio, valRatio, testRatio};
        best_networks_2{4} = performance;
        flag_2 = 1;
    elseif performance > best_performance_3 && performance < best_performance_1 && performance < best_performance_2
        best_performance_3 = performance;
        best_networks_3{1} = net;
        best_networks_3{2} = tr;
        best_networks_3{3} = {Neuro, trainingFunction, activationFunction, trainRatio, valRatio, testRatio};
        best_networks_3{4} = performance;
        flag_3 = 1;
    end
end



plotconfusion(category, out);
plotperf(tr);

for i = 1:3

    filename = strcat('best_network_', num2str(i), '.mat');
    if i == 1 && flag_1 == 1
        net = best_networks_1{1};
        tr = best_networks_1{2};
        parameters = best_networks_1{3};
        performance = best_networks_1{4};
        save(filename, 'net', 'tr', 'parameters','performance');
    elseif i == 2 && flag_2 == 1
        net = best_networks_2{1};
        tr = best_networks_2{2};
        parameters = best_networks_2{3};
        performance = best_networks_2{4};
        save(filename, 'net', 'tr', 'parameters','performance');
    elseif i == 3 && flag_3 == 1
        net = best_networks_3{1};
        tr = best_networks_3{2};
        parameters = best_networks_3{3};
        performance = best_networks_3{4};
        save(filename, 'net', 'tr', 'parameters','performance');
    end

end

for i = 1:3

    if i == 1 && flag_1 == 1
        net = best_networks_1{1};
        tr = best_networks_1{2};
    elseif i == 1 && flag_1 == 0
        net = best_networks_saved{1}{1};
        tr = best_networks_saved{1}{2};
    elseif i == 2 && flag_2 == 1
        net = best_networks_2{1};
        tr = best_networks_2{2};
    elseif i == 2 && flag_2 == 0
        net = best_networks_saved{2}{1};
        tr = best_networks_saved{2}{2};
    elseif i == 3 && flag_3 == 1
        net = best_networks_3{1};
        tr = best_networks_3{2};
    elseif i == 3 && flag_3 == 0
        net = best_networks_saved{3}{1};
        tr = best_networks_saved{3}{2};
    end

    % Refere-se a todos os dados, tanto os de treino como os de teste
    % Simular a rede para todos os dados
    out = sim(net, inputs);

    r=0;
    for i=1:size(out,2)               % Para cada classificacao  
      [a b] = max(out(:,i));          %b guarda a linha onde encontrou valor mais alto da saida obtida
      [c d] = max(category(:,i));  %d guarda a linha onde encontrou valor mais alto da saida desejada
      if b == d                       % se estao na mesma linha, a classificacao foi correta (incrementa 1)
          r = r+1;
      end
    end
    
    accuracy = r/size(out,2)*100;
    accuracy_total=[accuracy_total accuracy];

    % Refere-se apenas aos dados de teste
    % Simular a rede apenas no conjunto de teste
    TInput = inputs(:, tr.testInd);
    TTargets = category(:, tr.testInd);

    outTest = sim(net, TInput);

    %Calcula e mostra a percentagem de classificacoes corretas no conjunto de teste
    r=0;
    for i=1:size(tr.testInd,2)               % Para cada classificacao  
      [a b] = max(out(:,i));          %b guarda a linha onde encontrou valor mais alto da saida obtida
      [c d] = max(TTargets(:,i));  %d guarda a linha onde encontrou valor mais alto da saida desejada
      if b == d                       % se estao na mesma linha, a classificacao foi correta (incrementa 1)
          r = r+1;
      end
    end
    accuracy = r/size(tr.testInd,2)*100;
    accuracy_teste=[accuracy_teste accuracy];
end
fprintf('Accuracy total: %f\n', mean(accuracy_total));
fprintf('Accuracy teste: %f\n', mean(accuracy_teste));
end