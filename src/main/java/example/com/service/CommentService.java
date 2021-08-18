//package example.com.service;
//
//import example.com.entity.Comment;
//import example.com.entity.Post;
//import example.com.exeptions.PostNotFoundException;
//import example.com.dto.CommentDTO;
//import example.com.entity.UserApp;
//import example.com.repository.CommentRepository;
//import example.com.repository.PostRepository;
//import example.com.repository.UserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.security.Principal;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CommentService {
//    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);
//
//    private final CommentRepository commentRepository;
//    private final PostRepository postRepository;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
//        this.commentRepository = commentRepository;
//        this.postRepository = postRepository;
//        this.userRepository = userRepository;
//    }
//
//    public Comment saveComment(Long postId, CommentDTO commentDTO, Principal principal) {
//        UserApp user = getUserByPrincipal(principal);
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username: " + user.getEmail()));
//
//        Comment comment = new Comment();
//        comment.setPost(post);
//        comment.setUserId(user.getId());
//        comment.setUsername(user.getUsername());
//        comment.setMessage(commentDTO.getMessage());
//
//        LOG.info("Saving comment for Post: {}", post.getId());
//        return commentRepository.save(comment);
//    }
//
//    public List<Comment> getAllCommentsForPost(Long postId) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));
//        List<Comment> comments = commentRepository.findAllByPost(post);
//
//        return comments;
//    }
//
//    public void deleteComment(Long commentId) {
//        Optional<Comment> comment = commentRepository.findById(commentId);
//        comment.ifPresent(commentRepository::delete);
//    }
//
//
//    private UserApp getUserByPrincipal(Principal principal) {
//        String username = principal.getName();
//        return userRepository.findUserAppByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
//    }
//}
//
