package com.shifterwebapp.shifter.config;

import com.shifterwebapp.shifter.account.expert.Expert;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.repository.UserRepository;
import com.shifterwebapp.shifter.account.expert.repository.ExpertRepository; // ADD THIS
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.unit.DataSize;


@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UserRepository userRepository;
    private final ExpertRepository expertRepository;

    @Value("${upload.max-file-size}")
    private int maxFileSizeMB;

    @Value("${upload.max-request-size}")
    private int maxRequestSizeMB;

    @Value("${upload.max-swallow-size}")
    private int maxSwallowSizeMB;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        factory.setMaxFileSize(DataSize.ofMegabytes(maxFileSizeMB));
        factory.setMaxRequestSize(DataSize.ofMegabytes(maxRequestSizeMB));

        return factory.createMultipartConfig();
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return (factory) -> {
            factory.addConnectorCustomizers((Connector connector) -> {

                org.apache.coyote.ProtocolHandler protocolHandler = connector.getProtocolHandler();

                if (protocolHandler instanceof AbstractHttp11Protocol<?> abstractProtocol) {

                    abstractProtocol.setMaxSwallowSize((int) DataSize.ofMegabytes(maxSwallowSizeMB).toBytes());

//                     abstractProtocol.setMaxParts(50);
                }
            });
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Check Expert
            var expertOpt = expertRepository.findByEmail(username);
            if (expertOpt.isPresent()) {
                return expertOpt.get();
            }

            // Check User
            var userOpt = userRepository.findByEmail(username);
            if (userOpt.isPresent()) {
                return userOpt.get();
            }

            throw new UsernameNotFoundException("User not found with email: " + username + "!");
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}