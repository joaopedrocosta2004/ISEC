/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp_id;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Wrapper {

    public static String autor_nome(String nomeAutor) throws IOException {

        String link = "https://pt.wikipedia.org/wiki/";
        nomeAutor = nomeAutor.replace(" ", "_");
        HttpRequestFunctions.httpRequest1(link, nomeAutor, "wiki.html");

        Scanner ler;
        ler = new Scanner(Files.newInputStream(Path.of("wiki.html")));

        String er = "<h1 id=\"firstHeading\" class=\"firstHeading mw-first-heading\"><span class=\"mw-page-title-main\">([^<]+)</span></h1>";

        Pattern p = Pattern.compile(er);
        Matcher m;
        String linha;

        while(ler.hasNextLine()) {
            linha = ler.nextLine();
            m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }

        return "Não definido";
    }

    public static String autor_dataMorte(String nomeAutor) throws IOException {

        String link = "https://pt.wikipedia.org/wiki/";
        nomeAutor = nomeAutor.replace(" ", "_");
        HttpRequestFunctions.httpRequest1(link, nomeAutor, "wiki.html");

        //String conteudo = new String(Files.readAllBytes(Path.of("wiki.html")));

        Scanner ler;
        ler = new Scanner(Files.newInputStream(Path.of("wiki.html")));

        //String er = "<span style=\\\"white-space:nowrap;\\\"><a href=\\\"/wiki/\\\\d+_de_\\\\w+\\\" title=\\\"\\\\d+ de \\\\w+\\\">(\\\\d+ de \\\\w+)<\\\\/a> de <a href=\\\"/wiki/(\\\\d+)\\\" title=\\\"\\\\d+\\\">\\\\d+<\\\\/a>&nbsp;\\\\(\\\\d+&nbsp;anos\\\\)<\\\\/span>";

        //String er1 = "<span style=\"white-space:nowrap;\"><a href=\"/wiki/[^>]+>([^<]+)<\\/a> de <a href=\"/wiki/\\d+\"[^>]+>\\d+<\\/a>";

        //String er2 = "<a href=\"\\/wiki\\/[^\"]+\" title=\"([^\"]+)\">([^<]+)<\\/a>\\s+de\\s+<a href=\"\\/wiki\\/[^\"]+\" title=\"(\\d{4})\">";

        //String er3 = "<span style=\"white-space:nowrap;\"><a href=\"\\/wiki\\/[^\"]+\" title=\"([^\"]+)\">([^<]+)<\\/a>\\s+de\\s+<a href=\"\\/wiki\\/[^\"]+\" title=\"\\d+\">(\\d{4})";

        String er4 = "<span style=\"white-space:nowrap;\"><a href=\"\\/wiki\\/[^\\\"]+\" title=\"[^\"]+\">(\\d{1,2}) de ([^<]+)<\\/a> de <a href=\"\\/wiki\\/[^\\\"]+\" title=\"(\\d{4})\">\\d+";


        Pattern p = Pattern.compile(er4);
        Matcher m;
        String linha;

        while(ler.hasNextLine()) {
            linha = ler.nextLine();
            m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1) + " " + m.group(2) + " " + m.group(3);
            }
        }

        return "Não definido";

    }


    public static String autor_dataNascimento(String nomeAutor) throws IOException {

        String link = "https://pt.wikipedia.org/wiki/";
        nomeAutor = nomeAutor.replace(" ", "_");
        HttpRequestFunctions.httpRequest1(link, nomeAutor, "wiki.html");

        Scanner ler;
        ler = new Scanner(Files.newInputStream(Path.of("wiki.html")));

        String er = "<td style=\"vertical-align: top; text-align: left;\"><a href=\"\\/wiki\\/[^\\\"]+\" title=\"[^<]+\">(\\d{1,2}) de ([^<]+)<\\/a> de <a href=\"\\/wiki\\/[^\\\"]+\" title=\"(\\d{4})";

        Pattern p = Pattern.compile(er);
        Matcher m;
        String linha;

        while(ler.hasNextLine()) {
            linha = ler.nextLine();
            m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1) + " " + m.group(2) + " " + m.group(3);
            }
        }
        return "Não definido";
    }

    public static String obtem_link_foto(String nomeAutor) throws IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        nomeAutor = nomeAutor.replace(" ", "_");
        HttpRequestFunctions.httpRequest1(link, nomeAutor, "wiki.html");

        Scanner ler;
        ler = new Scanner(Files.newInputStream(Path.of("wiki.html")));


        String er1 = "<div class=\"floatnone\">(.*?)</div>";

        //String er2 = "\"<img[^>]*src=\\\"([^\\\"]+)\\\"[^>]*>\"";

        Pattern p = Pattern.compile(er1);
        Matcher m;
        String linha;

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m = p.matcher(linha);
            if (m.find()) {
                String conteudoDiv = m.group(1);

                String regex = "<img[^>]*src=\"([^\"]+)\"[^>]*>";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(conteudoDiv);

                while (matcher.find()) {
                    return matcher.group(1);
                }

                break;
            }
        }

        ler.close();

        return "não definido";

    }

    public static String obtemNacionalidade(String nomeAutor) throws IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        nomeAutor = nomeAutor.replace(" ", "_");
        HttpRequestFunctions.httpRequest1(link, nomeAutor, "wiki.html");

        Scanner ler;
        ler = new Scanner(Files.newInputStream(Path.of("wiki.html")));

        String er = "Nacionalidade[^h]+[^>]+>([^<]+)<";

        Pattern p = Pattern.compile(er);
        String linha;
        String texto= null;
        while(ler.hasNextLine())
        {
            linha = ler.nextLine();
            texto = texto + linha;
            Matcher m = p.matcher(texto);

            while(m.find())
            {

                return (m.group(1));
            }

        }
        ler.close();
        return null;
    }

    public static String obtemMovimento(String nomeAutor) throws IOException {
        HttpRequestFunctions.httpRequest1("https://pt.wikipedia.org/wiki/",nomeAutor,"wiki.html");

        //String er = "Src="//upload.wikimedia.org/wikipedia/commons/thumb/[0-9]+/[^\"]+"";
        //href="/wiki/Ficheiro:AlexandreHerculano.png"
        String er = "title=\"Movimento artístico\">Movimento</a>[^>]+>[^h]+[^>]+>([^<]+)<";
        Scanner ler = new Scanner(new FileInputStream("wiki.html"));
        Pattern p = Pattern.compile(er);
        String linha;
        String texto = null;
        while(ler.hasNextLine())
        {
            linha = ler.nextLine();

            texto = texto + linha;
            Matcher m = p.matcher(texto);

            while(m.find())
            {

                return (m.group(1));
            }

        }
        ler.close();
        return "Não definido";

    }

    public static String obtemOcupaçao(String nomeAutor) throws IOException {
        HttpRequestFunctions.httpRequest1("https://pt.wikipedia.org/wiki/",nomeAutor,"wiki.html");

        String er = "<[^>]*>Ocupação\\s*</td>\\s*<[^>]*>\\s*<a href=\"/wiki/([^\"]+)\"";


        Scanner ler = new Scanner(new FileInputStream("wiki.html"));
        Pattern p = Pattern.compile(er);

        String linha;
        String texto = null;

        while(ler.hasNextLine())
        {
            linha = ler.nextLine();
            texto = texto + linha;

            Matcher m = p.matcher(texto);

            while(m.find())
            {
                ler.close();
                return m.group(1);
            }

        }
        ler.close();
        return null;

    }

    public static String obtemCodigoAutor(String nomeAutor) throws IOException {
        HttpRequestFunctions.httpRequest1("https://www.bertrand.pt/pesquisatab/autores/",nomeAutor,"wiki.html");
        String autorlink = nomeAutor;
        autorlink = autorlink.toLowerCase();
        autorlink = autorlink.replace(" ", "-");
        String er = "href=\"/autor/"+autorlink+"/([0-9]+)\">";
        Scanner ler = new Scanner(new FileInputStream("wiki.html"));
        Pattern p = Pattern.compile(er);
        String linha;

        while(ler.hasNextLine())
        {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);

            if(m.find())
            {
                ler.close();
                return m.group(1);
            }
        }
        ler.close();
        return null;
    }

    public static String obtemLinkAutor(String nomeAutor) throws IOException {
        HttpRequestFunctions.httpRequest1("https://www.bertrand.pt/pesquisatab/autores/",nomeAutor,"bertrand.html");
        String autorlink = nomeAutor;
        autorlink = autorlink.toLowerCase();
        autorlink = autorlink.replace(" ", "-");
        String er = "href=\"/(autor/"+autorlink+"/[0-9]+)\">";
        Scanner ler = new Scanner(new FileInputStream("bertrand.html"));
        Pattern p = Pattern.compile(er);
        String linha;

        while(ler.hasNextLine())
        {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);

            if(m.find())
            {
                ler.close();
                return "https://www.bertrand.pt/" +m.group(1);
            }
        }
        ler.close();
        return null;

    }

    public static String obtemLinkLivro(String nome) throws IOException {
        String link = obtemLinkAutor(nome);
        HttpRequestFunctions.httpRequest1(link,"","livro.html");

        String er = "href=\"(/livro/[^\"]+)\"";
        Scanner ler = new Scanner(new FileInputStream("livro.html"));
        Pattern p = Pattern.compile(er);
        String linha;
        String texto = null;
        while(ler.hasNextLine())
        {
            linha = ler.nextLine();

            texto = texto + linha;
            Matcher m = p.matcher(texto);

            while(m.find())
            {

                return ("https://www.bertrand.pt"+m.group(1));
            }

        }
        ler.close();
        return "Não definido";

    }




    public static String obtemEditora(String nome) throws IOException {

        try {
            String link = obtemLinkLivro(nome);
            HttpRequestFunctions.httpRequest1(link, "", "livro.html");

            String er = "<div [^>]+>\\s*Editor:\\s*<[^>]+>([^<]+)</div>";
            Scanner ler = new Scanner(new FileInputStream("livro.html"));
            Pattern p = Pattern.compile(er);

            String linha;
            String texto = null;
            while (ler.hasNextLine()) {
                linha = ler.nextLine();
                texto = texto + linha;
                Matcher m = p.matcher(texto);

                while (m.find()) {
                    ler.close();
                    return m.group(1);
                }
            }
            ler.close();
        } catch (IOException e) {
            // Trate a exceção aqui ou imprima informações de depuração
            e.printStackTrace();
        }
        return null;
    }

    public static String obtemISBN (String nome) throws IOException {

            String link = obtemLinkLivro(nome);
            HttpRequestFunctions.httpRequest1(link, "", "livro.html");

        try (Scanner ler = new Scanner(new FileInputStream("livro.html"))) {
            String er = "<div [^>]+>\\s*ISBN:\\s*<[^>]+>([^<]+)</div>";
            Pattern p = Pattern.compile(er);
            Matcher m;
            StringBuilder htmlContent = new StringBuilder();

            while (ler.hasNextLine()) {
                htmlContent.append(ler.nextLine());
            }

            m = p.matcher(htmlContent.toString());
            if (m.find()) {
                return m.group(1);
            }
        }

        return "Não definido";


    }

    public static String obtemNomeAutor (String nome) throws IOException {
        String link = obtemLinkLivro(nome);
        HttpRequestFunctions.httpRequest1(link, "", "livro.html");

        try (Scanner ler = new Scanner(new FileInputStream("livro.html"))) {
            String er = "<a id=\"productPageRightSectionTop-author-lnk\" data-id=\"[0-9]+\">(.+?)</a>";
            Pattern p = Pattern.compile(er);
            Matcher m;
            StringBuilder htmlContent = new StringBuilder();

            while (ler.hasNextLine()) {
                htmlContent.append(ler.nextLine());
            }

            m = p.matcher(htmlContent.toString());
            if (m.find()) {
                return m.group(1);
            }
        }

        return "Não definido";
    }

    public static String obtemtituloLivro (String nome) throws IOException {
        String link = obtemLinkLivro(nome);
        HttpRequestFunctions.httpRequest1(link, "", "livro.html");

        try (Scanner ler = new Scanner(new FileInputStream("livro.html"))) {
            String er = "<h1 id=\"productPageRightSectionTop-title-h1\" class=\"col-lg-12 col-xs-12 col-md-12 col-sm-12 no-padding pull-left\">(.+?)</h1>";
            Pattern p = Pattern.compile(er);
            Matcher m;
            StringBuilder htmlContent = new StringBuilder();

            while (ler.hasNextLine()) {
                htmlContent.append(ler.nextLine());
            }

            m = p.matcher(htmlContent.toString());
            if (m.find()) {
                return m.group(1);
            }
        }

        return "Não definido";
    }

    public static String obtemPrecoLivro (String nome) throws IOException {
        String link = obtemLinkLivro(nome);
        HttpRequestFunctions.httpRequest1(link, "", "livro.html");

        try (Scanner ler = new Scanner(new FileInputStream("livro.html"))) {
            String er = "<div class=\"current\" id=\"productPageRightSectionTop-saleAction-price-current\">\\s*(.+?)\\s*</div>";
            Pattern p = Pattern.compile(er);
            Matcher m;
            StringBuilder htmlContent = new StringBuilder();

            while (ler.hasNextLine()) {
                htmlContent.append(ler.nextLine());
            }

            m = p.matcher(htmlContent.toString());
            if (m.find()) {
                return m.group(1);
            }
        }

        return "Não definido";
    }

    public static String obtemEditor(String nome) throws IOException {
        String link = obtemLinkLivro(nome);
        HttpRequestFunctions.httpRequest1(link, "", "livro.html");

        try (Scanner ler = new Scanner(new FileInputStream("livro.html"))) {
            String er = "<div [^>]+>\\s*Editor:\\s*<[^>]+>([^<]+)</div>";
            Pattern p = Pattern.compile(er);
            Matcher m;
            StringBuilder htmlContent = new StringBuilder();

            while (ler.hasNextLine()) {
                htmlContent.append(ler.nextLine());
            }

            m = p.matcher(htmlContent.toString());
            if (m.find()) {
                return m.group(1);
            }
        }

        return "Não definido";
    }

    public static String obtemimagemLivro(String nome) throws IOException {
        try (Scanner ler = new Scanner(new FileInputStream("livro.html"))) {
            String er = "<img[^>]*src=\"([^\"]+)\"[^>]*class=\"produto_imagem\"";
            Pattern p = Pattern.compile(er);
            Matcher m;
            StringBuilder htmlContent = new StringBuilder();

            while (ler.hasNextLine()) {
                htmlContent.append(ler.nextLine());
            }

            m = p.matcher(htmlContent.toString());
            if (m.find()) {
                return m.group(1);
            }
        }

        return "Não definido";
    }

    public static Autor criaAutor(String nomeAutor) throws IOException {

        String IdAutor = Wrapper.obtemCodigoAutor(nomeAutor);
        String nomedoAutor = Wrapper.autor_nome(nomeAutor);
        String MorteAutor = Wrapper.autor_dataMorte(nomeAutor);
        String NascimentoAutor = Wrapper.autor_dataNascimento(nomeAutor);
        String FotoAutor = Wrapper.obtem_link_foto(nomeAutor);
        String NacionalidadeAutor = Wrapper.obtemNacionalidade(nomeAutor);
        String MovimentoAutor = Wrapper.obtemMovimento(nomeAutor);
        String OcupacaoAutor = Wrapper.obtemOcupaçao(nomeAutor);



        Autor c = new Autor( IdAutor, nomedoAutor, MorteAutor, NascimentoAutor, FotoAutor, NacionalidadeAutor, MovimentoAutor, OcupacaoAutor);
        return c;
    }

    public static Obra criaObra(String nomeAutor) throws IOException {

        String idAutor = Wrapper.obtemCodigoAutor(nomeAutor);
        String ISBN = Wrapper.obtemISBN(nomeAutor);
        String NomeAutor = Wrapper.obtemNomeAutor(nomeAutor);
        String titulo = Wrapper.obtemtituloLivro(nomeAutor);
        String editora = Wrapper.obtemEditor(nomeAutor);
        String preco = Wrapper.obtemPrecoLivro(nomeAutor);
        String linkCapa = Wrapper.obtemimagemLivro(nomeAutor);



        Obra d = new Obra( idAutor,  ISBN,  NomeAutor,  titulo,  editora,  preco,  linkCapa);
        return d;
    }
}

