package com.intranet.project.controller.post;

import com.intranet.project.ResponseJSON;
import com.intranet.project.controller.post.PostingResponseFull;
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


    @DeleteMapping("/delete")
    public ResponseJSON deletePostingById(@RequestBody PostingEntity postingEntity){
        return new ResponseJSON(postingService.deletePostingById(postingEntity.getId()));
    }

}
