/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.projeto_id;

import java.util.ArrayList;

/**
 *
 * @author joaos
 */
public class Escritor {
    String nome, nome_completo, nacionalidade, data_nascimento, foto;
    ArrayList<String> ocupacoes;

    public Escritor(String nome, String nome_completo, String nacionalidade, String data_nascimento, String foto, ArrayList<String> ocupacoes) {
        this.nome = nome;
        this.nome_completo = nome_completo;
        this.nacionalidade = nacionalidade;
        this.data_nascimento = data_nascimento;
        this.foto = foto;
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

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public ArrayList<String> getOcupacoes() {
        return ocupacoes;
    }

    public void setOcupacoes(ArrayList<String> ocupacoes) {
        this.ocupacoes = ocupacoes;
    }
    
    public void imprime() {
        System.out.println("Nome: " + this.nome);
        System.out.println("Nome Completo: " + this.nome_completo);
        System.out.println("Nacionalidade: " + this.nacionalidade);
        System.out.println("Data de Nascimento: " + this.data_nascimento);
        System.out.println("Foto: " + this.foto);
        System.out.println("Ocupacoes: " + this.ocupacoes);
    }
}
