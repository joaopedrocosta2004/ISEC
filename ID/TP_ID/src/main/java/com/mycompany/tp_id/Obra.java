package com.mycompany.tp_id;

public class Obra {

    private String idAutor;
    private String ISBN;
    private String nomeAutor;
    private String titulo;
    private String editora;
    private String preco;
    private String linkCapa;


    public Obra(String idAutor, String ISBN, String nomeAutor, String titulo, String editora, String preco, String linkCapa) {
        this.ISBN = ISBN;
        this.idAutor = idAutor;
        this.nomeAutor = nomeAutor;
        this.titulo = titulo;
        this.editora = editora;
        this.preco = preco;
        this.linkCapa = linkCapa;

    }



    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(String idAutor) {
        this.idAutor = idAutor;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getLinkCapa() {
        return linkCapa;
    }

    public void setLinkCapa(String linkCapa) {
        this.linkCapa = linkCapa;
    }
}
