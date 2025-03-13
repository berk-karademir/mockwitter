package com.BekoInc.mockwitter.serviceImplementation;

import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.CommentLike;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.repository.CommentLikeRepository;
import com.BekoInc.mockwitter.repository.CommentRepository;
import com.BekoInc.mockwitter.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentLikeServiceImpl(CommentLikeRepository commentLikeRepository, CommentRepository commentRepository) {
        this.commentLikeRepository = commentLikeRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void likeComment(Long commentId, User user) {
        // Comment objesini veritabanından çek
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        // Kullanıcının daha önce bu comment'i beğenip beğenmediğini kontrol et
        if (!commentLikeRepository.existsByCommentAndUser(comment, user)) {
            CommentLike commentLike = new CommentLike();
            commentLike.setComment(comment);
            commentLike.setUser(user);
            commentLikeRepository.save(commentLike);
        }
    }

    @Override
    public void undoLikeComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        if (commentLikeRepository.existsByCommentAndUser(comment, user)) {
            commentLikeRepository.existsByCommentAndUser(comment, user);
        } else {
            throw new RuntimeException("You cannot undo a comment like you haven't liked.");
        }
    }
}
