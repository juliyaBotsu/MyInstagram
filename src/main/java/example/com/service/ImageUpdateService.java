package example.com.service;


import example.com.entity.ImageModel;
import example.com.entity.Post;
import example.com.entity.UserApp;
import example.com.exeptions.ImageNotFoundException;
import example.com.repository.ImageRepository;
import example.com.repository.PostRepository;
import example.com.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageUpdateService {
    public static final Logger LOG = LoggerFactory.getLogger(ImageUpdateService.class);

    @Autowired
    private  ImageRepository imageRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PostRepository postRepository;


    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
        } catch (DataFormatException e) {
            LOG.error("Can not decompress bytes");
        }
        return outputStream.toByteArray();
    }

    public ImageModel uploadImageModelToUser(MultipartFile file, Principal principal) throws IOException {
        UserApp user = getUserByPrincipal(principal);
        LOG.info("Uploading image profile to User {} " + user.getUsername());
        ImageModel userProfileImage = imageRepository.findByUserId(user.getId())
                .orElse(null);
        if (!ObjectUtils.isEmpty(userProfileImage)) {
            imageRepository.delete(userProfileImage);
        }

        ImageModel imageModel = new ImageModel();
        imageModel.setUserId(user.getId());
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        imageModel.setName(file.getOriginalFilename());
        return imageRepository.save(imageModel);
    }

    public ImageModel getImageToUser(Principal principal) {
        UserApp user = getUserByPrincipal(principal);
        ImageModel imageModel = imageRepository.findByUserId(user.getId())
                .orElse(null);
        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));

        }
        return imageModel;
    }

    public ImageModel getImageToPost(Long postId) {
        ImageModel imageModel = imageRepository.findByPostId(postId)
                .orElseThrow(() -> new ImageNotFoundException("Con not find image to post"));
        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));

        }
        return imageModel;

    }

    public ImageModel uploadImageToPost(MultipartFile file, Principal principal, Long postId) throws IOException {
        UserApp user = getUserByPrincipal(principal);
        Post post = user.getPosts()
                .stream().filter(n -> n.getId().equals(postId))
                .collect(toSinglePostCollector());
        ImageModel imageModel = new ImageModel();
        imageModel.setPostId(post.getId());
        imageModel.setImageBytes(file.getBytes());
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        imageModel.setName(file.getOriginalFilename());
        LOG.info("upload image to post {}", post.getId());
        return imageRepository.save(imageModel);
    }

    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            LOG.error("can not compress");
        }
        System.out.println("compressed image bytes size");
        return outputStream.toByteArray();
    }

    private UserApp getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserAppByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User can not be found"));
    }

    private <T> Collector<T, ?, T> toSinglePostCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }

        );
    }
}
