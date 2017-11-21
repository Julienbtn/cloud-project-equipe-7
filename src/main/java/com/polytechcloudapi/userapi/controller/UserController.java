package com.polytechcloudapi.userapi.controller;

import com.polytechcloudapi.userapi.model.User;
import com.polytechcloudapi.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Created by JOYMANGUL Jensen Selwyn
 * on 08-Nov-17.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final int MAX_PAGE_SIZE = 100;
    private final String DEFAULT_PAGE_NUMBER = "0";

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public List<User> getAll(@RequestParam(value = "page",  defaultValue = DEFAULT_PAGE_NUMBER) int page) {
        return userRepository.findAllByOrderByLastNameDesc(new PageRequest(page, MAX_PAGE_SIZE));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable(value = "id") String userId) {
        User user = userRepository.findOne(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody @Valid User user) {
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") String userId,
                                         @RequestBody @Valid  User UserDetails) {
        User user = userRepository.findOne(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setFirstName(UserDetails.getFirstName());
        user.setlastName(UserDetails.getlastName());
        user.setBirthDay(UserDetails.getBirthDay());
        user.setPosition(UserDetails.getPosition());

        User updatedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(updatedUser.getId()).toUri();
        return ResponseEntity.ok(updatedUser);
    }


    @PutMapping("")
    public ResponseEntity addList(@RequestBody @Valid List<User> users) {
        userRepository.deleteAll();
        List<User> createdUsers = userRepository.save(users);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(location).body(createdUsers);
    }

    @DeleteMapping("")
    public ResponseEntity deleteAll() {
        userRepository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String userId) {
        User user = userRepository.findOne(userId);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}
