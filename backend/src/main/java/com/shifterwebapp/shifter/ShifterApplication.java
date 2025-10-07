package com.shifterwebapp.shifter;
import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ShifterApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		// Set env variables from .env file
		System.setProperty("JWT_CONFIG_SECRET", dotenv.get("JWT_CONFIG_SECRET"));

		System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
		System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
		System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));

		System.setProperty("AWS_S3_REGION", dotenv.get("AWS_S3_REGION"));
		System.setProperty("AWS_S3_BUCKET_NAME", dotenv.get("AWS_S3_BUCKET_NAME"));
		System.setProperty("AWS_S3_ACCESS_KEY", dotenv.get("AWS_S3_ACCESS_KEY"));
		System.setProperty("AWS_S3_SECRET_KEY", dotenv.get("AWS_S3_SECRET_KEY"));

		System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
		System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
		System.setProperty("GOOGLE_REFRESH_TOKEN", dotenv.get("GOOGLE_REFRESH_TOKEN"));

		System.setProperty("GOOGLE_EXPERT_CALENDAR_ID", dotenv.get("GOOGLE_EXPERT_CALENDAR_ID"));
		System.setProperty("GOOGLE_SERVICE_ACCOUNT_EMAIL", dotenv.get("GOOGLE_SERVICE_ACCOUNT_EMAIL"));
		System.setProperty("GOOGLE_CALENDAR_PRIVATE_KEY", dotenv.get("GOOGLE_CALENDAR_PRIVATE_KEY").replace("\\n", "\n"));

		System.setProperty("EMAIL_HOST", dotenv.get("EMAIL_HOST"));
		System.setProperty("EMAIL_PORT", dotenv.get("EMAIL_PORT"));
		System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
		System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));

		System.setProperty("ZOOM_ACCOUNT_ID", dotenv.get("ZOOM_ACCOUNT_ID"));
		System.setProperty("ZOOM_CLIENT_ID", dotenv.get("ZOOM_CLIENT_ID"));
		System.setProperty("ZOOM_CLIENT_SECRET", dotenv.get("ZOOM_CLIENT_SECRET"));

		SpringApplication.run(ShifterApplication.class, args);
	}

}
