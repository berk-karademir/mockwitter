package com.BekoInc.mockwitter.mapper;

import com.BekoInc.mockwitter.dto.ReplyDTO;
import com.BekoInc.mockwitter.dto.UserDTO;
import com.BekoInc.mockwitter.entity.Reply;
import com.BekoInc.mockwitter.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class ReplyMapper {

    // Reply entity'sini ReplyDTO'ya dönüştürme
    public ReplyDTO toDTO(Reply reply) {
        if (reply == null) {
            return null;
        }

        ReplyDTO dto = new ReplyDTO();
        dto.setId(reply.getId());
        dto.setContent(reply.getContent());
        dto.setReplyDate(reply.getReplyDate());
        dto.setUser (reply.getUser () != null ? toUserDTO(reply.getUser ()) : null);
        dto.setComment_id(reply.getComment().getId()); // İlgili comment ID'sini ekle

        return dto;
    }

    // User entity'sini UserDTO'ya dönüştürme
    private UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(user.getId(), user.getUsername(), user.getLastName());
    }
}