/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tp_id;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TP_ID {

    public static void main(String[] args) throws IOException {
      try {

            //Para pedir ao utilizador o nome:
            //Scanner scanner = new Scanner(System.in);
            //System.out.print("Digite o nome do autor: ");
            //String nomeAutor = scanner.nextLine();


             //String nomeAutor = "Fernando Pessoa";
            String nomeAutor = "James Joyce";
         //String nomeAutor = "José Saramago";

           String nome = Wrapper.autor_nome(nomeAutor);
            System.out.println("Nome do autor: " + nome);

            String dataMorte = Wrapper.autor_dataMorte(nomeAutor);
            System.out.println("Morte do autor: " + dataMorte);

            String dataNascimento = Wrapper.autor_dataNascimento(nomeAutor);
            System.out.println("Nascimento do autor: " + dataNascimento);

            String linkFoto = Wrapper.obtem_link_foto(nomeAutor);
            System.out.println("Link da foto do autor: " + linkFoto);

           // String nacionalidade = Wrapper.obtemNacionalidade(nomeAutor);
            //System.out.println("Nacionalidade do autor: " + nacionalidade);
/*
            String movimento = Wrapper.obtemMovimento(nomeAutor);
            System.out.println("Movimento do autor: " + movimento);

            String ocupacao = Wrapper.obtemOcupaçao(nomeAutor);
            System.out.println("ocupacao do autor: " + ocupacao);

            String codigo = Wrapper.obtemCodigoAutor(nomeAutor);
            System.out.println("codigo do autor: " + codigo);

            String linkAutor = Wrapper.obtemLinkAutor(nomeAutor);
            System.out.println("link do autor: " + linkAutor);

            String linkLivro = Wrapper.obtemLinkLivro(nomeAutor);
            System.out.println("link do livro: " + linkLivro);

            String editora = Wrapper.obtemEditora(nomeAutor);
            System.out.println("editora: " + editora);

            String ISBN = Wrapper.obtemISBN(nomeAutor);
            System.out.println("Isbn: " + ISBN);

            String nomedoAutor = Wrapper.obtemNomeAutor(nomeAutor);
            System.out.println("Escritor: " + nomedoAutor);

            String tituloLivro = Wrapper.obtemtituloLivro(nomeAutor);
            System.out.println("Titulo do Livro: " + tituloLivro);

            String precoLivro = Wrapper.obtemPrecoLivro(nomeAutor);
            System.out.println("Preco do Livro: " + precoLivro);

            String editorLivro = Wrapper.obtemEditor(nomeAutor);
            System.out.println("Editor do Livro: " + editorLivro);

            String imagemLivro = Wrapper.obtemimagemLivro(nomeAutor);
            System.out.println("Capa do Livro: " + imagemLivro);
*/
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }

}
