package com.example.vendor.controller;

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
    @Qualifier("vendorJob")
    Job vendorKeeperJob;

    @GetMapping("/run-vendor-job")
    public String handle() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("source", "Adding vendors")
                .toJobParameters();
        jobLauncher.run(vendorKeeperJob, jobParameters);

        return "Batch job has been invoked";
    }
}
