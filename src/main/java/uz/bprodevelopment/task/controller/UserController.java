package uz.bprodevelopment.task.controller;


import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.task.entity.base.User;
import uz.bprodevelopment.task.service.base.UserService;
import uz.bprodevelopment.task.util.ErrorResponse;

import static uz.bprodevelopment.task.config.Urls.USER_URL;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping(USER_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        User users = service.getOne(id);
        return ResponseEntity.ok().body(users);
    }


    @GetMapping(USER_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "rowsPerPage") Integer size,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "sort", required = false) String sort
    ) {
        return ResponseEntity.ok().body(service.getList(page, size, fullName, sort));
    }

    @GetMapping(USER_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "sort", required = false) String sort
    ) {
        return ResponseEntity.ok().body(service.getListAll(fullName, sort));
    }


    @PostMapping(USER_URL)
    public ResponseEntity<?> save(@RequestBody User item) {
        service.save(item);
        return ResponseEntity.ok().build();
    }


    @PutMapping(USER_URL)
    public ResponseEntity<?> update(@RequestBody User item) {
        service.save(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(USER_URL + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}

