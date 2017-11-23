package com.polytechcloudapi.userapi.controller;

import com.polytechcloudapi.userapi.model.User;
import com.polytechcloudapi.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by JOYMANGUL Jensen Selwyn
 * on 08-Nov-17.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final int MAX_PAGE_SIZE = 100;
    private final String DEFAULT_PAGE_NUMBER = "0";
    private final int NUMBER_NEAREST_USER = 10 ;

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /***************************************************  GET  ***************************************************/

    @GetMapping("")
    public List<User> getAll(@RequestParam(name = "page", required = false) Optional<Integer> page) {
        if(!page.isPresent())
            page = Optional.of(0);
        return userRepository.findAllByOrderByLastNameDesc(new PageRequest(page.get(), MAX_PAGE_SIZE));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable(value = "id") String userId) {
        User user = userRepository.findOne(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/age")
    public List<User> getByAgeGreaterThan(@RequestParam(name = "gt", required = false) Optional<Integer> gt,
                                          @RequestParam(name = "eq", required = false) Optional<Integer>  eq,
                                          @RequestParam(name = "page",  defaultValue = DEFAULT_PAGE_NUMBER) int page) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if (gt.isPresent()) {
            cal.add(Calendar.YEAR, -gt.get());
            Date birthday = cal.getTime();
            return userRepository.findByBirthDayBefore(birthday, new PageRequest(page, MAX_PAGE_SIZE));
        }
        else {
            cal.add(Calendar.YEAR, -eq.get());
            Date endDate = cal.getTime();
            cal.setTime(endDate);
            cal.set(Calendar.DAY_OF_YEAR, 1);
            Date startDate = cal.getTime();
            return userRepository.findByBirthDayBetween(startDate, endDate, new PageRequest(Integer.valueOf(DEFAULT_PAGE_NUMBER), MAX_PAGE_SIZE));
        }
    }

    @GetMapping("/search")
    public List<User> getLikeLastName(@RequestParam(name = "term") String searchName,
                                      @RequestParam(name = "page",  defaultValue = DEFAULT_PAGE_NUMBER) int page) {
        return userRepository.findByLastNameLike(searchName, new PageRequest(page, MAX_PAGE_SIZE));
    }

    @GetMapping("/nearest")
    public List<User> getPositionNear(@RequestParam(value = "lat") double lat,
                                      @RequestParam(value = "lon") double lon,
                                      @RequestParam(name = "page",  defaultValue = DEFAULT_PAGE_NUMBER) int page) {
        return userRepository.findByPositionNear(new Point(lat, lon), new PageRequest(page, NUMBER_NEAREST_USER));
    }

    /***************************************************  POST  ***************************************************/

    @PostMapping("")
    public ResponseEntity create(@RequestBody @Valid User user) {
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(user);
    }

    /***************************************************  PUT  ***************************************************/

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
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("")
    public ResponseEntity addList(@RequestBody @Valid List<User> users) {
        userRepository.deleteAll();
        List<User> createdUsers = userRepository.save(users);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(location).body(createdUsers);
    }

    /***************************************************  DELETE  ***************************************************/

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
