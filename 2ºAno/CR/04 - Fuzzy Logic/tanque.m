function [fis] = tanque()

%PASSO 1 criar sistema fis
fis = mamfis;

%PASSO 2 VARIÁVEIS
fis = addInput(fis,[-1,1],'Name',"nivel");
fis = addInput(fis,[-0.4,0.4],'Name',"fluxo");
fis = addOutput(fis,[-1,1],'Name',"valvula");

%PASSO 3 FUNÇÕES DE PERTENÇA
    %NIVEL
fis = addMF(fis,"nivel", "gaussmf",[0.25 -1],'Name', "baixo");
fis = addMF(fis,"nivel", "gaussmf",[0.25 0],'Name', "bom");
fis = addMF(fis,"nivel", "gaussmf",[0.25 1],'Name', "alto");
    
    %FLUXO
fis = addMF(fis,"fluxo", "gaussmf",[0.025 -0.1],'Name', "negativo");
fis = addMF(fis,"fluxo", "gaussmf",[0.025 0],'Name', "nulo");
fis = addMF(fis,"fluxo", "gaussmf",[0.025 0.1],'Name', "positivo");

    %VALVULA
fis = addMF(fis,"valvula", "trimf",[-1 -0.9 -0.8],'Name', "fecha-rapido");
fis = addMF(fis,"valvula", "trimf",[-0.6 -0.5 -0.4],'Name', "fecha-devagar");
fis = addMF(fis,"valvula", "trimf",[-0.1 0 0.1],'Name', "nada");
fis = addMF(fis,"valvula", "trimf",[0.2 0.3 0.4],'Name', "abre-devagar");
fis = addMF(fis,"valvula", "trimf",[0.8 0.9 1],'Name', "abre-rapido");

%PASSO 4 REGRAS
regra1 = "nivel==bom => valvula=nada";
regra2 = "nivel==baixo => valvula=abre-rapido";
regra3 = "nivel==alto => valvula=fecha-rapido";
regra4 = "nivel==bom & fluxo==positivo => valvula=feche-devagar";
regra5 = "nivel==bom & fluxo==diminuindo => valvula=abre-devagar";
regras=[regra1 regra2 regra3];
fis = addRule(fis,regras);

%PASSO 5: avaliar para vários valores de servico e comida com evalfis
for nivel=-1:1
    for fluxo=-0.4:0.4
        entrada=[nivel fluxo];
        out = evalfis(fis, entrada);
        fprintf('nivel = %d\nfluxo = %d\nValvula = %f\n\n',nivel, fluxo, out);
    end
end
end