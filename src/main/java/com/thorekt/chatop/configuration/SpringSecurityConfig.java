package com.thorekt.chatop.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.DispatcherTypeRequestMatcher;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.thorekt.chatop.service.CustomUserDetailsService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Security filter chain for handling /error endpoint to allow all requests.
     * 
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(0)
    SecurityFilterChain errorChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/error")
                .authorizeHttpRequests(a -> a.anyRequest().permitAll())
                .build();
    }

    /**
     * Security filter chain for handling /api/auth/** endpoints to allow all
     * requests.
     * 
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/auth/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    /**
     * Security filter chain for handling all other endpoints to require
     * authentication via JWT tokens.
     * 
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(2)
    public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            PasswordEncoder encoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /// TODO: externaliser la clef
    private String jwtKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCWUIaybcxCrrlg\r\n" + //
            "heIrmxANWfu6ERR85HB7RYkuX/sgICRegx0kjKDZSqi1lhUV59KpajjvE2Hax8Sy\r\n" + //
            "IFAZKUxe368xarso78ChAUZZx7vKyWUGuf7cJLewzluu0W9aFMt4qJ2MImlt+UCv\r\n" + //
            "vQmG6KiKgtHf6fPAWLo4Bt2Lq7vyGMeQsd4Mfn14QgO7m0GU8sn9sVuM2pNmUyfp\r\n" + //
            "oBI29lTS6AE6BanspLMsXoB4Nf/q71aqBw08hQdhMcS0NAYECj98d7ay3X8WFzx0\r\n" + //
            "nkkC0Ceo4bpKpzbYBRmexbAyCLr+jpp580/Vh04V1zenyAKxovrYedtibUG9Gqyy\r\n" + //
            "dR7pDhcVAgMBAAECggEAAfy+asSVwa3vi6Fn/CggNoNz0/EYrSzLI5+bdtwjYFDn\r\n" + //
            "I1WaYhq5xKHc/FZejpFesrP8aQttV4H4k5hOS11XoIt7DnNFPfL0LczBWSNUYEqp\r\n" + //
            "fMT4OTgnuINJ7DWxEXMUsa3mJ+dzIw0+ij1nT0suTMiYy8nL3/AEv5Za4yZ7jM0W\r\n" + //
            "ZZgFIIEZCA3C4AAsW3c2Dpn4zADAfdRzB9T2LBsh0YHfItZRT2XaA8zBLdxFZ43F\r\n" + //
            "2mxvWTUs/ibEomsWyOzUPqQs7cAvVqbNOItYWSgUJ/z6P3M/n6PVRIWoYojPYGzl\r\n" + //
            "JP8yPjz3Il/sqiS2x1q9O0DBtB5HGNg+4iNaNep7EwKBgQDOySZTUoeBg+SemQao\r\n" + //
            "IYsJ+DzBMEpFuHU8NAwEncowrx8xkO3KQPySRjTCADNoA3s4Hlf4Z+bawgDrEJ10\r\n" + //
            "Gv1mXwgpJ5KooDbHqg9+8i/7F8CLzyQPdDRJ3xmp/KRMIYsMjLwGnPUBOqeGSYII\r\n" + //
            "eZD58dRMIv2KfvnCRFPXw930uwKBgQC6FsCWtNVZBWo2VUsg8otd/Bc9gP0x/pm/\r\n" + //
            "FRrbo5QJubjn0b8WXXJC7GTB4b4Np00KdbTuZmXhLvdpZscHPr3P77g8ZHZc/cKN\r\n" + //
            "347Xl5neuq3lvKBq5IkT6JgChZnIS90nAz1LCMIf6p/LGGCQP8SeEpwwJ4fsAk9I\r\n" + //
            "0jE9/cBObwKBgQDDwUZn0JP49rwBq8nSdlaibH5PQIC/Ody5zmemIx3aMp4sFRAW\r\n" + //
            "zKiNbKrbEMeZatpGCOuse+feJZQq80KrPDWwqLwYAZ9jxK+3GnWq7mifoh+j+scf\r\n" + //
            "Xhl16R7pfgKnE4YHrl79P67sp/MZPBM+JJ8rDlS0ctdkOcoErxxUANboqwKBgFOO\r\n" + //
            "cc15xy5guw9IfyJ9Jcz+q6THLWlsa7acEGI88fTn+rnEuWuea/bW1GsmJMNWssYW\r\n" + //
            "kjZVrjtNbnRaXNXI+0vJ1MzxQyCAJQK1NhtiDAoxgnHcn58/nLlewBqpyKJiRhwF\r\n" + //
            "XVk6xoe0PynfeURsDan4qz8IEQUNCHKnRssSDG5rAoGBAIG2TLiQZSTKz9D7t0T6\r\n" + //
            "Mt5Zv9NLX0N5dmYx8hPfcsBAWcTEForFo+u1s5WWLMPj5qop1ljRUj8FVXsuYqaU\r\n" + //
            "9F5GwILiAnb7ikUii5VSSFK3D15x9ZuZOlSQX/wQUbJO5yNL8Rj+AWeIxOCZh74m\r\n" + //
            "1TRyKyXoBoTVG84vyfI5lFEK";

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length, "RSA");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
    }
}
