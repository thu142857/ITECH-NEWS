package com.itechnews.controller.api;

import com.itechnews.entity.Post;
import com.itechnews.entity.User;
import com.itechnews.security.UserDetailsImpl;
import com.itechnews.security.UserDetailsUtil;
import com.itechnews.service.CommentService;
import com.itechnews.service.PostService;
import com.itechnews.service.Result;
import com.itechnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/post")
public class PostApiController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;


    @PostMapping
    public ResponseEntity<Result> likeAPost(@RequestParam("postId") Integer postId, @RequestParam("liked") Boolean liked)
            throws Exception {
        User user = null;
        Result result = new Result();
        try {
            UserDetails userDetails = UserDetailsUtil.getUserDetails();
            user = userService.findOneById(((UserDetailsImpl) userDetails).getId());
        } catch (Exception e) {
            //e.printStackTrace();
        }
        if (user == null) {
            result.setStatus(Result.Status.SUCCESS);
            result.setMessage("authentication");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        Post post = postService.findOneById(postId);


        if (liked) { //unlike
            post.getLikedUsers().remove(user);
            postService.save(post);
        } else { //like
            if (post.getLikedUsers().contains(user)) {
                result.setMessage("Error");
            } else {
                post.getLikedUsers().add(user);
                postService.save(post);
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("total_post_likes", post.getLikedUsers().size());
        result.setData(data);
        result.setStatus(Result.Status.SUCCESS);
        result.setMessage("OK");


        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
