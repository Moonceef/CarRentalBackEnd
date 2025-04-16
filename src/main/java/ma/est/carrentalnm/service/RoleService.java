package ma.est.carrentalnm.service;

import ma.est.carrentalnm.entities.Role;
import ma.est.carrentalnm.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name){
        return roleRepository.findByName(name);
    }
}
