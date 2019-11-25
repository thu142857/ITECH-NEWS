package com.itechnews.controller.api;

import com.itechnews.entity.User;
import com.itechnews.security.UserDetailsImpl;
import com.itechnews.security.UserDetailsUtil;
import com.itechnews.service.Result;
import com.itechnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/follow")
    public ResponseEntity<Result> followUser(@RequestParam("followedId") Integer followedId,
                                             @RequestParam("isFollowing") Boolean isFollowing) {
        User loggedUser = null;
        try {
            UserDetails userDetails = UserDetailsUtil.getUserDetails();
            loggedUser = userService.findOneById(((UserDetailsImpl) userDetails).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User followedUser = userService.findOneById(followedId);
        if (isFollowing) {
            if (followedUser.getFollower().contains(loggedUser)) {
                followedUser.getFollower().remove(loggedUser);
            }
        } else {
            if (!followedUser.getFollower().contains(loggedUser)) {
                followedUser.getFollower().add(loggedUser);
            }
        }
        followedUser = userService.save(followedUser);


        Result result = new Result();
        result.setStatus(Result.Status.SUCCESS);
        result.setMessage("OK");
        Map<String, Object> data = new HashMap<>();
        data.put("follower_size", followedUser.getFollower().size());
        result.setData(data);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
