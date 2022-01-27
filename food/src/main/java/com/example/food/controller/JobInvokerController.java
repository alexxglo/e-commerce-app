package com.example.food.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobInvokerController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("foodJob")
    Job foodKeeperJob;

    @GetMapping("/run-food-job")
    public String handle() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("source", "Adding food")
                .toJobParameters();
        jobLauncher.run(foodKeeperJob, jobParameters);

        return "Batch job has been invoked";
    }
}
