package com.intranet.project.service;


import com.intranet.project.controller.post.PostingResponseFull;
import com.intranet.project.repository.PostingRepository;
import com.intranet.project.repository.UserRepository;
import com.intranet.project.repository.post.PostingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class PostingService {

    @Autowired
    private PostingRepository postingRepository;
    @Autowired
    private UserRepository userRepository;

    public void createPosting(PostingResponseFull postingResponseFull) {
        String username = postingResponseFull.getUsername();
        Timestamp date = postingResponseFull.getDate();
        String heading = postingResponseFull.getHeading();
        String body = postingResponseFull.getBody();
        Long userId = userRepository.getUserIdByUsername(username);
        PostingEntity postingEntity = new PostingEntity(userId, date, heading, body);
        postingRepository.createPosting(postingEntity);
    }
}
