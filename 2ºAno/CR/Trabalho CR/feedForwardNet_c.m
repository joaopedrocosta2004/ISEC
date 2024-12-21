function feedForwardNet_c()

close all;
clear all;
clc;

% Load the best performing networks
best_networks_1 = cell(3, 1);
best_networks_2 = cell(3, 1);
best_networks_3 = cell(3, 1);

for i = 1:3
    if i == 1
        filename = strcat('best_network_', num2str(i), '.mat');
        load(filename, 'net', 'tr', 'parameters');
        best_networks_1 = {net, tr, parameters};
    elseif i == 2
        filename = strcat('best_network_', num2str(i), '.mat');
        load(filename, 'net', 'tr', 'parameters');
        best_networks_2 = {net, tr, parameters};
    else
        filename = strcat('best_network_', num2str(i), '.mat');
        load(filename, 'net', 'tr', 'parameters');
        best_networks_3 = {net, tr, parameters};
    end
end

% Load the TEST dataset
S = readmatrix("Test.csv", "Delimiter", ";", "DecimalSeparator", ".");

% Separate features and target
inputs = S(:, [1, 3:end])';
target= S(:, 2)';



% Evaluate each network on the test dataset
for i = 1:3
    if i == 1
        net = best_networks_1{1};
    elseif i == 2
        net = best_networks_2{1};
    else
        net = best_networks_3{1};
    end
    % Use the network on the test dataset
    out = sim(net,inputs);

    % Compute accuracy
    accuracy = sum(vec2ind(out) == vec2ind(target)) / length(target) * 100;

    % Store metrics
    disp(['Network ', num2str(i), ' - Test Accuracy: ', num2str(accuracy),'%']);
end


