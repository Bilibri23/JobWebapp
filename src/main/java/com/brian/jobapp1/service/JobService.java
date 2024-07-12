package com.brian.jobapp1.service;

import com.brian.jobapp1.model.JobPost;
import com.brian.jobapp1.repo.JobRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepo repo;



    public  void addJob(JobPost jobPost){
        repo.save(jobPost); //send data to repo layer
    }
    public List<JobPost> getAllJobs(){
        return repo.findAll(); // get data from repo
    }

    public JobPost getJob(int postId) {
        return repo.findById(postId).orElse(new JobPost());
    }
    public void deleteJob(int postId){
        repo.deleteById(postId);
    }

    public void updateJob(JobPost jobPost) {
        repo.save(jobPost);
    }


    //preloading default data in the database
    @PostConstruct  // spring calls it automatically after beans initialization so no need to call it manually
    public void load() {
        List<JobPost> jobs = new ArrayList<>(List.of(
                new JobPost(1, "java developer", "need to work on orm", 8,  List.of("Game Development", "Unity", "C#", "3D Modeling")),
                new JobPost(2, "IT Project Manager", "Lead and manage IT projects from initiation to completion",6, List.of("Project Management", "Agile", "Scrum", "Risk Management")),
                new JobPost(3, "Game Developer", "Create and optimize games for various platforms", 3,
                        List.of("Game Development", "Unity", "C#", "3D Modeling"))
        ));
        //repo.save(jobPost);
        repo.saveAll(jobs);



    }

    public List<JobPost> search(String keyword) {
       return repo.findByPostProfileContainingOrPostDescContaining(keyword, keyword);
    }
}
