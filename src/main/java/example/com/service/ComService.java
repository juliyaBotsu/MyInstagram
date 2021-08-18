//package example.com.service;
//
//import example.com.dto.CommentDTO;
//import example.com.entity.CommentApp;
//import example.com.entity.PostApp;
//import example.com.entity.UserApp;
//import example.com.exeptions.PostNotFoundException;
//import example.com.repository.ComRepository;
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
//public class ComService {
//    public static final Logger LOG = LoggerFactory.getLogger(ComService.class);
//
//    private final ComRepository commentRepository;
//    private final PostRepository postRepository;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public ComService(ComRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
//        this.commentRepository = commentRepository;
//        this.postRepository = postRepository;
//        this.userRepository = userRepository;
//    }
//    public CommentApp saveComment(Long postId, CommentDTO commentDTO, Principal principal) {
//        UserApp user = getUserByPrincipal(principal);
//        PostApp postApp = postRepository.findById(postId)
//                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username: " + user.getEmail()));
//
//        CommentApp commentApp = new CommentApp();
//        commentApp.setPostApp(postApp);
//        commentApp.setUserId(user.getId());
//        commentApp.setUsername(user.getUsername());
//        commentApp.setMessage(commentDTO.getMessage());
//
//        LOG.info("Saving comment for Post: {}", postApp.getId());
//        return commentRepository.save(commentApp);
//    }
//
//    public List<CommentApp> getAllCommentsForPost(Long postId) {
//        PostApp postApp = postRepository.findById(postId)
//                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));
//        List<CommentApp> commentApps = commentRepository.findAllByPost(postApp);
//
//        return commentApps;
//    }
//
//    public void deleteComment(Long commentId) {
//        Optional<CommentApp> comment = commentRepository.findById(commentId);
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
