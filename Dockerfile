FROM gradle:9-jdk25 AS build

WORKDIR /app

COPY build.gradle* settings.gradle* gradle.properties* ./
COPY gradle/ ./gradle/

COPY src/ ./src/

RUN gradle clean build --no-daemon

RUN mkdir -p /app/split && \
    cd /app/split && \
    gradle -q dependencies --configuration runtimeClasspath | grep -v "BUILD SUCCESSFUL" | grep -v "Configuration on demand" > /dev/null && \
    cp -r /home/gradle/.gradle/caches/modules-2/files-2.1/* ./dependencies/ 2>/dev/null || true && \
    find /app/build/libs -name "*.jar" -not -name "*-sources.jar" -not -name "*-javadoc.jar" | head -1 | xargs -I {} cp {} ./app.jar

FROM openjdk:25-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar ./libs/
COPY --from=build /home/gradle/.gradle/caches/modules-2/files-2.1/ ./dependencies/

EXPOSE 9595

ENV CLASSPATH="/app/libs/*:/app/dependencies/*/*/*/*.jar"
CMD ["java", "-cp", "/app/libs/*", "com.github.kinetic.logthing.LogThing"]
