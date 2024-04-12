/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.projeto_id;

import java.util.ArrayList;

/**
 *
 * @author JoaoSilveira
 */
public class Escritor {
    String nome, nome_completo, nacionalidade, dnascimento, fotografia;
    ArrayList<String> ocupacoes;

    public Escritor(String nome, String nome_completo, String nacionalidade, String dnascimento, String fotografia, ArrayList<String> ocupacoes) {
        this.nome = nome;
        this.nome_completo = nome_completo;
        this.nacionalidade = nacionalidade;
        this.dnascimento = dnascimento;
        this.fotografia = fotografia;
        this.ocupacoes = ocupacoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome_completo() {
        return nome_completo;
    }

    public void setNome_completo(String nome_completo) {
        this.nome_completo = nome_completo;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getDnascimento() {
        return dnascimento;
    }

    public void setDnascimento(String dnascimento) {
        this.dnascimento = dnascimento;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public ArrayList<String> getOcupacoes() {
        return ocupacoes;
    }

    public void setOcupacoes(ArrayList<String> ocupacoes) {
        this.ocupacoes = ocupacoes;
    }
    
    public void imprime(){
        System.out.println("Nome: " + this.nome);
        System.out.println("Nome Completo: " + this.nome_completo);
        System.out.println("Nacionalidade: " + this.nacionalidade);
        System.out.println("Data de Nascimento: " + this.dnascimento);
        System.out.println("Fotografia: " + this.fotografia);
        System.out.println("Ocupações: " + this.ocupacoes);
    }
    
}
