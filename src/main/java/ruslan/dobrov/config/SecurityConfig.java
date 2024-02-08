package ruslan.dobrov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.models.Role;
import ruslan.dobrov.repositories.PeopleRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PeopleRepository peopleRepository;

    public SecurityConfig(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").hasAuthority("USER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and()
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/user", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(userAuthoritiesMapper())))
                .logout()
                .logoutSuccessUrl("/login");
    }

//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(request ->
//                        request
//                                .requestMatchers("/user/**").hasAuthority("USER")
//                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                                .anyRequest().permitAll())
//                .oauth2Login(oauth2 -> oauth2
//                        .defaultSuccessUrl("/user", true)
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userAuthoritiesMapper(userAuthoritiesMapper())))
//                .build();
//    }

    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return authorities -> {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            authorities.forEach(authority -> {
                if (authority instanceof OAuth2UserAuthority oAuth2UserAuthority) {
                    Map<String, Object> userAttributes = oAuth2UserAuthority.getAttributes();
                    String email = (String) userAttributes.get("email");
                    Person user = peopleRepository.findByUsername(email).orElseGet(() -> {
                        Person createUser = Person.builder()
                                .username(email)
                                .password("123")
                                .email(email)
                                .roles(new HashSet<>())
                                .build();
                        createUser.getRoles().add(Role.USER);
                        peopleRepository.save(createUser);
                        return createUser;
                    });
                    grantedAuthorities.addAll(user.getRoles());
                }
            });
            return grantedAuthorities;
        };
    }

}
