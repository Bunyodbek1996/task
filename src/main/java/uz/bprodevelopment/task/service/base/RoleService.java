package uz.bprodevelopment.task.service.base;


import uz.bprodevelopment.task.entity.base.Role;

import java.util.List;

public interface RoleService {

    Role getRole(String name);
    Role getRole(Long id);
    List<Role> getRoles();

    Role saveRole(Role role);

}
