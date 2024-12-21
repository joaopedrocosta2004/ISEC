function retrieve()

clear all;
close all;
clc;

% Carregar o conjunto de dados TRAIN
Data = readtable("Train.csv");

% Definir os valores correspondentes para a coluna 'Category'
categories = {'0=Blood Donor', '0s=suspect Blood Donor', '1=Hepatitis', '2=Fibrosis', '3=Cirrhosis'};
categoryValues = 0:4;

% Converter a coluna 'Category' para valores numéricos
for i = 1:length(Data.Category)
    for j = 1:length(categories)
        if strcmp(Data.Category{i}, categories{j})
            Data.Category{i} = categoryValues(j);
            break;
        end
    end
end

% Converter o atributo Sex
for i = 1:length(Data.Sex)
    if strcmp(Data.Sex{i}, "m")
        Data.Sex{i} = 0;
    elseif strcmp(Data.Sex{i}, "f")
        Data.Sex{i} = 1;
    end
end

Data.Sex = cell2mat(Data.Sex); % Converter para tipo numérico

% Identificar o atributo que corresponderá à saída desejada (target) da rede neuronal
targetAttribute = 'Category';

% Identificar colunas para comparação
columnsToCompare = {'Age','Sex','ALB', 'ALP', 'ALT', 'AST', 'BIL', 'CHE', 'CHOL', 'CREA', 'GGT', 'PROT'};

% Substituir valores 'NA' por NaN
for i = 1:numel(columnsToCompare)
    Data.(columnsToCompare{i})(strcmp(Data.(columnsToCompare{i}), 'NA')) = NaN;
end

% Normalização por min-max para as colunas numéricas
for i = 1:numel(columnsToCompare)
    column = Data.(columnsToCompare{i});
    minValue = min(column);
    maxValue = max(column);
    Data.(columnsToCompare{i}) = round((column - minValue) / (maxValue - minValue), 4); % Normalização com 4 dígitos decimais
end

% Ajuste de dígitos decimais específicos para a coluna 'Age'
Data.Age = round(Data.Age, 2);

% Identificar índices onde a categoria é 'NA'
naIndices = strcmp(Data.(targetAttribute), 'NA');

Data_converted = Data;

% Para cada linha onde a categoria é 'NA'
for i = 1:numel(naIndices)
    if naIndices(i)
        % Calcular a distância euclidiana entre as linhas com e sem 'NA'
        weights = [0.5, 0.25, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7]; % Pesos para cada atributo
        weighted_differences = (Data{~naIndices, columnsToCompare} - Data{i, columnsToCompare}) .* weights;
        distances = sqrt(sum(weighted_differences.^2, 2));

        % Encontrar a linha mais próxima (exceto ID)
        [~, closestIndex] = min(distances);

        % Substituir os valores 'NA' pelos valores da linha mais próxima, exceto para 'Category'
        Data{i, columnsToCompare} = Data{closestIndex, columnsToCompare};

        % Substituir o valor 'NA' na coluna 'Category' pelo valor correspondente na linha mais próxima
        Data.(targetAttribute){i} = Data.(targetAttribute){closestIndex};
    end
end

% Salvar os dados convertidos
writetable(Data_converted, 'Train_Converted.csv');

% Salvar tabela com todos os dados
writetable(Data, 'Train_Converted_Filled.csv');
end
