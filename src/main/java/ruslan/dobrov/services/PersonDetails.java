package ruslan.dobrov.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ruslan.dobrov.models.Person;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@RequiredArgsConstructor
public class PersonDetails implements UserDetails {
    private final Person person;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return person.getRoles();
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Person getPerson() {
        return this.person;
    }
}
