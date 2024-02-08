package ruslan.dobrov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.models.Role;
import ruslan.dobrov.repositories.PeopleRepository;

@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;

    @Transactional
    public void register(Person person) {
        person.getRoles().add(Role.USER);
        peopleRepository.save(person);
    }
}
