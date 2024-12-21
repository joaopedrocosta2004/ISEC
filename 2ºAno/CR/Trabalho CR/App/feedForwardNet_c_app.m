function accuracy = feedForwardNet_c_app(Net_Name, File_Name)



% Load the best performing networks
best_networks_1 = cell(3, 1);

filename = strcat(Net_Name, '.mat');
load(filename, 'net', 'tr', 'parameters');
best_networks_1 = {net, tr, parameters};


% Load the TEST dataset
S = readmatrix(strcat(File_Name, ".csv"), "Delimiter", ";", "DecimalSeparator", ".");

% Separate features and target
inputs = S(:, [1, 3:end])';
target= S(:, 2)';

% Initialize variables for storing metrics


% Evaluate each network on the test dataset
        net = best_networks_1{1};
  
    % Use the network on the test dataset
    out = net(inputs);

    % Compute accuracy
    accuracy = sum(vec2ind(out) == vec2ind(target)) / length(target) * 100;




% Display the results

