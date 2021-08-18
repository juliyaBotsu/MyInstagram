//package example.com.web;
//
//import example.com.dto.CommentDTO;
//import example.com.entity.CommentApp;
//import example.com.facade.CommentFacade;
//import example.com.payload.response.MessageResponse;
//import example.com.service.ComService;
//import example.com.validations.ResponseErrorValidation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.ObjectUtils;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.security.Principal;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("api/comment")
//@CrossOrigin
//public class ComController {
//    @Autowired
//    private ComService comService;
//    @Autowired
//    private CommentFacade commentFacade;
//    @Autowired
//    private ResponseErrorValidation responseErrorValidation;
//
//    @PostMapping("/{postId}/create")
//    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDTO commentDTO,
//                                                @PathVariable("postId") String postId,
//                                                BindingResult bindingResult,
//                                                Principal principal) {
//        ResponseEntity<Object> errors = responseErrorValidation.mapValidatorService(bindingResult);
//        if (!ObjectUtils.isEmpty(errors)) return errors;
//
//        CommentApp commentApp = comService.saveComment(Long.parseLong(postId), commentDTO, principal);
//        CommentDTO createdComment = commentFacade.commentToCommentDTO(commentApp);
//
//        return new ResponseEntity<>(createdComment, HttpStatus.OK);
//    }
//
//    @GetMapping("/{postId}/all")
//    public ResponseEntity<List<CommentDTO>> getAllCommentsToPost(@PathVariable("postId") String postId) {
//        List<CommentDTO> commentDTOList = comService.getAllCommentsForPost(Long.parseLong(postId))
//                .stream()
//                .map(commentFacade::commentToCommentDTO)
//                .collect(Collectors.toList());
//
//        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
//    }
//
//    @PostMapping("/{commentId}/delete")
//    public ResponseEntity<MessageResponse> deleteComment(@PathVariable("commentId") String commentId) {
//        comService.deleteComment(Long.parseLong(commentId));
//        return new ResponseEntity<>(new MessageResponse("Post was deleted"), HttpStatus.OK);
//    }
//}
