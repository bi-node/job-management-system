package jobmanagement.controller;

import jobmanagement.entity.StorageData;
import jobmanagement.service.StorageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/jobs/storage-data")
public class StorageDataController {
    @Autowired
    private StorageDataService storageDataService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) throws IOException {
        byte[] imageData=storageDataService.downloadFileById(id);
        String contentType=storageDataService.getById(id).getType();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(contentType))
                .body(imageData);
    }
}
