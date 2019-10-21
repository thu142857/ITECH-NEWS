package com.itechnews.controller.api;

import com.itechnews.entity.User;
import com.itechnews.service.Result;
import com.itechnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserApiController {

    @Autowired
    private UserService userService;

    @GetMapping("/find-by-username")
    public ResponseEntity<Result> findUserByUsername(@RequestParam("username") String username) {
        User user = userService.findOneByUsername(username);

        Result result = new Result();
        if (user == null) {
            result.setMessage("Not found this username");
            result.setStatus(Result.Status.FAILED);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.setStatus(Result.Status.SUCCESS);
            result.setData(getUserApi(user));
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
    @GetMapping("/find-by-email")
    public ResponseEntity<Result> findUserByEmail(@RequestParam("email") String email) {
        User user = userService.findOneByEmail(email);

        Result result = new Result();
        if (user == null) {
            result.setMessage("Not found this email");
            result.setStatus(Result.Status.FAILED);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.setStatus(Result.Status.SUCCESS);
            result.setData(getUserApi(user));
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    private Map<String, Object> getUserApi(User user) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());
        data.put("address", user.getAddress());
        data.put("status", user.getStatus());
        return data;
    }
}
