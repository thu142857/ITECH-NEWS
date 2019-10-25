package com.itechnews.database.seed;

import com.github.javafaker.Faker;
import com.itechnews.entity.Comment;
import com.itechnews.entity.Post;
import com.itechnews.entity.User;
import com.itechnews.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class CommentsTableSeeder implements Seeder {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TagRepository tagRepository;

    @Autowired
    Faker faker;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public void run() {
        List<User> authors = userRepository.findByIdLessThan(6);
        Random random = new Random();
        List<Post> posts = (List<Post>) postRepository.findAll();

        for (int k = 0; k < posts.size(); k++) { //loop over list of posts
            Post post = posts.get(k);
            Date date = post.getCreateAt();
            int limit = random.nextInt(6) + 1;
            for (int i = 0; i < limit; i++) { //loop over list of parent comments
                User author = authors.get(random.nextInt(5));
                Date d = getDate(date, 10 + i * 2);
                Comment parentComment = new Comment(null, null, null, null,
                        author, post, d, true, faker.lorem().paragraph(1));
                parentComment = commentRepository.save(parentComment);

                List<Comment> allChildComments = new ArrayList<>();
                int limit2 = random.nextInt(6);
                for (int j = 0; j < limit2; j++) { ////loop over list of child comments
                    Date dd = getDate(d, 5 + j * 2);
                    User commentUser = authors.get(random.nextInt(5));
                    Comment childComment = new Comment(null, parentComment.getId(), parentComment, null,
                            commentUser, post, dd, true, faker.lorem().paragraph(1));
                    allChildComments.add(childComment);
                }
                commentRepository.saveAll(allChildComments);
            }
        }
    }

    private Date getDate(Date date, int minutes) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
        long t = date.getTime();
        return new Date(t + (minutes * ONE_MINUTE_IN_MILLIS));
    }
}
