package jobmanagement.controller;


import jobmanagement.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/jobApplications")
public class JobApplicationController {
    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<?> saveJobApplication(
            @RequestParam int jobId,
            @RequestParam(value = "resume", required = false) MultipartFile resume,
            @RequestParam(value = "coverLetter", required = false) MultipartFile coverLetter,
            @RequestParam(value = "otherDocument", required = false) MultipartFile otherDocument,
            @RequestParam("appDate")LocalDate appDate,
            @RequestParam("intDate")LocalDate intDate
            ) throws IOException {

    return ResponseEntity.status(HttpStatus.OK).body(jobApplicationService.saveOrUpdate(
            jobId,appDate,intDate,resume,coverLetter,otherDocument));
    }


    @GetMapping("/allPaged")
    public ResponseEntity<?> getAllJobApplications(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "1") int size) throws IOException {
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.status(HttpStatus.OK).body(jobApplicationService.getAllJobApplications(pageable));
    }

}
