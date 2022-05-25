//package me.jaeyeopme.sns.entity.post;
//
//import java.util.List;
//import java.util.Set;
//import javax.persistence.CascadeType;
//import javax.persistence.Embedded;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import me.jaeyeopme.sns.entity.base.EntityBase;
//import me.jaeyeopme.sns.entity.hashTag.HashTag;
//import me.jaeyeopme.sns.entity.interaction.Comment;
//import me.jaeyeopme.sns.entity.interaction.Like;
//import me.jaeyeopme.sns.entity.user.User;
//
//@Getter
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//public class Post extends EntityBase {
//
//    @Embedded
//    private Caption caption = new Caption();
//
//    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
//    private List<HashTag> hashTags;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(nullable = false)
//    private User owner;
//
//    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "post")
//    private List<Media> medias;
//
//    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "post")
//    private List<Comment> comments;
//
//    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//    private Set<Like> likes;
//
//}
