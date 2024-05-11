<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:cv24="http://univ.fr/cv24">

    <xsl:output method="html"/>

    <xsl:template match="/cv24:cv24">
        <xsl:element name="html">
            <xsl:element name="head">
                <xsl:element name="title">CV24</xsl:element>
                <link href="/stylesheets/cv.css" rel="stylesheet"/>
            </xsl:element>
            <xsl:element name="body">
                <xsl:element name="h1">CV24 - XSLT V1.0</xsl:element>
                <xsl:element name="p">Le 14 février 2024</xsl:element>
                <xsl:call-template name="identite"/>
                <xsl:call-template name="objectif"/>
                <xsl:call-template name="experiences"/>
                <xsl:call-template name="diplome"/>
                <xsl:call-template name="certifications"/>
                <xsl:call-template name="language"/>
                <xsl:call-template name="autres"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <xsl:template name="identite">
        <xsl:element name="h2">
            <xsl:call-template name="name"/>
        </xsl:element>
        <xsl:element name="p">
            <xsl:call-template name="coordonnes"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="name">
        <xsl:value-of select="cv24:identite/cv24:genre"/>&#160;<xsl:value-of select="cv24:identite/cv24:nom"/>&#160;<xsl:value-of select="cv24:identite/cv24:prenom"/>
    </xsl:template>

    <xsl:template name="coordonnes">
        Tel :
        <xsl:choose>
            <xsl:when test="starts-with(cv24:identite/cv24:tel, '+33')">
                <xsl:value-of select="substring(cv24:identite/cv24:tel, 1, 3)"/>(0)<xsl:value-of select="substring(cv24:identite/cv24:tel, 4, 99)"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="cv24:identite/cv24:tel"/>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:element name="br"/>
        Mel : <xsl:value-of select="cv24:identite/cv24:mel"/>
    </xsl:template>

    <xsl:template name="objectif">
        <xsl:element name="h2">
            <xsl:value-of select="cv24:objectif"/>
        </xsl:element>
        <xsl:element name="p">
            Demande de <xsl:value-of select="cv24:objectif/@statut"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="experiences">
        <xsl:element name="h2">
            Expériences professionnelles
        </xsl:element>
        <xsl:element name="ol">
            <xsl:for-each select="cv24:prof/cv24:detail">
                <xsl:element name="li">
                    <xsl:value-of select="cv24:titre"/>(du <xsl:call-template name="formatDate">
                                                                <xsl:with-param name="DateTime" select="cv24:datedeb"/>
                                                            </xsl:call-template> au
                    <xsl:call-template name="formatDate">
                        <xsl:with-param name="DateTime" select="cv24:datefin"/>
                    </xsl:call-template>)
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

    <xsl:template name="diplome">
        <xsl:element name="h2">
            Diplomes
        </xsl:element>
        <xsl:element name="table">
            <xsl:element name="tbody">
                <xsl:for-each select="cv24:competences/cv24:diplome">
                    <xsl:element name="tr">
                        <xsl:element name="th">
                            <xsl:call-template name="formatDate">
                                <xsl:with-param name="DateTime" select="cv24:date"/>
                            </xsl:call-template>
                        </xsl:element>
                        <xsl:element name="th">
                            <xsl:value-of select="cv24:titre"/>
                        </xsl:element>
                        <xsl:element name="th">
                            (niveau <xsl:value-of select="@niveau"/>)
                        </xsl:element>
                        <xsl:element name="th">
                            <xsl:value-of select="cv24:institut"/>
                        </xsl:element>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <xsl:template name="certifications">
        <xsl:element name="h2">
            Certifications
        </xsl:element>
        <xsl:element name="table">
            <xsl:element name="tbody">
                <xsl:for-each select="cv24:competences/cv24:certif">
                    <xsl:element name="tr">
                        <xsl:element name="th">
                            <xsl:call-template name="formatDate">
                                <xsl:with-param name="DateTime" select="cv24:datedeb"/>
                            </xsl:call-template> - <xsl:call-template name="formatDate">
                            <xsl:with-param name="DateTime" select="cv24:datefin"/>
                        </xsl:call-template>
                        </xsl:element>
                        <xsl:element name="th">
                            <xsl:value-of select="cv24:titre"/>
                        </xsl:element>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <xsl:template name="language">
        <xsl:element name="h2">
            Langues
        </xsl:element>
        <xsl:element name="ul">
            <xsl:for-each select="cv24:divers/cv24:lv">
                <xsl:element name="li">
                    <xsl:value-of select="@lang"/> : <xsl:value-of select="@cert"/>
                    (<xsl:value-of select="@nivi"/> <xsl:value-of select="@nivs"/>)
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

    <xsl:template name="autres">
        <xsl:element name="h2">
            Divers
        </xsl:element>
        <xsl:element name="ul">
            <xsl:for-each select="cv24:divers/cv24:autre">
                <xsl:element name="li">
                    <xsl:value-of select="@titre"/> : <xsl:value-of select="@comment"/>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

    <xsl:template name="formatDate">
        <xsl:param name="DateTime" />

        <xsl:variable name="year" select="substring($DateTime,1,4)" />
        <xsl:variable name="month-temp" select="substring-after($DateTime,'-')" />
        <xsl:variable name="month" select="substring-before($month-temp,'-')" />
        <xsl:variable name="day-temp" select="substring-after($month-temp,'-')" />
        <xsl:variable name="day" select="substring($day-temp,1,2)" />

        <xsl:value-of select="$day"/>
        <xsl:value-of select="' '"/>
        <xsl:choose>
            <xsl:when test="$month = '1' or $month= '01'">Janv</xsl:when>
            <xsl:when test="$month = '2' or $month= '02'">Fevr</xsl:when>
            <xsl:when test="$month= '3' or $month= '03'">Mars</xsl:when>
            <xsl:when test="$month= '4' or $month= '04'">Apri</xsl:when>
            <xsl:when test="$month= '5' or $month= '05'">Mai</xsl:when>
            <xsl:when test="$month= '6' or $month= '06'">Juin</xsl:when>
            <xsl:when test="$month= '7' or $month= '07'">Juil</xsl:when>
            <xsl:when test="$month= '8' or $month= '08'">Augu</xsl:when>
            <xsl:when test="$month= '9' or $month= '09'">Sept</xsl:when>
            <xsl:when test="$month= '10'">Octo</xsl:when>
            <xsl:when test="$month= '11'">Nove</xsl:when>
            <xsl:when test="$month= '12'">Dece</xsl:when>
        </xsl:choose>
        <xsl:value-of select="' '"/>
        <xsl:value-of select="$year"/>
    </xsl:template>

</xsl:stylesheet>