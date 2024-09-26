package jobmanagement.adapters;

import jobmanagement.entity.Job;
import jobmanagement.entity.JobResponse;


import java.io.IOException;


public  class JobAdapter {

    public static JobResponse JobToJobResponse(Job job) throws IOException {
        JobResponse jobResponse;
        jobResponse = JobResponse.builder()
                .id(job.getId())
                .jobIdNo(job.getJobIdNo())
                .title(job.getTitle())
                .companyName(job.getCompanyName())
                .email(job.getEmail())
                .hiringManager(job.getHiringManager())
                .hiringManagerPhoneNumber(job.getHiringManagerPhoneNumber())
                .address(job.getAddress())
                .jobDescriptionFileId(job.getJobDescription().getId())
                .build();
        return jobResponse;

    }
}
