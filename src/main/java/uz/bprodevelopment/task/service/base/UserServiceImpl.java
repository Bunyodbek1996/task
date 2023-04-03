package uz.bprodevelopment.task.service.base;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bprodevelopment.task.entity.base.Role;
import uz.bprodevelopment.task.entity.base.User;
import uz.bprodevelopment.task.repo.base.UserRepo;
import uz.bprodevelopment.task.spec.BaseSpec;
import uz.bprodevelopment.task.spec.SearchCriteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public User getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<User> getListAll(
            String fullName,
            String sort
    ) {

        BaseSpec<User> spec1 = new BaseSpec<>(new SearchCriteria("id", ">", 0));
        Specification<User> spec = Specification.where(spec1);

        if (fullName != null)
            spec = spec.and(new BaseSpec<>(new SearchCriteria("fullName", ":", fullName)));

        if (sort == null) sort = "id";

        return repo.findAll(spec, Sort.by(sort).descending());
    }

    @Override
    public Page<User> getList(
            Integer page,
            Integer size,
            String fullName,
            String sort
    ) {

        BaseSpec<User> spec1 = new BaseSpec<>(new SearchCriteria("id", ">", 0));
        Specification<User> spec = Specification.where(spec1);

        if (fullName != null)
            spec = spec.and(new BaseSpec<>(new SearchCriteria("fullName", ":", fullName)));

        if (sort == null) sort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        return repo.findAll(spec, pageable);
    }

    @Override
    public User save(User item) {
        if (item.getUsername() == null || item.getPassword() == null
                || item.getFullName() == null) {
            throw new RuntimeException("Required fields is empty");
        }

        User dbUser = repo.findByUsername(item.getUsername());
        if (dbUser != null && item.getId() == null) {
            throw new RuntimeException("This username is busy: " + item.getUsername());
        }

        item.setPassword(passwordEncoder.encode(item.getPassword()));
        return repo.save(item);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public User getOneByUsername(String username) {
        return repo.findByUsername(username);
    }


    @Override
    @Transactional
    public void addRole(Long userId, Long roleId) {
        User user = repo.getReferenceById(userId);
        Role role = new Role();
        role.setId(roleId);
        user.getRoles().add(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);
        if (user == null) {
            log.error("User {} not found in the database", username);
            throw new UsernameNotFoundException("User " + username + " not found in the database");
        } else {
            log.info("User {} found in the database", username);
        }

        Collection<SimpleGrantedAuthority> authorities
                = new ArrayList<>();

        if (user.getRoles() != null) {
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );

    }
}
