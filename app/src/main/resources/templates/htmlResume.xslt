<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html"/>

    <xsl:template match="/cvlist">
        <xsl:element name="html">
            <xsl:element name="head">
                <xsl:element name="title">CV24 Resume</xsl:element>
                <link href="/stylesheets/resume.css" rel="stylesheet"/>
            </xsl:element>
            <xsl:element name="body">
                <xsl:element name="h1">CV24 - Resume</xsl:element>
                <xsl:for-each select="/cvlist/resume">
                    <xsl:call-template name="cvResume"/>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <xsl:template name="cvResume">
        <xsl:element name="h1">
            <xsl:value-of select="id"/>
        </xsl:element>
        <xsl:call-template name="cv"/>
    </xsl:template>

    <xsl:template name="cv">
        <xsl:element name="div">
            <xsl:call-template name="identity"/>
            <xsl:call-template name="goal"/>
            <xsl:call-template name="diploma"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="identity">
        <xsl:element name="div">
            <xsl:element name="h2">Identité</xsl:element>
            <xsl:element name="p">
                <xsl:value-of select="cv/identite/nom"/>
            </xsl:element>
            <xsl:element name="p">
                <xsl:value-of select="cv/identite/prenom"/>
            </xsl:element>
            <xsl:element name="p">
                <xsl:value-of select="cv/identite/genre"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <xsl:template name="goal">
        <xsl:element name="div">
            <xsl:element name="h2">Objectif</xsl:element>
            <xsl:element name="p">Recherche de <xsl:value-of select="cv/objectif"/></xsl:element>
        </xsl:element>
    </xsl:template>

    <xsl:template name="diploma">
        <xsl:element name="div">
            <xsl:element name="h2">Diplome</xsl:element>
            <xsl:element name="p">
                <xsl:value-of select="cv/diplome/titre"/>
            </xsl:element>
            <xsl:element name="p">
                <xsl:value-of select="cv/diplome/date"/>
            </xsl:element>
            <xsl:element name="p">Niveau : <xsl:value-of select="cv/diplome/niveau"/></xsl:element>
            <xsl:element name="p">Institut : <xsl:value-of select="cv/diplome/institut"/></xsl:element>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>