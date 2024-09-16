./gradlew build -x test
docker compose down spring
docker compose up -d --build spring