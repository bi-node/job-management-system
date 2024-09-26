package jobmanagement.service;

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

    public Job saveJob(Job job, MultipartFile file) throws IOException {
        // Handle the file upload and retrieve the storage data
        StorageData storageData = storageDataService.uploadFile(file, FOLDER_PATH);

        // Associate the file information with the job
        job.setJobDescription(storageData);
        return jobRepository.save(job);
    }


    public void deleteJob(int id) {
        jobRepository.deleteById(id);
    }

    public Job update(Job job) {
        Job updateJob=Job.builder().jobIdNo(job.getJobIdNo())
                .title(job.getTitle())
                .address(job.getAddress())
                .email(job.getEmail())
                .companyName(job.getCompanyName())
                .hiringManager(job.getHiringManager())
                .hiringManagerPhoneNumber(job.getHiringManagerPhoneNumber())
                .jobDescription(job.getJobDescription())
                .build();
        return jobRepository.save(job);
    }


   public Page<Job> findAllJobPage(Pageable pageable) {
        return jobRepository.findAll(pageable);
   }


}
