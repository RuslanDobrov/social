package ruslan.dobrov.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.repositories.PeopleRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);
        if (person.isEmpty()) {
            log.error("User with login: " + username + ", is not found");
            throw new UsernameNotFoundException("User not found!");
        }
        return new PersonDetails(person.get());
    }
}
