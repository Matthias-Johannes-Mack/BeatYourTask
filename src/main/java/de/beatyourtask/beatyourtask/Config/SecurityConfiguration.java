package de.beatyourtask.beatyourtask.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                // Authentifizierung erfolgt über den UserDetailsService. Entsprechende Methode wird im UserService
                // überschrieben
                .userDetailsService(userDetailsService)
                // Niemals Passwörter im Klartext abspeichern! Passwörter werden encoded mithilfe des BCryptPasswordEncoders
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                //TODO Delete /Project/
                // alle Requests die ohne Login erreichbar sind
                .antMatchers("/", "/Register", "/console/**", "static/**", "/Registerprocess", "images/**", "scss/**").permitAll()
                // definiere alle URLs die nur für eine bestimmte Rolle zugänglich sind
                // Achtung: Spring Security fügt automatisch das Prefix "ROLE_" für die Überprüfung ein. Daher verwenden wir
                // hier nicht "ROLE_ADMIN", wie bspw. im DataLoader angegeben.
                .antMatchers("/admin/**").hasRole("ADMIN")
                // alle weiteren Requests erfordern Authentifizierung
                .anyRequest().authenticated()
                // füge CSRF token ein, welches evtl. für AJAX-requests benötigt wird
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/console/**")
                // Request zum Aufruf der Login-Seite
                .and().formLogin().loginPage("/").failureUrl("/?error=true").permitAll()
                .defaultSuccessUrl("/projectoverview")
                .usernameParameter("username")
                .passwordParameter("password")
                // jeder kann sich ausloggen über den simplen /logout request ausloggen
                .and().logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout").and().csrf().disable();// CSRF Abgestellt für testen

        // Deaktiviert header security. Ermöglicht Nutzung der H2 Console.
        http.headers().frameOptions().sameOrigin().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/static/js/**", "/fragments/**");
    }

    /**
     * Password-encoder Bean der Spring Security.
     *
     * @return BCryptPasswordEncoder bean.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

}
