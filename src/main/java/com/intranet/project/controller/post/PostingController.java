package com.intranet.project.controller.post;

import com.intranet.project.controller.classes.ResponseJSON;
import com.intranet.project.repository.post.PostingEntity;
import com.intranet.project.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("posting")
public class PostingController {

    @Autowired
    private PostingService postingService;

    @PostMapping("/create")
    public void createPosting(@RequestBody PostingResponseFull postingResponseFull){
        postingService.createPosting(postingResponseFull);

    }

    @GetMapping("/list")
    public List<PostingResponseFull> getListOfPostings(){

        return postingService.getListOfPostings();
    }

    @GetMapping("/user/{id}")
    public List<PostingResponseFull> getUserPostings(@PathVariable("id") Long userId){
        return postingService.getUserPostings(userId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseJSON deletePostingById(@PathVariable("id") Long postId){
        return new ResponseJSON(postingService.deletePostingById(postId));
    }
    @DeleteMapping("/delete/userposts/{userId}")
    public void deleteUserPostings(@PathVariable("userId") Long userId){
        postingService.deleteUserPostings(userId);
    }
}
