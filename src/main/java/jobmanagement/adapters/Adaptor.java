package jobmanagement.adapters;

import jobmanagement.entity.Job;
import jobmanagement.dto.JobResponse;


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
                .jobApplicationDate(job.getJobApplicationDate())
                .interviewDate(job.getInterviewDate())
                .resumeId(job.getResume()!=null?job.getResume().getId():-1)
                .coverLetterId(job.getCoverLetter()!=null?job.getCoverLetter().getId():-1)
                .otherDocumentId(job.getOtherDocument()!=null?job.getOtherDocument().getId():-1)
                .build();
        return jobResponse;

    }



}
