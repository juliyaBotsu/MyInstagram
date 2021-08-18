//package example.com.repository;
//
//import example.com.entity.CommentApp;
//import example.com.entity.PostApp;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ComRepository extends JpaRepository<CommentApp,Long> {
//    List<CommentApp> findAllByPost(PostApp postApp);
//    CommentApp findByIdAndUserId(Long commentId, Long userId);
//}
