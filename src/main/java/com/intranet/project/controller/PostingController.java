package com.intranet.project.controller;

import com.intranet.project.controller.post.PostingResponseFull;
import com.intranet.project.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
