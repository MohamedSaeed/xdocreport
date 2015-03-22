# Introduction #

FieldsMetadata  is used to manage lazy loop tables, images and text styling.

Ex:

```java

FieldsMetadata metadata = report.createFieldsMetadata();
metadata.addFieldAsImage("logo", "project.Logo");
```

# @FieldMetadata annotation #

@FieldMetadata gives you the capability to manage metadata with annotations:

  * description
  * syntax for text styling

Alternatively to

https://code.google.com/p/xdocreport/wiki/ODTReportingJavaMainHTMLTextStyling

or

https://code.google.com/p/xdocreport/wiki/DocxReportingJavaMainHTMLTextStyling

Users can easily configure threw fieldmetadata annotation :

```java

@FieldMetadata(syntaxKind="Html", description="HTML Formatted long text")
public String getComment() {
return comment;
}
```


# Sample #

Imagine you have the Project Pojo :

```java

public class Project {

private final String name;

public Project(String name) {
this.name = name;
}

public String getName() {
return name;
}
}
```

and you wish to display the name of the project with Html syntax.


## Without @FieldsMetadata ##

```java

// 1) Create FieldsMetadata
FieldsMetadata fieldsMetadata = report.createFieldsMetadata();

// 2) Set name as Html syntax
fieldsMetadata.addFieldAsTextStyling("project.Name", SyntaxKind.Html);
```

## With @FieldMetadata ##

Add @FieldMetadata in the Pojo class

```java

@FieldMetadata(syntaxKind="Html", description="HTML text")
public String getName() {
return name;
}
```

Load fields metadata to use information from the @FieldMetadata :

```java

// 1) Create FieldsMetadata
FieldsMetadata fieldsMetadata = report.createFieldsMetadata();

// 2) Load fields metadata from Java Class
fieldsMetadata.load("project", Project.class);
```