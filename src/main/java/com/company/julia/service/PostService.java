package com.company.julia.service;

import com.company.julia.dto.PostDTO;
import com.company.julia.entity.ImageModel;
import com.company.julia.entity.Post;
import com.company.julia.entity.UserApp;
import com.company.julia.exeptions.PostNotFoundException;
import com.company.julia.repository.ImageRepository;
import com.company.julia.repository.PostRepository;
import com.company.julia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);
    private PostRepository postRepository;
    private UserRepository userRepository;
    private ImageRepository imageRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public Post createPost(PostDTO postDTO, Principal principal) {
        UserApp user = getUserByPrincipal(principal);
        Post post = new Post();
        post.setUser(user);
        post.setCaption(postDTO.getCapture());
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);
        LOG.info("Saving post to database");
        return postRepository.save(post);
    }

    public Post getPostById(Long postId, Principal principal) {
        UserApp user = getUserByPrincipal(principal);
        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("Post can not be found for this username"));
    }

    public List<Post> getAllPostsFromUser(Principal principal) {
        UserApp user = getUserByPrincipal(principal);
        return postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Post likePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post can not be found"));

        Optional<String> userLiked = post.getLikedUsers()
                .stream().filter(u -> u.equals(username)).findAny();

        if(userLiked.isPresent()){
            post.setLikes(post.getLikes()-1);
            post.getLikedUsers().remove(username);
        }else {
            post.setLikes(post.getLikes()+1);
            post.getLikedUsers().add(username);
        }

        return postRepository.save(post);
    }

    public void deletePost(Long postId,Principal principal){
        Post post = getPostById(postId,principal);
        Optional<ImageModel> imageModel = imageRepository.findByPostId(post.getId());
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository::delete);
    }

    private UserApp getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserAppByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
