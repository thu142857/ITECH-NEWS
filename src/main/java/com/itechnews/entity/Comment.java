package com.itechnews.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //https://stackoverflow.com/questions/30334044/hibernate-same-table-parent-child-relation
    @Transient
    private Integer parentId;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name="parent_id")
    private Comment parent;

    @OneToMany(mappedBy="parent", fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Comment> children = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user; //The user who commented this comment


    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_id")
    private Post post;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "status")
    private Boolean status;

    @Column(columnDefinition = "TEXT")
    private String content;
}