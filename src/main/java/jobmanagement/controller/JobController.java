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
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobService jobService;


    @PostMapping("/add-job")
    public ResponseEntity<?> addJob(
            @RequestParam("jobIdNo") String jobIdNo,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "companyName") String companyName,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "hiringManagerName") String hiringManagerName,
            @RequestParam(value = "hiringManagerPhoneNumber") String hiringManagerPhoneNumber,
            @RequestParam(value = "applicationDate", required = false) LocalDate applicationDate,
            @RequestParam(value = "interviewDate", required = false) LocalDate interviewDate,
            @RequestParam(value = "jobDescription" ,required = false)MultipartFile jobDescription,
            @RequestParam(value = "resume" ,required = false)MultipartFile resume,
            @RequestParam(value = "coverLetter" ,required = false)MultipartFile coverLetter,
            @RequestParam(value = "otherDocument" ,required = false)MultipartFile otherDocument

    ) throws IOException {

        Job newJob = jobService.saveJob(jobIdNo,title,companyName,address,email,hiringManagerName
                ,hiringManagerPhoneNumber,applicationDate,interviewDate,jobDescription,resume,
                coverLetter,otherDocument);
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
    public void deleteJob(@PathVariable int id) throws IOException {
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
