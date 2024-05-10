function trainHeart()

clear all;
close all;

S = readmatrix('heart_train.csv', 'Delimiter', ',', 'DecimalSeparator', '.');

p = S(:,1:end-1)';
t = S(:,end)';

net = feedforwardnet([10 10 10]); %[10 10 10] rede neuronal com 3 camadas e 10 neuronios cada uma

net.divideFcn = '';
[net,tr] = train(net,p,t);
view(net);

y = sim(net,p);

y = (y >= 0.5);
accuracy = sum(y==t)/length(t);
disp(['Accuracy = ' num2str(accuracy)]);
plotperform(tr);

save('nn_heart1.mat', 'net');