package jobmanagement.service;

import jobmanagement.adapters.Adaptor;
import jobmanagement.dto.JobApplicationResponse;
import jobmanagement.entity.Job;
import jobmanagement.entity.JobApplication;
import jobmanagement.entity.StorageData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jobmanagement.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class JobApplicationService {
    private final String RESUME_FOLDER_PATH="E:/Job Mission/MyFileSystem/resumes/";
    private final String CL_FOLDER_PATH="E:/Job Mission/MyFileSystem/coverletters/";
    private final String OTHER_FOLDER_PATH="E:/Job Mission/MyFileSystem/otherdocuments/";
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private StorageDataService storageDataService;


    public JobApplication saveOrUpdate(
            int jobId,
            LocalDate jobApplicationDate,
            LocalDate interviewDate,
            MultipartFile resume,
            MultipartFile coverLetter,
            MultipartFile otherDocument

    ) throws IOException {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobId(jobId);
        // Handle optional file uploads for each document
        if (resume != null && !resume.isEmpty()) {
            StorageData resumeData = storageDataService.uploadFile(resume,RESUME_FOLDER_PATH);
            jobApplication.setResume(resumeData);
        }
        if (coverLetter != null && !coverLetter.isEmpty()) {
            StorageData coverLetterData = storageDataService.uploadFile(coverLetter,CL_FOLDER_PATH);
            jobApplication.setCoverLetter(coverLetterData);
        }
        if (otherDocument != null && !otherDocument.isEmpty()) {
            StorageData otherDocumentData = storageDataService.uploadFile(otherDocument,OTHER_FOLDER_PATH);
            jobApplication.setOtherDocument(otherDocumentData);
        }
        jobApplication.setJobApplicationDate(jobApplicationDate);
        jobApplication.setInterviewDate(interviewDate);


        return jobApplicationRepository.save(jobApplication);
    }

    public Page<JobApplicationResponse> getAllJobApplications(Pageable pageable) throws IOException {
        List<JobApplication> jobApplications = jobApplicationRepository.findAll();
        List<JobApplicationResponse> allJobApplicationResponses = new ArrayList<>();
        for(JobApplication jobApplication : jobApplications){
            allJobApplicationResponses.add(Adaptor.JobAppToJobApplicationResponse(jobApplication));
        }
        return new PageImpl<>(allJobApplicationResponses,pageable,jobApplications.size());


    }

}
