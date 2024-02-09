package ruslan.dobrov.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.repositories.PeopleRepository;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonService {
    private final PeopleRepository peopleRepository;

    public Optional<Person> findByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }

    public Optional<Person> findByEmail(String email) {
        return peopleRepository.findByEmail(email);
    }

    public void save(Person person) {
        log.info("User " + person.getUsername() + " with role: " + person.getRoles() + " was created");
        peopleRepository.save(person);
    }
}
