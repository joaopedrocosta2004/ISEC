function maior = maior_num(t)
    switch t
        case 1

            maior = -inf;
            n = 1;
            while (n~=0)
                n=input('Digite um numero: ');
                if(n > maior)
                    maior = n;
                end
            end
    end