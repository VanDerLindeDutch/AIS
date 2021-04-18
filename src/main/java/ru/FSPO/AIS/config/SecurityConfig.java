package ru.FSPO.AIS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.FSPO.AIS.security.LandlordDetailsServiceImpl;
import ru.FSPO.AIS.security.RenterDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }



    @Configuration
    @Order(2)
    public static class RenterConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final RenterDetailsServiceImpl renterDetailsService;

        @Autowired
        public RenterConfigurationAdapter(RenterDetailsServiceImpl renterDetailsService) {
            super();
            this.renterDetailsService = renterDetailsService;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http

                    .requestMatcher(new AntPathRequestMatcher("/**"))
                    .authorizeRequests()
                    .antMatchers("/resources/database.properties")
                    .denyAll()
                    .antMatchers("/start", "/renter/**", "/resources/**", "/resources/landlord/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()


                    .and()

                    .formLogin()
                    .loginPage("/renter/login").permitAll()
                    .failureUrl("/renter/login?error=true")

                    .defaultSuccessUrl("/main")
                    // logout
                    .and()
                    .logout()
                    .logoutSuccessUrl("/start")
                    .deleteCookies("JSESSIONID")

                    .and()
                    .csrf()
                    .disable();

        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(daoRenterAuthenticationProvider());
        }

        @Bean
        protected DaoAuthenticationProvider daoRenterAuthenticationProvider() {
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            daoAuthenticationProvider.setUserDetailsService(renterDetailsService);
            return daoAuthenticationProvider;
        }
    }


    @Configuration
    @Order(1)
    public static class LandlordConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final LandlordDetailsServiceImpl landlordDetailsService;

        @Autowired
        public LandlordConfigurationAdapter(LandlordDetailsServiceImpl landlordDetailsService) {
            super();
            this.landlordDetailsService = landlordDetailsService;
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .requestMatcher(new AntPathRequestMatcher("/landlord/**"))
                    .authorizeRequests()
                    .antMatchers("/landlord/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()


                    .and()

                    .formLogin()
                    .loginPage("/landlord/login").permitAll()
                    .loginProcessingUrl("/landlord/login")
                    .defaultSuccessUrl("/main")
                    .failureUrl("/landlord/login?error=true")
                    // logout
                    .and()
                    .logout()
                    .logoutSuccessUrl("/start")
                    .deleteCookies("JSESSIONID")

                    .and()

                    .csrf()
                    .disable();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(daoLandlordAuthenticationProvider());
        }

        @Bean
        protected DaoAuthenticationProvider daoLandlordAuthenticationProvider() {
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            daoAuthenticationProvider.setUserDetailsService(landlordDetailsService);
            return daoAuthenticationProvider;
        }
    }

}
