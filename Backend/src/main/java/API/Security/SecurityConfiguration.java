package API.Security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailService authenticationUserDetailService;

    @Override protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, AuthenticationConfigConstants.SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.POST,"/api/library/book/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/account").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/inventory").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .anyRequest().permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }

}
