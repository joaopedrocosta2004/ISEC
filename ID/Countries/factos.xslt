<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="UTF-8"/>
  
  <xsl:template match="/">
    <html>
      <head>
        <title>Lista de Países</title>
        <style>
          table {
            border-collapse: collapse;
            width: 100%;
          }
          th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
          }
          th {
            background-color: #f2f2f2;
          }
          img {
            max-width: 100px;
            max-height: 100px;
          }
        </style>
      </head>
      <body>
        <h1>Lista de Países</h1>
        <table>
          <tr>
            <th>País</th>
            <th>Área Geográfica</th>
            <th>Bandeira</th>
          </tr>
          <xsl:apply-templates select="//facto"/>
        </table>
      </body>
    </html>
  </xsl:template>
  
  <xsl:template match="facto">
    <tr>
      <td><xsl:value-of select="@nome"/></td>
      <td><xsl:value-of select="area"/> km²</td>
      <td><img src="{imagem}" alt="Bandeira de {translate(@nome, ' ', '_')}"/></td>
    </tr>
  </xsl:template>
</xsl:stylesheet>
