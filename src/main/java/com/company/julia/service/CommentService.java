package com.company.julia.service;

import com.company.julia.dto.CommentDTO;
import com.company.julia.entity.Comment;
import com.company.julia.entity.Post;
import com.company.julia.entity.UserApp;
import com.company.julia.exeptions.PostNotFoundException;
import com.company.julia.repository.CommentRepository;
import com.company.julia.repository.PostRepository;
import com.company.julia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class CommentService {
    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Comment saveComment(Long postId, CommentDTO commentDTO, Principal principal){
            UserApp user = getUserByPrincipal(principal);
            Post post = postRepository.findById(postId)
                    .orElseThrow(()->new PostNotFoundException("Post not found"));

            Comment comment =  new Comment();
            comment.setPost(post);
            comment.setUserId(user.getId());
            comment.setUsername(user.getUsername());
            comment.setMessage(commentDTO.getMessage());
            LOG.info("saving comment");
            return commentRepository.save(comment);

    }

    public List<Comment> getAllCommentFromPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException("post not found"));
        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments;
    }
    private UserApp getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserAppByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User can not be found"));
    }
}
