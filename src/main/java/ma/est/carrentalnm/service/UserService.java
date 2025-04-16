package ma.est.carrentalnm.service;
import ma.est.carrentalnm.dtos.RegistreResponse;
import ma.est.carrentalnm.entities.Role;
import ma.est.carrentalnm.entities.User;
import ma.est.carrentalnm.repositories.RoleRepository;
import ma.est.carrentalnm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    RoleService roleService;


    private final UserRepository userRepository;
    @Autowired
    private Jwtservice jwTservice;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository,RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;

    }

    public RegistreResponse register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(List.of(roleService.getRoleByName("ROLE_CLIENT")));

        User savedUser = userRepository.save(user);

        RegistreResponse registre = new RegistreResponse();
        registre.setName(savedUser.getName());
        registre.setEmail(savedUser.getEmail());
        registre.setTel(savedUser.getTel());

        List<Role> userRoles = (List<Role> ) savedUser.getRoles();
        Role userRole = userRoles.get(0);


        registre.setRole(userRole.getName());


        return registre;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    public String validate(User user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));

       if (authentication.isAuthenticated())
           return jwTservice.generateToken(user.getEmail());
       return "fail";
    }
}