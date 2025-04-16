package ma.est.carrentalnm.config;

import jakarta.transaction.Transactional;
import ma.est.carrentalnm.entities.Role;
import ma.est.carrentalnm.entities.User;
import ma.est.carrentalnm.repositories.RoleRepository;
import ma.est.carrentalnm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setName("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setTel("+212522101010");
        user.setRoles(Arrays.asList(adminRole));
        if(!userRepository.existsByEmail(user.getEmail()) &&
                !userRepository.existsByName(user.getName()) &&
        !userRepository.existsByTel(user.getTel()))
        {
            userRepository.save(user);
        }


        alreadySetup = true;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}
