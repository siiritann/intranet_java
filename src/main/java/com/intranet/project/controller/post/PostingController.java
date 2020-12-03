package com.intranet.project.controller.post;

import com.intranet.project.controller.classes.ResponseJSON;
import com.intranet.project.repository.post.PostingEntity;
import com.intranet.project.repository.user.UserEntity;
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
    public PostingResponseFull createPosting(@RequestBody PostingResponseFull postingResponseFull){
        return postingService.createPosting(postingResponseFull);
    }

    @PutMapping("/update")
    public void updatePosting(@RequestBody PostingResponseFull postingResponseFull){
        postingService.updatePosting(postingResponseFull);
    }

    @GetMapping("/list")
    public List<PostingResponseFull> getListOfPostings(){

        return postingService.getListOfPostings();
    }
    @GetMapping("/view/{id}")
    public PostingResponseFull getPosting(@PathVariable("id") Long postingId){
        return postingService.getPosting(postingId);
    }

    @GetMapping("/user/{username}")
    public List<PostingResponseFull> getUserPostings(@PathVariable("username") String username){
        return postingService.getPostingsListByUsername(username);
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
