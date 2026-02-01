### Run as specific Env

1. setx -- Environment Variable
```
setx SPRING_PROFILES_ACTIVE qa
mvn spring-boot:run
```
The key rule on Windows:

setx does NOT affect the current terminal session

setx writes the variable to the registry

It is available only to new shells

The current PowerShell session still has no SPRING_PROFILES_ACTIVE

2. Session-only env var

   This works immediately, but only for this session.

``` 
$env:SPRING_PROFILES_ACTIVE="qa"
mvn spring-boot:run
```

3. JVM arguments 
``` 
mvn "-Dspring-boot.run.jvmArguments=-Dspring.profiles.active=prod" spring-boot:run

```