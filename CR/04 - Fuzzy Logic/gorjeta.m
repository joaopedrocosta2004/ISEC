function [fis] = gorjeta()

%PASSO 1 criar sistema fis
fis = mamfis;

%PASSO 2 VARIÁVEIS
fis = addInput(fis,[0,10],'Name',"servico");
fis = addInput(fis,[0,10], 'Name', "comida");
fis = addInput(fis,[0,10], 'Name', "tempo_de_espera");
fis = addOutput(fis,[0,30], 'Name', "gorjeta");
       

%PASSO 3 FUNÇÕES DE PERTENÇA
fis = addMF(fis,"servico", "gaussmf",[1.5 0],'Name', "fraco");
fis = addMF(fis,"servico", "gaussmf",[1.5 5], 'Name', "bom");
fis = addMF(fis,"servico", "gaussmf",[1.5 10], 'Name', "excelente");

fis = addMF(fis,"comida", "trapmf",[0 0 1 3], 'Name', "ma");
fis = addMF(fis,"comida", "trapmf",[7 9 11 19], 'Name', "deliciosa");

fis = addMF(fis,"gorjeta", "trimf",[0 5 10], 'Name', "fraca");
fis = addMF(fis,"gorjeta", "trimf",[10 15 20], 'Name', "media");
fis = addMF(fis, "gorjeta", "trimf",[20 25 30], 'Name', "generosa");

fis = addMF(fis,"tempo_de_espera", "trimf",[0 10 15], 'Name', "fraco");
fis = addMF(fis, "tempo_de_espera", "trimf",[15 20 25], 'Name', "medio");
fis = addMF(fis, "tempo_de_espera", "trimf",[25 30 35], 'Name', "bom");

    %PASSO 4 REGRAS
regra1 = "servico==fraco | comida==ma | tempo_de_espera==fraco => gorjeta=fraca";
regra2 = "servico==bom | tempo_de_espera==medio => gorjeta=media";
regra3 = "servico==excelente | comida==deliciosa | tempo_de_espera==bom => gorjeta=generosa";
regras=[regra1 regra2 regra3];
fis = addRule(fis,regras);

%PASSO 5: avaliar para vários valores de servico e comida com evalfis
for servico=0:10
        for comida=0:10
          for tempo_de_espera=0:10
            entrada=[servico comida tempo_de_espera];
            out = evalfis(fis, entrada);
            fprintf('serviço = %d\nComida = %d\nTempo de Espera = %d\nGorjeta = %f\n\n',servico, comida, tempo_de_espera, out);
          end
      end
 end
end