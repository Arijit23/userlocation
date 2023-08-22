package com.example.userlocation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.userlocation.entity.UserLocation;
import com.example.userlocation.repository.UserLocationRepository;
import com.example.userlocation.service.UserLocationService;


@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private UserLocationService userLocationService;

    @Autowired
    private UserLocationRepository userLocationRepository;

    @GetMapping("/get_users")
    public ResponseEntity<List<UserLocation>> getAllUsers() {
        List<UserLocation> users = userLocationRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users , HttpStatus.OK);
    }

    @GetMapping("/get_users/{n}")
    public List<UserLocation> getNearestUsers(@PathVariable int n) {
        return userLocationService.getNearestUsers(n);
    }

    @GetMapping("/get_user/{id}")
    public ResponseEntity<UserLocation> getUserById(@PathVariable Long id) {
        Optional<UserLocation> user = userLocationRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create_data")
    public ResponseEntity<UserLocation> saveEmployeeDetails(@RequestBody UserLocation user) {
        UserLocation savedUser = userLocationRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PatchMapping("/update_data")
    public ResponseEntity<UserLocation> updateEmployeeDetails(@RequestBody UserLocation user) {
        if (userLocationRepository.existsById(user.getId())) {
            UserLocation updatedUser = userLocationRepository.save(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delemp/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        if (userLocationRepository.existsById(id)) {
            userLocationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
