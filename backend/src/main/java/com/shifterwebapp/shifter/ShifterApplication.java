package com.shifterwebapp.shifter;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ShifterApplication {

	public static void main(String[] args) {
		// Load .env file only if it exists (for local development)
		// In production, rely on actual environment variables
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()  // Don't fail if .env doesn't exist (production)
				.load();

		// Only set properties if they're not already set by the environment
		// This allows production environment variables to take precedence
		setPropertyIfPresent(dotenv, "SPRING_PROFILES_ACTIVE");
		setPropertyIfPresent(dotenv, "PORT");
		setPropertyIfPresent(dotenv, "BACKEND_URL");
		setPropertyIfPresent(dotenv, "FRONTEND_URL");
		setPropertyIfPresent(dotenv, "ALLOWED_ORIGINS");

		// Database
		setPropertyIfPresent(dotenv, "SPRING_DATASOURCE_URL");
		setPropertyIfPresent(dotenv, "SPRING_DATASOURCE_USERNAME");
		setPropertyIfPresent(dotenv, "SPRING_DATASOURCE_PASSWORD");
		setPropertyIfPresent(dotenv, "DB_POOL_SIZE");
		setPropertyIfPresent(dotenv, "DB_POOL_MIN_IDLE");
		setPropertyIfPresent(dotenv, "DDL_AUTO");
		setPropertyIfPresent(dotenv, "SHOW_SQL");

		// JWT
		setPropertyIfPresent(dotenv, "JWT_CONFIG_SECRET");
		setPropertyIfPresent(dotenv, "JWT_EXPIRATION");
		setPropertyIfPresent(dotenv, "JWT_REFRESH_EXPIRATION");

		// AWS S3
		setPropertyIfPresent(dotenv, "AWS_S3_REGION");
		setPropertyIfPresent(dotenv, "AWS_S3_BUCKET_NAME");
		setPropertyIfPresent(dotenv, "AWS_S3_ACCESS_KEY");
		setPropertyIfPresent(dotenv, "AWS_S3_SECRET_KEY");

		// Google OAuth2
		setPropertyIfPresent(dotenv, "GOOGLE_CLIENT_ID");
		setPropertyIfPresent(dotenv, "GOOGLE_CLIENT_SECRET");
		setPropertyIfPresent(dotenv, "GOOGLE_REFRESH_TOKEN");

		// Google Calendar
		setPropertyIfPresent(dotenv, "GOOGLE_EXPERT_CALENDAR_ID");
		setPropertyIfPresent(dotenv, "GOOGLE_SERVICE_ACCOUNT_EMAIL");

		// Handle private key with newline replacement
		String privateKey = dotenv.get("GOOGLE_CALENDAR_PRIVATE_KEY");
		if (privateKey != null && System.getProperty("GOOGLE_CALENDAR_PRIVATE_KEY") == null) {
			System.setProperty("GOOGLE_CALENDAR_PRIVATE_KEY", privateKey.replace("\\n", "\n"));
		}

		// Email
		setPropertyIfPresent(dotenv, "EMAIL_HOST");
		setPropertyIfPresent(dotenv, "EMAIL_PORT");
		setPropertyIfPresent(dotenv, "EMAIL_USERNAME");
		setPropertyIfPresent(dotenv, "EMAIL_PASSWORD");

		// Zoom
		setPropertyIfPresent(dotenv, "ZOOM_ACCOUNT_ID");
		setPropertyIfPresent(dotenv, "ZOOM_CLIENT_ID");
		setPropertyIfPresent(dotenv, "ZOOM_CLIENT_SECRET");

		// Logging
		setPropertyIfPresent(dotenv, "LOG_LEVEL");
		setPropertyIfPresent(dotenv, "APP_LOG_LEVEL");
		setPropertyIfPresent(dotenv, "SECURITY_LOG_LEVEL");
		setPropertyIfPresent(dotenv, "WEB_LOG_LEVEL");
		setPropertyIfPresent(dotenv, "HTTP_LOG_LEVEL");

		SpringApplication.run(ShifterApplication.class, args);
	}

	private static void setPropertyIfPresent(Dotenv dotenv, String key) {
		String value = dotenv.get(key);
		if (value != null && System.getProperty(key) == null) {
			System.setProperty(key, value);
		}
	}
}