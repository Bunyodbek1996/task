package uz.bprodevelopment.task.service.base;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.task.entity.base.User;

import java.util.List;

public interface UserService {

    User getOne(Long id);

    List<User> getListAll(
            String fullName,
            String sort
    );

    Page<User> getList(
            Integer page,
            Integer size,
            String fullName,
            String sort
    );

    User save(User item);

    void delete(Long id);

    User getOneByUsername(String username);

    void addRole(Long userId, Long roleId);

}
