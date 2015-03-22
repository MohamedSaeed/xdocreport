# Loop for fields anywhere with template engine script #

Loop for fields in anywhere use syntax of the template engine. For instance if you wish generate list of name of developers you can write that with Velocity :

```
#foreach($developer in $developers)
  $developer.Name
#end
```

And write that with Freemarker :

```
[#list developers as developer]
  ${developer.name}
[/#list]
```

The only requirement is that you must use Mergefield to set this script. Here a sample with Velocity which use a Mergefield for start/end loop and developer name directive :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityList2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityList2.png)

After report generation you can see that :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityList2_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityList2_Out.png)