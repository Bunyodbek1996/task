package uz.bprodevelopment.task.service.base;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.task.entity.base.Role;
import uz.bprodevelopment.task.repo.base.RoleRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;


    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }


    @Override
    public Role getRole(String name) {
        return roleRepo.findByName(name);
    }

    @Override
    public Role getRole(Long id) {
        Optional<Role> optionalRole = roleRepo.findById(id);
        return optionalRole.orElse(null);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

}
