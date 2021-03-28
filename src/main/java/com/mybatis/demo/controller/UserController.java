package com.mybatis.demo.controller;

import com.mybatis.demo.entity.User;
import com.mybatis.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable("id") String id){
        return ResponseEntity.ok(userService.getUserByPK(id));
    }

    @PostMapping("/")
    public ResponseEntity<User> insert(){
        var user = User.builder()
                .userId(UUID.randomUUID().toString())
                .userName("test")
                .age(35)
                .gender("male")
                .build();
        userService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") String id){
        var user = User.builder()
                .userId(id)
                .userName("test_update")
                .age(20)
                .gender("female")
                .build();
        userService.update(user);
        user = userService.getUserByPK(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") String id){
        var user = userService.getUserByPK(id);
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteAll() > 0 ? "all deleted" : "fail");
    }
}
