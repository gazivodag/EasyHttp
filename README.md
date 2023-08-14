# EasyHttp
EasyHttp is a wrapper library for Java's Http Server library, providing a simplified interface for creating routes and handling HTTP requests.

We are not on any maven repositories, so you will have to build & publish locally.

## Building & Publishing to Maven Local

Since the library is not available on any Maven repositories yet, you need to build and publish it locally. Follow these steps:

1. Build the Gradle Project
```
gradle build
```
2. Publish to Maven Local:
```
gradle publishMavenPublicationToMavenLocal
```

## Implementing EasyHttp into your project
To use EasyHttp in your project, make sure you have the following configuration:
1. Add `mavenLocal()` to your repositories block in your `build.gradle.kts` file:
```kotlin
repositories {
    mavenLocal() // <----
    mavenCentral()
}
```
2. Add the library as a dependency in your dependencies block of build.gradle.kts:
```kotlin
dependencies {
    implementation("com.driftkiller:EasyHttp:1.0")
} 
```

## Usage
1. Create your route class(es). EasyHttp can handle multiple route classes, and each route method should be annotated with `@EasyRoute`:
#### Routes.java
```java
@SuppressWarnings("unused")
public class Routes {
    
    @EasyRoute(path = "/test", httpMethodType = HttpMethodType.GET)
    public static void testRoute(EasyHttpInteraction interaction) throws IOException {
        // ....some code in between
        
        interaction.send("Hello, World!");
    }
    
    // Sending an object (automatic JSON serialization thanks to Gson)
    @EasyRoute(path = "/serializetest", httpMethodType = HttpMethodType.GET)
    public static void testRoute(EasyHttpInteraction interaction) throws IOException {
        Person person = new Person(
                "John",
                "Doe",
                41,
                "123 Placeholder Street",
                "Placeholder City",
                11111
        );
        interaction.json(person);
    }
    
    
    @EasyRoute(path = "/echorequestbody", httpMethodType = HttpMethodType.POST)
    public static void echoBodyRoute(EasyHttpInteraction interaction) throws IOException {
        // EasyHttp can also handle POST requests.
        Map<String, Object> body = interaction.getBodyJson();
        interaction.json(body); // Echo the JSON back
    }
}
```

2. Implement an EasyHttpServer that takes in your route class(es):
### Main class
```java
public class Main {

    private static EasyHttpServer easyHttpServer;

    private static final Class<?>[] routes = new Class[]{
        Routes.class
    };

    private static final int PORT = 6089;

    public static void main(String[] args) throws IOException {
        easyHttpServer = new EasyHttpServer(PORT, routes);
        easyHttpServer.start();
    }
}
```