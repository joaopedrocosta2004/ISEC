/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.countries;

import java.util.ArrayList;


public class Countrie {
    String nome, ISO, continente, presidente_republica, imagem;
     
    public Countrie(String nome, String ISO, String continente, String presidente_republica, String imagem) {
        this.nome = nome;
        this.ISO = ISO;
        this.continente = continente;
        this.presidente_republica = presidente_republica;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getISO() {
        return ISO;
    }

    public void setISO(String ISO) {
        this.ISO = ISO;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }
    
    public String getPresidente_republica() {
        return presidente_republica;
    }

    public void setPresidente_republica(String presidente_republica) {
        this.presidente_republica = presidente_republica;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    
    public void imprime(){
        System.out.println("Nome do País: " + this.nome);
        System.out.println("Código ISO País: " + this.ISO);
        System.out.println("Continente: " + this.continente);
        System.out.println("Presidente da República: " + this.presidente_republica);
        System.out.println("Imagem: " + this.imagem);
    }
}
