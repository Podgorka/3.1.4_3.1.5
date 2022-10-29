package demo.service;

import demo.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> getAllRoles();

    List<Role> getByName(String name);
}
