package com.itechnews.controller.api;

import com.itechnews.entity.Comment;
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
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/comment")
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Result> addComment(@RequestParam("content") String content,
                                             @RequestParam(value = "parentCommentId", required = false) Integer parentCommentId,
                                             @RequestParam("postId") Integer postId) throws Exception {
        UserDetails userDetails = UserDetailsUtil.getUserDetails();
        User user = userService.findOneById(((UserDetailsImpl) userDetails).getId());
        Comment parentComment = null;
        if (parentCommentId != null) {
            parentComment = commentService.findOneById(parentCommentId);
        }
        Post post = postService.findOneById(postId);
        Comment comment = new Comment(null, null, parentComment, null,
                user, post, new Date(), true, content);
        Comment newComment = commentService.save(comment);

        Result result = new Result();
        result.setStatus(Result.Status.SUCCESS);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        Map<String, Object> data = new HashMap<>();
        data.put("id", newComment.getId());
        data.put("content", newComment.getContent());
        data.put("user_id", newComment.getUser().getId());
        data.put("parent_comment_id", parentCommentId);
        data.put("created_at", format.format(newComment.getCreateAt()));
        result.setData(data);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Result> deleteComment(@RequestParam(value = "commentId") Integer commentId) throws Exception {

        Comment comment = commentService.findOneById(commentId);
        if (comment.getParent() == null) {
            commentService.deleteByParentId(commentId);

        }
        commentService.deleteById(commentId);

        Result result = new Result();
        result.setStatus(Result.Status.SUCCESS);
        Map<String, Object> data = new HashMap<>();
        result.setMessage("OK");
        result.setData(data);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Result> editComment(@RequestParam("content") String content,
                                             @RequestParam(value = "commentId") Integer commentId) throws Exception {
        Comment comment = commentService.findOneById(commentId);
        comment.setContent(content);
        comment = commentService.save(comment);

        Result result = new Result();
        result.setStatus(Result.Status.SUCCESS);
        Map<String, Object> data = new HashMap<>();
        data.put("id", comment.getId());
        data.put("content", comment.getContent());
        result.setData(data);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
