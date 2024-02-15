--Exercicio 2
Create table temp(col1 number(10),col2 number(20),message varchar2(100));

--Exercicio 3
DECLARE
    nova_col1 NUMBER;
    nova_col2 NUMBER;
    nova_message VARCHAR2(50);
BEGIN
    FOR i IN 1..100 LOOP
        nova_col1 := i;
        nova_col2 := i * 100;
        IF MOD(i, 2) = 0 THEN
            nova_message := 'Col1 é par';
        ELSE
            nova_message := 'Col1 é ímpar';
        END IF;
        
        INSERT INTO temp (COL1, COL2, MESSAGE) VALUES (nova_col1, nova_col2, nova_message);
    END LOOP; 
    COMMIT;
END;
/

--Exercicio 4
DECLARE
  cod_livro NUMBER := &codigo_a_testar;
  cod_user AUTORES.CODIGO_AUTOR%TYPE;
BEGIN
  INSERT INTO AUTORES(CODIGO_AUTOR, NOME, N_CONTRIBUINTE, MORADA, SEXO, NACIONALIDADE, GENERO_PREFERIDO)
  VALUES (80, 'Luis Moreno Campos', 23432432, 'Lisboa', 'M', 'Portuguesa', 'Informatica');

  SELECT LIVROS.CODIGO_AUTOR INTO cod_user FROM LIVROS WHERE cod_livro = CODIGO_LIVRO;
  IF cod_user = 17 THEN 
      UPDATE LIVROS SET CODIGO_AUTOR = 80 WHERE cod_livro = CODIGO_LIVRO ;
      COMMIT;
  END IF;
END;
/

--Exercicio 5
DECLARE
  cod_livro NUMBER := &codigo_a_testar;
  cod_user AUTORES.CODIGO_AUTOR%TYPE;
  verifica AUTORES.CODIGO_AUTOR%TYPE := 0;
BEGIN
  SELECT COUNT(CODIGO_AUTOR) INTO verifica FROM AUTORES WHERE CODIGO_AUTOR = 80;
  IF verifica = 0 THEN
    INSERT INTO AUTORES(CODIGO_AUTOR, NOME, N_CONTRIBUINTE, MORADA, SEXO, NACIONALIDADE, GENERO_PREFERIDO)
    VALUES (80, 'Luis Moreno Campos', 23432432, 'Lisboa', 'M', 'Portuguesa', 'Informatica');
  END IF;
  SELECT LIVROS.CODIGO_AUTOR INTO cod_user FROM LIVROS WHERE cod_livro = CODIGO_LIVRO;
  IF cod_user = 17 THEN 
      UPDATE LIVROS SET CODIGO_AUTOR = 80 WHERE cod_livro = CODIGO_LIVRO ;
      COMMIT;
  END IF;
END;

--Exercicio 6

DECLARE
  cod_livro NUMBER := &codigo_a_testar;
  cod_user AUTORES.CODIGO_AUTOR%TYPE;
  verifica AUTORES.CODIGO_AUTOR%TYPE := 0;
BEGIN
  SELCET COUNT(LIVROS.CODIGO_LIVRO) INTO verifica FROM LIVROS WHERE CODIGO_LIVRO = cod_livro;
  IF verifica > 0 THEN
     SELECT LIVROS.CODIGO_AUTOR INTO cod_user FROM LIVROS WHERE cod_livro = CODIGO_LIVRO;
        SELECT COUNT(CODIGO_AUTOR) INTO verifica FROM AUTORES WHERE CODIGO_AUTOR = 80;
    IF verifica = 0 THEN
      INSERT INTO AUTORES(CODIGO_AUTOR, NOME, N_CONTRIBUINTE, MORADA, SEXO, NACIONALIDADE, GENERO_PREFERIDO)
      VALUES (80, 'Luis Moreno Campos', 23432432, 'Lisboa', 'M', 'Portuguesa', 'Informatica');
    END IF;
   
    IF cod_user = 17 THEN 
        UPDATE LIVROS SET CODIGO_AUTOR = 80 WHERE cod_livro = CODIGO_LIVRO ;
        COMMIT;
    END IF;
  END IF;
END;