# Tellme – Multilingual Service Intake System

## Required to run the project
- Java 17
- Maven
- PostgreSQL database (Supabase or local)

## Setup steps
1. Clone the repository
2. Configure `application.properties` with your own:
    - Database URL, username, password
    - Gmail SMTP credentials (or other mail server)
    - OpenAI API key (for translation)
3. Build: `mvn clean package`
4. Run: `java -jar target/springboot-html-0.0.1-SNAPSHOT.jar`

## Default login
- Service writer: username `writer`, password `password`

## External accounts needed
- Gmail account for email notifications
- OpenAI API key (for translation feature)
- PostgreSQL database (Supabase or any provider)