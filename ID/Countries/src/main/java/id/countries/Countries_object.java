/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.countries;

/**
 *
 * @author joaos
 */
public class Countries_object {
    String ISO, nome, continente, nome_presidente, imagem;

    public Countries_object(String ISO, String nome, String continente, String nome_presidente, String imagem) {
        this.ISO = ISO;
        this.nome = nome;
        this.continente = continente;
        this.nome_presidente = nome_presidente;
        this.imagem = imagem;
    }

    public String getISO() {
        return ISO;
    }

    public void setISO(String ISO) {
        this.ISO = ISO;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public String getNome_presidente() {
        return nome_presidente;
    }

    public void setNome_presidente(String nome_presidente) {
        this.nome_presidente = nome_presidente;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    
    
}
