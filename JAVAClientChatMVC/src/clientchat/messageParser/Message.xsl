<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : Message.xsl
    Created on : January 24, 2017, 3:16 PM
    Author     : fatma
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Message.xsl</title>
            </head>
            <body>
                <table>
                    <tr>
                        <link rel="stylesheet" type="text/css" href="chatMsg.css"/>
                        <xsl:apply-templates />
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="header">
        <tr>
            <td> 
                <!-- <xsl:value-of select="."/>-->
            </td>
        </tr>
    </xsl:template>
    
    <xsl:template match="msgElement">
<!--        <tr>
            <xsl:apply-templates select="from"/>
        </tr>-->
        <tr>
            <xsl:apply-templates select="body"/>
        </tr>
    </xsl:template>
    
    <xsl:template match="from">
        <tr>
            <td style="position: relative;top: 77px;left: 53px;z-index: 3;font-style: italic;font-weight: bold">
                <xsl:value-of select="."/>
            </td>
        </tr>
    </xsl:template>
    
<!--    <xsl:template match="to">
        <tr>
            <xsl:if test=". ='fa1@yahoo.com'"> 
                    <xsl:value-of select="."/>
                <tabel border="2">
                    <tr>
                        <td>
                            <xsl:value-of select="."/>
                        </td>
                    </tr>
                </tabel>
            </xsl:if>
           <xsl:value-of select="."/>
        </tr>
    </xsl:template>-->
    
    
    <xsl:template match="body">
      <xsl:apply-templates select="from"/>

      <xsl:choose>
            <xsl:when test="from ='me' ">
                <tr class="talk-bubble tri-right border round btm-right-in " style="margin-left:70%;">
<!--                <tr class="talk-bubble tri-right border round bubble you">-->
                    <td>
                        <xsl:apply-templates select="content"/>
                    </td>
                    <td>
                        <xsl:apply-templates select="date"/>
                    </td>
<!--                    <td>me</td>-->
                </tr>
            </xsl:when>
            <xsl:otherwise>
                <tr class="talk-bubble tri-right border round btm-left-in ">
                    <td>
                        <xsl:apply-templates select="content"/>
                    </td>
                    <td>
                        <xsl:apply-templates select="date"/>
                    </td>
<!--                    <td>otherwise</td>-->
                </tr>
            </xsl:otherwise>
        </xsl:choose>
        
        
<!--        
        <xsl:if test=". ='me'"> 
            <tr class="talk-bubble tri-right border round btm-left-in ">
                    <td>
                        <xsl:apply-templates select="content"/>
                        <xsl:value-of select="."/>
                    </td>
                    <td>
                        <xsl:apply-templates select="date"/>
                    </td>
                </tr>
        </xsl:if>
        -->
        
    </xsl:template>
       
    
    <xsl:template match="content">
        <td>
            <div class="talktext">
                <xsl:value-of select="."/>
            </div>
        </td>
           
    </xsl:template>
    
    <xsl:template match="date">
        <td>
            <div class="talktext timestamp">
                <xsl:value-of select="."/>
            </div>
        </td>
           
    </xsl:template>

   
</xsl:stylesheet>
