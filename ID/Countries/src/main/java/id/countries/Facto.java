/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.countries;

import java.util.ArrayList;


public class Facto {
    String nome, ISO, capital, continente, moeda, populacao, crescimento_populacao, area, presidente_republica, dominio_internet, imagem;
    ArrayList<String> idiomas_oficiais, paises_vizinhos, cidades_populosas;
     
    public Facto(String nome, String ISO, String capital, String continente, String moeda, String populacao, String crescimento_populacao, String area, ArrayList<String> cidades_populosas, ArrayList<String> idiomas_oficiais, ArrayList<String> paises_vizinhos, String presidente_republica, String dominio_internet, String imagem) {
        this.nome = nome;
        this.ISO = ISO;
        this.capital = capital;
        this.continente = continente;
        this.moeda = moeda;
        this.populacao = populacao;
        this.crescimento_populacao = crescimento_populacao;
        this.area = area;
        this.cidades_populosas = cidades_populosas;
        this.idiomas_oficiais = idiomas_oficiais;
        this.paises_vizinhos = paises_vizinhos;
        this.presidente_republica = presidente_republica;
        this.dominio_internet = dominio_internet;
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

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getPopulacao() {
        return populacao;
    }

    public void setPopulacao(String populacao) {
        this.populacao = populacao;
    }

    public String getCrescimento_populacao() {
        return crescimento_populacao;
    }

    public void setCrescimento_populacao(String crescimento_populacao) {
        this.crescimento_populacao = crescimento_populacao;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public ArrayList<String> getCidades_populosas() {
        return cidades_populosas;
    }

    public void setCidades_populosas(ArrayList<String> cidades_populosas) {
        this.cidades_populosas = cidades_populosas;
    }

    public String getPresidente_republica() {
        return presidente_republica;
    }

    public void setPresidente_republica(String presidente_republica) {
        this.presidente_republica = presidente_republica;
    }

    public String getDominio_internet() {
        return dominio_internet;
    }

    public void setDominio_internet(String dominio_internet) {
        this.dominio_internet = dominio_internet;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public ArrayList<String> getIdiomas_oficiais() {
        return idiomas_oficiais;
    }

    public void setIdiomas_oficiais(ArrayList<String> idiomas_oficiais) {
        this.idiomas_oficiais = idiomas_oficiais;
    }

    public ArrayList<String> getPaises_vizinhos() {
        return paises_vizinhos;
    }

    public void setPaises_vizinhos(ArrayList<String> paises_vizinhos) {
        this.paises_vizinhos = paises_vizinhos;
    }
    
    public void imprime(){
        System.out.println("Nome do País: " + this.nome);
        System.out.println("Código ISO País: " + this.ISO);
        System.out.println("Capital: " + this.capital);
        System.out.println("Continente: " + this.continente);
        System.out.println("Moeda: " + this.moeda);
        System.out.println("População: " + this.populacao);
        System.out.println("Crescimento da População: " + this.crescimento_populacao);
        System.out.println("Área: " + this.area);
        System.out.println("Cidades mais Populosas: " + this.cidades_populosas);
        System.out.println("Idiomas Oficiais: " + this.idiomas_oficiais);
        System.out.println("Países Vizinhos: " + this.paises_vizinhos);
        System.out.println("Presidente da República: " + this.presidente_republica);
        System.out.println("Domínio internet: " + this.dominio_internet);
        System.out.println("Imagem: " + this.imagem);
    }
}
