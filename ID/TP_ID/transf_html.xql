xquery version "1.0";

(: Generated with EditiX XML Editor (http://www.editix.com) at Sun Jun 12 11:57:36 BST 2022 :)


<html>
    <body>

        <h1>Fotos de autores</h1>
        <table style="width: 69%;" border="1" cellspacing="0" cellpadding="0" width="200" align="center">
            <tr>
                <th>	Autor</th>
                <th>Foto</th>
            </tr>
        {
            for $x in distinct-values(doc("C:\Users\Pedro\Downloads\TP_ID\TP_ID\escritores.xml")//autor/@nome)
            let $foto := distinct-values(doc("C:\Users\Pedro\Downloads\TP_ID\TP_ID\escritores.xml")//autor[@nome = $x]/dados/foto/text())
            return <tr>
                <td>
                    {$x}
                </td>
                <td>
 				 <img src="{$foto}"/>
                </td>
            </tr>
        }
        </table>

    </body>
</html>
