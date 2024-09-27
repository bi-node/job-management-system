package jobmanagement.service;

import jakarta.transaction.Transactional;
import jobmanagement.adapters.Adaptor;
import jobmanagement.entity.Job;
import jobmanagement.dto.JobResponse;
import jobmanagement.entity.StorageData;
import jobmanagement.repository.JobRepository;
import jobmanagement.repository.StorageDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StorageDataRepository storageDataRepository;
    @Autowired
    private StorageDataService storageDataService;

    private final String FOLDER_PATH="E:/Job Mission/MyFileSystem/jds/";
    private final String RESUME_FOLDER_PATH="E:/Job Mission/MyFileSystem/resumes/";
    private final String CL_FOLDER_PATH="E:/Job Mission/MyFileSystem/coverletters/";
    private final String OTHER_FOLDER_PATH="E:/Job Mission/MyFileSystem/otherdocuments/";



    public List<JobResponse> findAll() throws IOException {
        List<Job> jobs = jobRepository.findAll();
        List<JobResponse> jobResponses=new ArrayList<>();

        for(Job job:jobs){
            jobResponses.add(Adaptor.JobToJobResponse(job));
        }
        return jobResponses;

    }

    public Job findById(int id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isPresent()) {
            return job.get();
        }
        throw new RuntimeException("Job ID not found");
    }

    @Transactional
    public Job saveJob(String jobIdNo,
                       String title,
            String companyName,
            String address,
            String email,
            String hiringManagerName,
            String hiringManagerPhoneNumber,
            LocalDate applicationDate,
            LocalDate interviewDate,
            MultipartFile jobDescription,
            MultipartFile resume,
            MultipartFile coverLetter,
            MultipartFile otherDocument) throws IOException {
        Job job = new Job();
        job.setJobIdNo(jobIdNo);
        job.setTitle(title);
        job.setCompanyName(companyName);
        job.setAddress(address);
        job.setEmail(email);
        job.setHiringManager(hiringManagerName);
        job.setHiringManagerPhoneNumber(hiringManagerPhoneNumber);
        job.setJobApplicationDate(applicationDate);
        job.setInterviewDate(interviewDate);
        // Handle the file upload and retrieve the storage data
        StorageData storageData = storageDataService.uploadFile(jobDescription, FOLDER_PATH);
        job.setJobDescription(storageData);

        if (resume != null && !resume.isEmpty()) {
            StorageData resumeData = storageDataService.uploadFile(resume,RESUME_FOLDER_PATH);
            job.setResume(resumeData);
        }
        if (coverLetter != null && !coverLetter.isEmpty()) {
            StorageData coverLetterData = storageDataService.uploadFile(coverLetter,CL_FOLDER_PATH);
            job.setCoverLetter(coverLetterData);
        }
        if (otherDocument != null && !otherDocument.isEmpty()) {
            StorageData otherDocumentData = storageDataService.uploadFile(otherDocument,OTHER_FOLDER_PATH);
            job.setOtherDocument(otherDocumentData);
        }
        // Associate the file information with the job

        return jobRepository.save(job);
    }


    public void deleteJob(int id) {
        jobRepository.deleteById(id);
    }

    public Job update(Job job) {
        Job updateJob=Job.builder().
                jobIdNo(job.getJobIdNo())
                .title(job.getTitle())
                .address(job.getAddress())
                .email(job.getEmail())
                .companyName(job.getCompanyName())
                .hiringManager(job.getHiringManager())
                .hiringManagerPhoneNumber(job.getHiringManagerPhoneNumber())
                .jobApplicationDate(job.getJobApplicationDate())
                .interviewDate(job.getInterviewDate())
                .jobDescription(job.getJobDescription())
                .resume(job.getResume())
                .coverLetter(job.getCoverLetter())
                .otherDocument(job.getOtherDocument())
                .build();
        return jobRepository.save(job);
    }


   public Page<Job> findAllJobPage(Pageable pageable) {
        return jobRepository.findAll(pageable);
   }


}
