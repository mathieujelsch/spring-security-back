package com.cordernot.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cordernot.entities.Comment;
import com.cordernot.entities.Customer;
import com.cordernot.entities.Like;
import com.cordernot.entities.LikeComment;
import com.cordernot.entities.Publication;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long>{
    Optional<LikeComment> findByCommentAndCustomer(Comment comment, Customer customer);

    List<LikeComment> findByCommentId(Long commentId);
}
