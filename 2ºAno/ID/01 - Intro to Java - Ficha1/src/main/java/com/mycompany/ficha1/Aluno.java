/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ficha1;

/**
 *
 * @author joaos
 */
public class Aluno {
    // Exercicio 3.1, 3.2, 3.3
    String nome;
    String local;
    String genero;
    int idade;

    public Aluno(String nome, String local, String genero, int idade) {
        this.nome = nome;
        this.local = local;
        this.genero = genero;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    public void imprime() {
        System.out.println("Dados do aluno: ");
        System.out.println("\tNome: "+ this.nome);
        System.out.println("\tLocal: "+ this.local);
        System.out.println("\tGénero: "+ this.genero);
        System.out.println("\tIdade: "+ this.idade);
    }
    
}

