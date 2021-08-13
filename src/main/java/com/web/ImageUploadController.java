package com.web;

import com.entity.ImageModel;
import com.payload.response.MessageResponse;
import com.service.ImageUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;


@RestController
@CrossOrigin
@RequestMapping("api/image")
public class ImageUploadController {
    @Autowired
    private ImageUpdateService imageUpdateService;


    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException {
        imageUpdateService.uploadImageModelToUser(file, principal);

        return ResponseEntity.ok(new MessageResponse("Image was uploaded successfully"));

    }

    @PostMapping("/{postId}/upload")
    public ResponseEntity<MessageResponse> uploadImageToPost(@PathVariable("postId") String postId,
                                                             @RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        imageUpdateService.uploadImageToPost(file, principal, Long.parseLong(postId));
        return ResponseEntity.ok(new MessageResponse("Image was uploaded successfully for post "));
    }

    @GetMapping("/profileImage")
    public ResponseEntity<ImageModel> getImageForUser(Principal principal) {
        ImageModel imageModel = imageUpdateService.getImageToUser(principal);
        return new ResponseEntity<>(imageModel, HttpStatus.OK);
    }

    @GetMapping("/{postId}/image")
    public ResponseEntity<ImageModel> getImageToPost(@PathVariable("postId") String postId) {
        ImageModel imageModel = imageUpdateService.getImageToPost(Long.parseLong(postId));
        return new ResponseEntity<>(imageModel, HttpStatus.OK);
    }

}
