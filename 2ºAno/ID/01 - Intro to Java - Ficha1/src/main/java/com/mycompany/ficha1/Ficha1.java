/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ficha1;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author joaos
 */
public class Ficha1 {

    static void minhafunc1() {
        // Funcao de experimentos
        // Exercicio 2.2
        System.out.println("Olá Mundo!");
        // Exercicio 2.3
        String nome = "Joana Melo";
        int idade = 24;
        System.out.println("O meu nome é " + nome + " e tenho " + idade + "anos.");
        System.out.printf("O meu nome é %s e tenho %d anos.%n", nome, idade);
        // Exercicio 2.4
        Scanner dados = new Scanner(System.in);
        int n;
        n = dados.nextInt();
        float f;
        f = dados.nextFloat();
        String s;
        s = dados.next(); // Para palavras simples
        s = dados.nextLine(); // Para palavras com espaços
    }

    static void pedeInfo() {
        // Exercicio 2.4 ultimo ponto
        Scanner dados = new Scanner(System.in);

        String nome;
        int idade;
        System.out.println("Introduza o seu nome: ");
        nome = dados.nextLine();

        System.out.println("Introduza a sua idade: ");
        idade = dados.nextInt();

        System.out.printf("O meu nome é %s e tenho %d anos.%n", nome, idade);
    }

    static void minhafunc2(int num) {
        // Exercicio 3.4
        ArrayList<Aluno> turma = new ArrayList();
        Aluno a;

        Scanner dados = new Scanner(System.in);
        for (int i = 0; i < num; i++) {
            System.out.println("");
            System.out.println("Nome: ");
            String nome = dados.nextLine();

            System.out.println("Local: ");
            String local = dados.nextLine();

            System.out.println("Genero: ");
            String genero = dados.nextLine();

            System.out.println("Idade: ");
            int idade = dados.nextInt();
            dados.nextLine(); // Limpar buffer

            a = new Aluno(nome, local, genero, idade);

            turma.add(a);
        }

        int somaF = 0;
        int somaM = 0;
        int contaF = 0;
        int contaM = 0;
            
        for (int i = 0; i < turma.size(); i++) {
            turma.get(i).imprime();

            if (turma.get(i).genero.equals("F")) {
                somaF += turma.get(i).idade;
                contaF++;
            } else if(turma.get(i).genero.equals("M")) {
                somaM += turma.get(i).idade;
                contaM++;
            }
        }
        
        System.out.println("Media Feminina: " + somaF/contaF);
        System.out.println("Media Masculina: " + somaM/contaM);
        

    }
    
    public static void lerFicheiros(String nomeF) throws FileNotFoundException, IOException {
        // Exercicio 4.1
        Scanner ler = new Scanner(new FileInputStream(nomeF));
        String linha;
        String []campos;
        BufferedWriter homens = new BufferedWriter(new FileWriter("alunosH.txt"));
        BufferedWriter mulheres = new BufferedWriter(new FileWriter("alunosM.txt"));
        
        while ((ler.hasNextLine())) {
            linha = ler.nextLine(); //lê linha a linha
            campos =  linha.split(";");
            if (campos[2].equals("F")){
                mulheres.write(campos[0] + "\n");
            } else if (campos[2].equals("M")) {
                homens.write(campos[0] + "\n");
            }
        }
        ler.close();
        homens.close();
        mulheres.close();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //minhafunc1();
        //pedeInfo();
        //minhafunc2(4);
        lerFicheiros("alunos.txt");
    }
}
