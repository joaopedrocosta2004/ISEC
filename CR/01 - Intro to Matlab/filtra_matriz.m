function [v] = filtra_matriz(m,t)
    switch t
        case 1
            v = [];
            for coluna = 1 : size(m,2)
                for linha = 1 : size(m,1)
                    value = m(linha,coluna);
                    if mod(value,2)==0
                        v = [v value]
                    end
                end
            end
        case 2
            posicoes = find( mod(m,2) == 0 );
            v = m(posicoes)';
    end
end