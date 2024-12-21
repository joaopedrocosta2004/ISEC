-- Ficha 2

-- Exercicio 2
DECLARE
  CURSOR CAUTOR IS
    SELECT CODIGO_AUTOR, GENERO_PREFERIDO, 
            SUBSTR(NOME, INSTR (NOME, ' ', -1 ) + 1) NOME
    FROM AUTORES 
    WHERE CODIGO_AUTOR BETWEEN 8 AND 14;
    
    N_GEN NUMBER;
    N_LIVRO NUMBER;
    
BEGIN
  FOR  R IN CAUTOR
  LOOP
    SELECT COUNT(GENERO) INTO N_GEN FROM LIVROS WHERE R.GENERO_PREFERIDO = GENERO AND CODIGO_AUTOR = R.CODIGO_AUTOR;
    SELECT COUNT(CODIGO_LIVRO) INTO N_LIVRO FROM LIVROS WHERE CODIGO_AUTOR = R.CODIGO_AUTOR;
    
    INSERT INTO TEMP VALUES (N_LIVRO, N_GEN, R.NOME);
  END LOOP;
END;

-- Exercicio 3
declare 
  cursor clivros is
    select preco_tabela, paginas from livros where upper(genero) = 'AVENTURA';
    
    soma number;
    n number;
    media number;
    n_pre number;
    n_pag number;
begin
  for r in clivros
  loop
    if r.preco_tabela > 20 then
      n_pre := n_pre + 1;
    end if;
    
    if r.paginas > 400 then
      n_pag := n_pag + 1;
    end if;
    soma := soma + r.preco_tabela;
    n := n + 1;
  end loop;
  media := soma/n;
  insert into TEMP values (n_pre, n_pag, media);
end;

-- Exercicio 4
declare 
  cursor c1 (coda number) is
    select quant_em_stock, preco_tabela, titulo from livros where preco_tabela < coda;
    
begin 
  for r in c1 (40)
  loop
    insert into temp values (r.quant_em_stock, r.preco_tabela, r.titulo);
  end loop;
end;

-- Exercicio 5

declare
	cursor c1 is
		select codigo_livro,preco_tabela, genero
		from livros
		where genero in ('Policial','Romance') and preco_tabela<=60
		for update of preco_tabela;
begin
	for r in c1
    loop
      if r.preco_tabela <= 30 then
        update livros set preco_tabela=preco_tabela*1.08 
        where genero = r.genero;
      else
        update livros set preco_tabela=preco_tabela*1.05 
        where genero = r.genero;
      end if;
    end loop;
end;

-- Exercicio 6

declare
  cursor c1 is
    select codigo_livro, preco_tabela, titulo from livros order by preco_tabela desc;
begin
  for r in c1
    loop
      insert into temp values (r.codigo_livro, r.preco_tabela, r.titulo);
      exit when c1%rowcount >= 6;
    end loop;
end;

-- Exercicio 7

declare
  pre_total number;
  cursor c1 is 
    select codigo_livro, preco_tabela, genero from livros where upper(genero) = 'AVENTURA' order by preco_tabela asc;
begin
  for r in c1
  loop
    select sum(preco_tabela) into pre_total from livros where upper(genero) = r.genero;
    if pre_total <= 160 then
      update livros set preco_tabela=preco_tabela*1.15 
      where genero = r.genero and codigo_livro = r.codigo_livro;
    else
      exit;
    end if;
  end loop;
end;

-- Exercicio 8

declare 
  cursor c1 is
    select codigo_livro, codigo_autor, genero from livros where upper(genero) = 'AVENTURA';
  
  n_livros number;
  nome_autor autores.nome%TYPE;
begin
  for r in c1
  loop
    select count(*) into n_livros from livros where r.codigo_autor = codigo_autor and upper(genero) = 'AVENTURA';
    if n_livros > 0 then
      select reverse(nome) into nome_autor from autores where codigo_autor = r.codigo_autor;
      
      insert into temp values (r.codigo_autor, n_livros, nome_autor);
    end if;
  end loop;
end;