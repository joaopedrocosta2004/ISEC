<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="/">
	<html>
		<body>
			<h1>Listagem de fotos</h1>
			<table border ="1">
				<tr><th>Autor</th><th>Foto</th></tr>
				<xsl:apply-templates select ="escritores/autor" />
					
			</table>

		</body>
	</html>
	</xsl:template>
	
	
	<xsl:template match="autor">
		<xsl:variable name="nome" select="@nome" />
        <xsl:variable name="foto" select="Dados/foto" />
        <tr>
            <td><xsl:value-of select="@nome" /></td>
            <td>
                <a href="{$foto}">
                    <img src="{$foto}" alt="{$nome}" width="200" />
                </a>
            </td>
        </tr>
	</xsl:template>

</xsl:stylesheet>


