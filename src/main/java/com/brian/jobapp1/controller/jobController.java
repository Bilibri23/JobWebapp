package com.brian.jobapp1.controller;

import com.brian.jobapp1.model.JobPost;
import com.brian.jobapp1.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class jobController {
    @Autowired
    private JobService service;

    @RequestMapping(value = {"/JobPosts","/"})
    public List<JobPost> getJobPosts(){
        return  service.getAllJobs();
    }

    @GetMapping("JobPost/{postId}")
    public JobPost getJobPost( @PathVariable int postId){
        return service.getJob(postId);
    }

    @PostMapping("Post")
    public JobPost PostJob(@RequestBody JobPost jobPost){
         service.addJob(jobPost);
         return service.getJob(jobPost.getPostId());
    }
    @DeleteMapping("deletePost/{postId}")
    public  void DeleteJob(@PathVariable int postId){
        service.deleteJob(postId);
    }

    @PutMapping("JobPost")
    public  JobPost updateJob(@RequestBody JobPost jobPost){
        service.updateJob(jobPost);
        return service.getJob(jobPost.getPostId());
    }

    @GetMapping("JobPosts/keyword/{keyword}")
    public List<JobPost> searchByKeyword(@PathVariable("keyword") String keyword){
        return service.search(keyword);
    }


    @GetMapping("load")
    public  String loadData(){
        service.load();
        return "success";
    }

}
