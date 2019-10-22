package com.itechnews.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Column(length = 100, name = "displayed_name")
    private String displayedName;

    @Column(nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private Boolean status;

    private String email;

    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;


    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.MERGE}, mappedBy = "likedUsers")
    private List<Post> likedPosts;


    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "follows",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "follower_id")
            },
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"user_id", "follower_id"})
            }
    )
    private List<User> follower; //users who are following me

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.MERGE}, mappedBy = "follower")
    private List<User> following; //users who I'am following


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
    private PasswordResetToken passwordResetToken;

}