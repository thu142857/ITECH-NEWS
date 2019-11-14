package com.itechnews.controller.api;

import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import com.itechnews.security.UserDetailsImpl;
import com.itechnews.security.UserDetailsUtil;
import com.itechnews.service.CommentService;
import com.itechnews.service.PostService;
import com.itechnews.service.Result;
import com.itechnews.service.UserService;
import com.itechnews.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @GetMapping("load-more-top-post")
    public ResponseEntity<Result> loadMoreTopPosts(@RequestParam("page") Integer page) {
        page++;
        Page<Post> postPage = postService.findTopPosts(page);
        Map<String, Object> data = getPostData(postPage);

        Result result = new Result();
        result.setData(data);
        result.setStatus(Result.Status.SUCCESS);
        result.setMessage("OK");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("load-more-new-post")
    public ResponseEntity<Result> loadMoreNewPosts(@RequestParam("page") Integer page) {
        page++;
        Page<Post> postPage = postService.findNewPosts(page);
        Map<String, Object> data = getPostData(postPage);

        Result result = new Result();
        result.setStatus(Result.Status.SUCCESS);
        result.setData(data);
        result.setMessage("OK");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private Map<String, Object> getPostData(Page<Post> postPage) {
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> dataPosts = new ArrayList<>();
        List<Post> posts = postPage.getContent();
        for (Post post : posts) {
            Map<String, Object> dataPost = new HashMap<>();
            dataPost.put("id", post.getId());
            dataPost.put("title", post.getTitle());
            dataPost.put("slug", post.getSlug());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dataPost.put("created_at", sdf.format(post.getCreateAt()));
            dataPost.put("sub_content", StringUtil.getThreeDotsTextFromHtml(post.getContent(), 160));

            Map<String, Object> dataAuthor = new HashMap<>();
            dataAuthor.put("username", post.getPostedUser().getUsername());
            dataAuthor.put("image", post.getPostedUser().getImage());
            dataPost.put("author", dataAuthor);

            List<Map<String, Object>> dataTags = new ArrayList<>();
            post.getTags().forEach(tag -> {
                Map<String, Object> dataTag = new HashMap<>();
                dataTag.put("slug", tag.getSlug());
                dataTag.put("name", tag.getName());
                dataTags.add(dataTag);
            });
            dataPost.put("tags", dataTags);

            dataPosts.add(dataPost);
        }
        data.put("posts", dataPosts);
        return data;
    }
}
