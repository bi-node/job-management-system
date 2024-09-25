package jobmanagement.service;

import jakarta.transaction.Transactional;
import jobmanagement.entity.Job;
import jobmanagement.repository.JobRepository;
import jobmanagement.repository.StorageDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
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

    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    public Job findById(int id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isPresent()) {
            return job.get();
        }
        throw new RuntimeException("Job ID not found");
    }

    public Job saveJob(Job job, MultipartFile file) throws IOException {
        try {
            storageDataService.uploadImageToFileSystem(file);
        }
        catch (Exception e) {
            throw new IOException(e.getMessage());
        }
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

   public Page<Job> findJobByTitleContaining(String title, Pageable pageable) {
       return jobRepository.findAllByTitleContainingIgnoreCase(title,pageable);
   }

   public Page<Job> findAllJobPage(Pageable pageable) {
        return jobRepository.findAll(pageable);
   }


}
