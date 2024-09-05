package com.mycompany.tp_id;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Autor {
    private String IdAutor;
    private String nomedoAutor;
    private String MorteAutor;
    private String NascimentoAutor;
    private String FotoAutor;
    private String NacionalidadeAutor;
    private String MovimentoAutor;
    private String OcupacaoAutor;


    public Autor(String IdAutor, String nomedoAutor, String MorteAutor, String NascimentoAutor, String FotoAutor, String NacionalidadeAutor, String MovimentoAutor, String OcupacaoAutor) {
        this.IdAutor = IdAutor;
        this.nomedoAutor = nomedoAutor;
        this.MorteAutor = MorteAutor;
        this.NascimentoAutor = NascimentoAutor;
        this.FotoAutor = FotoAutor;
        this.NacionalidadeAutor = NacionalidadeAutor;
        this.MovimentoAutor = MovimentoAutor;
        this.OcupacaoAutor = OcupacaoAutor;

    }
//private ArrayList obras;


    public String getNome() {
        return nomedoAutor;
    }

    public void setNome(String nome) {
        this.nomedoAutor = nomedoAutor;
    }

    public String getId_autor() {
        return IdAutor;
    }

    public void setId_autor(int id_autor) {
        this.IdAutor = IdAutor;
    }

    public String getNacionalidade() {
        return NacionalidadeAutor;
    }

    public void setNacionalidade(String nacionalidade) {
        this.NacionalidadeAutor = NacionalidadeAutor;
    }

    public String getDataNascimento() {
        return NascimentoAutor;
    }

    public void setDataNascimento(String dataNascimento) {
        this.NascimentoAutor = NascimentoAutor;
    }

    public String getDataMorte() {
        return MorteAutor;
    }

    public void setDataMorte(String dataMorte) {
        this.MorteAutor = MorteAutor;
    }

    public String getLinkFoto() {
        return FotoAutor;
    }

    public void setLinkFoto(String linkFoto) {
        this.FotoAutor = FotoAutor;
    }

    public String getGeneroLiterario() { return MovimentoAutor; }

    public void setGeneroLiterario(String generoLiterario) {
        this.MovimentoAutor = MovimentoAutor;
    }

    public String getOcupaçoes() {
        return OcupacaoAutor;
    }

    public void setOcupaçoes(ArrayList ocupaçoes) {
        this.OcupacaoAutor = OcupacaoAutor;
    }

}
