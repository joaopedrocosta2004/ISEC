<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" indent="no"/>

    <xsl:template match="/">
        <xsl:text>Listagem de Países:</xsl:text>
        <xsl:text>&#10;</xsl:text> <!-- quebra de linha -->

        <xsl:apply-templates select="//facto"/>
    </xsl:template>

    <xsl:template match="facto">
        <xsl:value-of select="@nome"/>
        <xsl:text>&#10;</xsl:text> <!-- quebra de linha -->
    </xsl:template>
</xsl:stylesheet>
