close all;
clear;
clc;

S = readmatrix("diabetes.csv", 'Delimiter',',','DecimalSeparator','.');
inputs = S(:, 1:end-1)';
targets = S(:,end)';

net = feedforwardnet;

[net,tr] = train(net, inputs, targets);
%view(net);

out = sim(net, inputs);

plotconfusion(targets, out);
plotperf(tr);

out = (out>0.5); %confiança do neuronio
r = sum(out==targets);
accuracy = 100*r/size(targets,2);
fprintf('Accuracy total %f\n', accuracy);