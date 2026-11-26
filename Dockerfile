# Runs the full suite headless in a container: same result on any machine or CI agent.
#   docker build -t hybrid-tests .
#   docker run --rm -v "$PWD/target:/app/target" hybrid-tests   (results land in ./target/allure-results)
FROM maven:3.9.9-eclipse-temurin-17

RUN apt-get update \
    && apt-get install -y --no-install-recommends wget gnupg ca-certificates \
    && wget -q -O /tmp/chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
    && apt-get install -y --no-install-recommends /tmp/chrome.deb \
    && rm -rf /tmp/chrome.deb /var/lib/apt/lists/*

WORKDIR /app

# Cache dependencies in their own layer so code changes don't re-download them.
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY testng.xml testng-smoke.xml ./
COPY src ./src

ENTRYPOINT ["mvn", "-B", "test", "-Dheadless=true", "-Dbrowser=chrome"]
