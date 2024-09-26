package jobmanagement.adapters;

import jobmanagement.dto.JobApplicationResponse;
import jobmanagement.entity.Job;
import jobmanagement.dto.JobResponse;
import jobmanagement.entity.JobApplication;


import java.io.IOException;


public  class Adaptor {

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

    public static JobApplicationResponse JobAppToJobApplicationResponse(JobApplication jobApplication) throws IOException {
        JobApplicationResponse jobApplicationResponse;
        jobApplicationResponse= JobApplicationResponse.builder()
                .id(jobApplication.getId())
                .jobId(jobApplication.getJobId())
                .jobApplicationDate(jobApplication.getJobApplicationDate())
                .interviewDate(jobApplication.getInterviewDate())
                .resumeId(jobApplication.getResume()!=null?jobApplication.getResume().getId():-1)
                .coverLetterId(jobApplication.getCoverLetter()!=null?jobApplication.getCoverLetter().getId():-1)
                .otherDocumentId(jobApplication.getOtherDocument()!=null?jobApplication.getOtherDocument().getId():-1)
        .build();
        return jobApplicationResponse;
    }


}
