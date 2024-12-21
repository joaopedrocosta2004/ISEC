function mean_accuracy = feedForwardNet_b_app(Neuro_Number,activationFunction_1,activationFunction_2,trainingFunction_1,divideFunction_1,trainRatio,valRatio,testRatio)

S = readmatrix("Train_Converted_Filled.csv","Delimiter",",","DecimalSeparator",".");

inputs = S(:, [1, 3:end])'; % todas as colunas exceto a segunda
target = S(:, 2)'; % 2ª coluna - Category
category = onehotencode(target,1,'ClassNames',0:4);

for i = 1:3
    filename = strcat('best_network_app', num2str(i), '.mat');
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

accuracy_total = [];

for rep = 1:10

    net = feedforwardnet(Neuro_Number);

    activationFunction = {activationFunction_1,activationFunction_2};
    net.layers{1, 3:end}.transferFcn = activationFunction{1};
    net.layers{2}.transferFcn = activationFunction{2};

    trainingFunction = trainingFunction_1;
    net.trainFcn = trainingFunction;

    net.divideFcn = divideFunction_1;

    net.divideParam.trainRatio = trainRatio;
    net.divideParam.valRatio = valRatio;
    net.divideParam.testRatio = testRatio;

    % Train the network
    [net, tr] = train(net, inputs, category);
    out = net(inputs);

    correctPredictions = (out >= 0.5) == category;
    accuracy_train = sum(correctPredictions) / length(category) * 100;

    accuracy_total = [accuracy_total, accuracy_train];

    performance = perform(net, category, out);

    % Guarda os valores da melhor rede
    if performance > best_performance_1
        best_performance_1 = performance;
        best_networks_1{1} = net;
        best_networks_1{2} = tr;
        best_networks_1{3} = {Neuro_Number, trainingFunction, activationFunction, trainRatio, valRatio, testRatio};
        best_networks_1{4} = performance;
        flag_1 = 1;
    elseif performance > best_performance_2 && performance < best_performance_1
        best_performance_2 = performance;
        best_networks_2{1} = net;
        best_networks_2{2} = tr;
        best_networks_2{3} = {Neuro_Number, trainingFunction, activationFunction, trainRatio, valRatio, testRatio};
        best_networks_2{4} = performance;
        flag_2 = 1;
    elseif performance > best_performance_3 && performance < best_performance_1 && performance < best_performance_2
        best_performance_3 = performance;
        best_networks_3{1} = net;
        best_networks_3{2} = tr;
        best_networks_3{3} = {Neuro_Number, trainingFunction, activationFunction, trainRatio, valRatio, testRatio};
        best_networks_3{4} = performance;
        flag_3 = 1;
    end
    
end

plotconfusion(category, out);
plotperf(tr);

mean_accuracy = mean(accuracy_total) * 100;

for i = 1:3

    filename = strcat('best_network_app', num2str(i), '.mat');
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
end