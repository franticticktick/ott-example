package com.example.ott.magiclink;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public final class MagicLinkGenerationSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    private final MailSender mailSender;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public MagicLinkGenerationSuccessHandler(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {
        String magicLink = UriComponentsBuilder.fromUriString(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())
                .replaceQuery(null)
                .fragment(null)
                .path("/login/ott")
                .queryParam("token", oneTimeToken.getTokenValue())
                .toUriString();

        this.mailSender.send(oneTimeToken.getUsername(), "Your Spring Security One Time Token",
                "Use the following link to sign in into the application: " + magicLink);
        this.redirectStrategy.sendRedirect(request, response, "/page");
    }
}
