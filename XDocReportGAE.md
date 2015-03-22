# XDocReport & Google App Engine #

Since [XDocReport 1.0.2](XDocReport102.md), XDocReport supports [Google App Engine](https://developers.google.com/appengine/) : reporting and converter features are available in Google App Engine context.

## Converters with GAE ##

You can play with XDocReport ODT and DOCX converters (XHTML and PDF) with the [XDocReport GAE live demo](http://1.xdocreport-gae.appspot.com/). You can find sources of this demo on [xdocreport-gae-demo](http://code.google.com/p/xdocreport/source/browse/?repo=samples#git%2Fxdocreport-gae-demo).

## Reporting with GAE ##

You can :

  * [download](https://code.google.com/p/xdocreport/downloads/list) the **reporting-webapp-gae-xxx.war** : if you don't use maven, this war is perfect to get the well GAE JARs.
  * find sources on [reporting-webapp-gae](https://code.google.com/p/xdocreport/source/browse/?repo=samples#git%2Freporting-webapp-gae) : if you  use maven, this project contains a POM with the well dependencies.

# Problem with Google App Engine #

With Google App Engine, java.awt.Color is not allowed, see
[The JRE Class White List](https://developers.google.com/appengine/docs/java/jrewhitelist) for more information.

ODT and DOCX converters uses java.awt.Color in their code, so it cannot be used with Google App Engine.

To resolve this problem, XDocReport uses a dedicated implementation of itext ported on gae (https://github.com/pascalleclercq/itext-gae) (since version 1.0.2).


# Converters & Google App Engine #

## DOCX Converter with GAE ##

### DOCX Converter to XHTML with GAE ###

To download the XDOCREPORT\_VERSION version of DOCX 2 XHTML converter you can use maven :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>org.apache.poi.xwpf.converter.xhtml-gae</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

### DOCX Converter to PDF with GAE ###

To download the XDOCREPORT\_VERSION version of DOCX 2 PDF converter you can use maven :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>org.apache.poi.xwpf.converter.pdf-gae</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

## ODT Converter with GAE ##

### ODT Converter to XHTML with GAE ###

To download the XDOCREPORT\_VERSION version of ODT 2 XHTML converter you can use maven :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>org.odftoolkit.odfdom.converter.xhtml-gae</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

### ODT Converter to PDF with GAE ###

To download the XDOCREPORT\_VERSION version of ODT 2 PDF converter you can use maven :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>org.odftoolkit.odfdom.converter.pdf-gae</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```