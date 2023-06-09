package uz.bprodevelopment.task.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.bprodevelopment.task.util.ErrorResponse;
import uz.bprodevelopment.task.entity.base.User;
import uz.bprodevelopment.task.repo.base.UserRepo;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static uz.bprodevelopment.task.config.Constants.SECURE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                      UserRepo userRepo,
                                      PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username: {} Password: {}", username, password);

        User optionalUser = userRepo.findByUsername(username);

        if (optionalUser == null) {
            log.info("Error logging in: username is not exist");
            response.setStatus(409);
            response.setContentType(APPLICATION_JSON_VALUE);
            try {
                new ObjectMapper()
                        .writeValue(response.getOutputStream(),
                                ErrorResponse.getInstance().buildMap(
                                        409,
                                        "Error logging in: username is not exist",
                                        "Username mavjud emas",
                                        request.getServletPath()
                                ));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (!passwordEncoder.matches(password, optionalUser.getPassword())) {
                response.setStatus(410);
                response.setContentType(APPLICATION_JSON_VALUE);
                try {
                    new ObjectMapper()
                            .writeValue(response.getOutputStream(),
                                    ErrorResponse.getInstance().buildMap(
                                            410,
                                            "Error logging in: Password is incorrect",
                                            "Parol xato kiritildi",
                                            request.getServletPath()
                                    ));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        assert optionalUser != null;
        CustomUsernamePasswordAuthenticationToken authenticationToken
                = new CustomUsernamePasswordAuthenticationToken(username, password, optionalUser.getId());
        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication) throws IOException {

        org.springframework.security.core.userdetails.User user
                = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        Algorithm algorithm = Algorithm.HMAC256(SECURE.getBytes());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 2))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        User dbUser = userRepo.findByUsername(user.getUsername());

        response.setContentType(APPLICATION_JSON_VALUE);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("user", dbUser);
        userMap.put("accessToken", accessToken);
        userMap.put("refreshToken", refreshToken);

        new ObjectMapper().writeValue(response.getOutputStream(), userMap);
    }

}
