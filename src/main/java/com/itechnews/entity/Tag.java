package com.itechnews.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
//@ToString //StackOverflowException
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @Size(min = 1, max = 50)
    private String slug;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "post_tag",
            joinColumns = {
                    @JoinColumn(name = "tag_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "post_id")
            },
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"tag_id", "post_id"})
            }
    )
    private List<Post> posts;

    @Column(name = "status")
    private Boolean status;

    @Override
    public String toString() {
        return "";
    }
}