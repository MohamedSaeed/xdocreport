No external dependencies is required for logging with XDocReport.

**We recommend the use of logback since It's probably the most advanced logging framework available.**

# JUL #
If no logback jar  nor log4j jar can be found in the classpath, JUL (java.util.Logging) is used.

JUL documentation can be found here : http://docs.oracle.com/javase/1.4.2/docs/guide/util/logging/overview.html

The default configuration is located under lib/logging.properties file in the JRE directory.

If you want to customize It, you have to provide a logging.properties file like this an tell your application :
-Djava.util.logging.config.file=mylogging.properties

```
handlers = java.util.logging.ConsoleHandler

 # Set the default logging level for the root logger
 .level = FINE

 # Set the default logging level for new ConsoleHandler instances
 java.util.logging.ConsoleHandler.level = FINE

 # Set the default formatter for new ConsoleHandler instances
 java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
```

# Log4j #

If [Log4j](http://logging.apache.org/log4j/) jar can be found in the classpath then log4j will be use instead of JUL.

You can configure log4j by simply putting a log4j.xml at the root of the classpath:
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">
  <appender name="stdout" class="org.apache.log4j.ConsoleAppender">
    <layout class="org.apache.log4j.PatternLayout">
      <param name="ConversionPattern" value="%-4r %-5p %c %x - %m%n" />
    </layout>
  </appender>
  <root>
    <level value="DEBUG" />
    <appender-ref ref="stdout" />
  </root>

</log4j:configuration>
```


# Logback #

If [Logback](http://logback.qos.ch/) can be found in the classpath then the It will be used.

To configure lobback, simply place a file called "logback.xml" at the root of the classpath.

Example:
```
<configuration>

  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <!-- encoders are assigned the type ch.qos.logback.classic.encoder.PatternLayoutEncoder
      by default -->
    <encoder>
      <pattern>%-4relative [%thread] %-5level %logger{35} - %msg %n
      </pattern>
    </encoder>
  </appender>

  <root level="DEBUG">
    <appender-ref ref="STDOUT" />
  </root>
</configuration>
```

You can find more informations on how to configure logback here : http://logback.qos.ch/manual/joran.html