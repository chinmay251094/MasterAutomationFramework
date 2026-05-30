FROM mcr.microsoft.com/playwright/java:v1.52.0-noble

WORKDIR /workspace

COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

COPY . .

CMD ["mvn", "-B", "test", "-Denv=qa", "-Dheadless=true"]
