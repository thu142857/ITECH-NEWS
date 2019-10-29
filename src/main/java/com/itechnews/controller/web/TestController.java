package com.itechnews.controller.web;

import com.github.javafaker.Faker;
import com.itechnews.entity.Comment;
import com.itechnews.entity.Tag;
import com.itechnews.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    Faker faker;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/1")
    @ResponseBody
    public String test() {

        commentRepository.deleteById(105);
        return "";
    }

    @GetMapping("/3")
    @ResponseBody
    public String test3() {
        //Post post = postRepository.findOnePost();
        List<Comment> comments = commentRepository.findByParentIsNullAndPost_IdOrderByCreateAtDesc(1);
        //commentRepository.deleteAll();
        return "";
    }

    @GetMapping("/2")
    @ResponseBody
    public String test2() {
//        List<User> authors = userRepository.findByIdLessThan(6);
//        Random random = new Random();
//        List<Post> posts = (List<Post>) postRepository.findAll();
//
//        for (int k = 0; k < posts.size(); k++) { //loop over list of posts
//            Post post = posts.get(k);
//            Date date = post.getCreateAt();
//            int limit = random.nextInt(6) + 1;
//            List<Comment> allPostComments = new ArrayList<>();
//            for (int i = 0; i < limit; i++) { //loop over list of parent comments
//                User author = authors.get(random.nextInt(5));
//                Date d = getDate(date, 10 + i * 2);
//                Comment parentComment = new Comment(null, null, null, null,
//                        author, post, d, true, faker.lorem().paragraph(1));
//                parentComment = commentRepository.save(parentComment);
//
//                List<Comment> allChildComments = new ArrayList<>();
//                int limit2 = random.nextInt(6);
//                for (int j = 0; j < limit2; j++) { ////loop over list of child comments
//                    Date dd = getDate(d, 5 + j * 2);
//                    User commentUser = authors.get(random.nextInt(5));
//                    Comment childComment = new Comment(null, parentComment.getId(), parentComment, null,
//                            commentUser, post, dd, true, faker.lorem().paragraph(1));
//                    allChildComments.add(childComment);
//                }
//                commentRepository.saveAll(allChildComments);
//                allPostComments.add(parentComment);
//            }
//            commentRepository.saveAll(allPostComments);
//        }
        return "";
    }

//    public Date getDate(Date date, int minutes) {
//        final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
//        long t= date.getTime();
//        return new Date(t + (minutes * ONE_MINUTE_IN_MILLIS));
//    }
}
