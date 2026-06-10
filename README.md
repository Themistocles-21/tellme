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

## Setting up credentials (required before running)

### 1. Gmail (for email notifications)
- Create a Gmail account or use an existing one
- Generate an App Password (Google Account → Security → 2FA → App Passwords)
- Replace the Gmail username and password placeholders in `application.properties`

### 2. PostgreSQL database
- Create a database (Supabase, Neon, AWS RDS, or local PostgreSQL)
- Replace the database URL, username, and password placeholders in `application.properties`

### 3. OpenAI API key (for translation)
- Get an API key from https://platform.openai.com/api-keys
- Set as environment variable: `OPENAI_API_KEY=your-key-here`

## Features
- 7-step customer intake form
- Multi-language support (English, Persian, Russian)
- OpenAI-powered translation of customer comments
- PDF work order generation
- Email notifications to customer and service writer
- Service writer dashboard to toggle available services/languages
- Persistent storage of PDF work orders

## Team
Team #4: Aaron Oehler, Danish Wahidi, Derik Little
CSCD 488/490 - Winter/Spring 2026