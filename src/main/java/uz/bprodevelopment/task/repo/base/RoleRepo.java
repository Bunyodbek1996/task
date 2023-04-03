package uz.bprodevelopment.task.repo.base;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bprodevelopment.task.entity.base.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
