package ruslan.dobrov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.repositories.PeopleRepository;


import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PersonService {
    private final PeopleRepository peopleRepository;

    public Optional<Person> findByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }

    public void save(Person person) {
        peopleRepository.save(person);
    }
}
