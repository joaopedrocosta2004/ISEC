<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml"/>
	<xsl:template match="lista">
		<listagem>
		<xsl:apply-templates select="escritor">
			<xsl:sort select="@nome"/>
		</xsl:apply-templates>
		</listagem>
	</xsl:template>
	<xsl:template match="escritor">
		<escritor nome='{@nome}'><xsl:value-of select="obtem_nome_completo"/></escritor>
	</xsl:template>
</xsl:stylesheet>


