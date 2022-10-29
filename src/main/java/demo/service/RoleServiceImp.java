package demo.service;

import demo.model.Role;
import demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleServiceImp implements RoleService {

    public final RoleRepository repository;

    @Autowired
    public RoleServiceImp(RoleRepository repository) {
        this.repository = repository;
    }
    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    public List<Role> getByName(String name) {
        return repository.findRoleByName(name);
    }
}