package ruslan.dobrov.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    public String doAdminStuff() {
        return "Only admin here";
    }
}
