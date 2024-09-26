package jobmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jobmanagement.entity.Job;
import jobmanagement.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/add-job")
    public ResponseEntity<?> addJob(
            @RequestParam("job") String jobJson,  // JSON string for Job
            @RequestParam("jdfile") MultipartFile jdFile // File upload
    ) throws IOException {
        // Convert the JSON string to a Job object
        ObjectMapper objectMapper = new ObjectMapper();
        Job job = objectMapper.readValue(jobJson, Job.class);

        // Save the job and file
        Job newJob = jobService.saveJob(job, jdFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(newJob);
    }



    @GetMapping
    public ResponseEntity<?> getAllJobs() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.findById(id));
    }

    @PutMapping
    public ResponseEntity<?> updateJob(@RequestBody Job job) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.update(job));
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable int id) {
        jobService.deleteJob(id);
    }

    @GetMapping("/jobs-page")
    public ResponseEntity<?> getJobsPage( @RequestParam(defaultValue = "0") int page,
                                          @RequestParam (defaultValue = "10") int size) {
        Pageable pageable=PageRequest.of(page, size);
        Page<Job> jobsPage=jobService.findAllJobPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(jobsPage);

    }



}
