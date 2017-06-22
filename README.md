# Reform http proxy spring boot autoconfigure library

A Java module which allows to set java proxy variables from the environment

It currently sets

* http.proxyHost
* http.proxyPort
* https.proxyHost
* https.proxyPort
* http.nonProxyHosts

from the `http_proxy` and `no_proxy` environment variables

## Prerequisites

- [Java 8](https://www.oracle.com/java)

### Basic usage

You should be able to use the module by simply adding it as your project's dependency.

Maven:

```xml
<dependency>
    <groupId>uk.gov.hmcts.reform</groupId>
    <artifactId>http-proxy-spring-boot-autoconfigure</artifactId>
    <version>1.0.0</version>
</dependency>
```

Gradle:

```groovy
compile group: 'uk.gov.hmcts.reform', name: 'http-proxy-spring-boot-autoconfigure', version: '1.0.0'
```

## Development guide

[Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) will automatically download a
project-local [Gradle](https://gradle.org/) distribution the first time you run any of the `gradlew` commands below.

### Tests and verification

To run all unit tests:

```bash
./gradlew test
```

To execute [Checkstyle](http://checkstyle.sourceforge.net/) checks:

```bash
./gradlew check
```

You can also execute both via:

```bash
./gradlew build
```

### Installing

To install the artifact to a local Maven repository:
```bash
./gradlew install
```
