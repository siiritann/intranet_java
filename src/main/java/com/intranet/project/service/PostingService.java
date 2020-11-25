package com.intranet.project.service;


import com.intranet.project.controller.post.PostingResponseFull;
import com.intranet.project.repository.post.PostingRepository;
import com.intranet.project.repository.user.UserRepository;
import com.intranet.project.repository.post.PostingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostingService {

    @Autowired
    private PostingRepository postingRepository;
    @Autowired
    private UserRepository userRepository;

    public PostingEntity postingResponseFullToEntity(PostingResponseFull postingResponseFull){
        PostingResponseFull newPosting = new PostingResponseFull(postingResponseFull.getUsername(), postingResponseFull.getHeading(), postingResponseFull.getBody());
        String username = newPosting.getUsername();
        Timestamp date = newPosting.getDate();
        String heading = newPosting.getHeading();
        String body = newPosting.getBody();
        Long userId = userRepository.getUserIdByUsername(username);
        return new PostingEntity(userId, date, heading, body);

    }
    public PostingResponseFull postingEntityToResponseFull(PostingEntity postingEntity){
        Long userId = postingEntity.getUserId();
        Timestamp date = postingEntity.getDate();
        String heading = postingEntity.getHeading();
        String body = postingEntity.getBody();
        String username = userRepository.getUsernameById(userId);
        return new PostingResponseFull(username, date, heading, body);
    }

    public void createPosting(PostingResponseFull postingResponseFull) {
        PostingEntity postingEntity = postingResponseFullToEntity(postingResponseFull);
        postingRepository.createPosting(postingEntity);
    }

    public List<PostingResponseFull> getListOfPostings() {
        List<PostingEntity> postingEntityList = postingRepository.getListOfPostings();
        List<PostingResponseFull> postingResponseFullList = new ArrayList<>();
        for(PostingEntity item: postingEntityList){
            PostingResponseFull postingResponseFull = postingEntityToResponseFull(item);
            postingResponseFullList.add(postingResponseFull);
        }
        return postingResponseFullList;
    }

}
