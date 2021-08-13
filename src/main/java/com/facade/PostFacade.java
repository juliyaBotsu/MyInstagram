package com.facade;

import com.entity.Post;
import com.dto.PostDTO;
import org.springframework.stereotype.Component;

@Component
public class PostFacade {
    public PostDTO postToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setUsername(post.getUser().getUsername());
        postDTO.setId(post.getId());
        postDTO.setCapture(post.getCaption());
        postDTO.setLikes(post.getLikes());
        postDTO.setUserLiked(post.getLikedUsers());
        postDTO.setLocation(post.getLocation());
        postDTO.setTitle(post.getTitle());
        return postDTO;

    }
}
