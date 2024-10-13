FROM alpine:latest AS setup

RUN apk add git openjdk17

RUN git clone https://github.com/Anton3413/Telegram-Language-Bot.git

WORKDIR Telegram-Language-Bot

RUN chmod +x mvnw && ./mvnw clean install -DskipTests


FROM alpine:latest AS result

RUN apk add git openjdk17

WORKDIR /app

COPY --from=setup /Telegram-Language-Bot/target/TelegramLanguageBot-*.jar ./web.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "web.jar"]

