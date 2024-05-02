<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:cv24="http://univ.fr/cv24">

    <xsl:output method="xml"/>
    <xsl:template match="/">
        <xsl:element name="cv">
            <xsl:call-template name="identite"/>
            <xsl:call-template name="objectif"/>
            <xsl:call-template name="diplome"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="identite">
        <xsl:element name="identite">
            <xsl:call-template name="name"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="name">
        <xsl:element name="genre">
            <xsl:value-of select="/cv24:cv24/cv24:identite/cv24:genre"/>
        </xsl:element>
        <xsl:element name="nom">
            <xsl:value-of select="/cv24:cv24/cv24:identite/cv24:nom"/>
        </xsl:element>
        <xsl:element name="prenom">
            <xsl:value-of select="/cv24:cv24/cv24:identite/cv24:prenom"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="objectif">
        <xsl:element name="objectif">
            <xsl:value-of select="/cv24:cv24/cv24:objectif/@statut"/>&#160;<xsl:value-of select="/cv24:cv24/cv24:objectif"/>
        </xsl:element>
    </xsl:template>


    <xsl:template name="diplome">
        <xsl:element name="diplome">
            <xsl:for-each select="/cv24:cv24/cv24:competences/cv24:diplome">
                <xsl:sort select="@niveau" data-type="number" order="descending"/>
                <xsl:sort select="cv24:date" order="descending"/>
                <xsl:if test="position() = 1">
                    <xsl:element name="titre">
                        <xsl:value-of select="cv24:titre"/>
                    </xsl:element>
                    <xsl:element name="date">
                        <xsl:value-of select="cv24:date"/>
                    </xsl:element>
                    <xsl:element name="niveau">
                        <xsl:value-of select="@niveau"/>
                    </xsl:element>
                    <xsl:element name="institut">
                        <xsl:value-of select="cv24:institut"/>
                    </xsl:element>
                </xsl:if>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>