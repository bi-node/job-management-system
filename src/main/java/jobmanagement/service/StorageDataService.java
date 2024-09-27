package jobmanagement.service;

import jobmanagement.entity.StorageData;
import jobmanagement.repository.StorageDataRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class StorageDataService {


    @Autowired
    private StorageDataRepository storageDataRepository;


    public StorageData uploadFile(MultipartFile file, String folderPath) throws IOException {
        // Check if the file is empty
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        // Build file path and save the file
        String filePath = folderPath + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        // Create StorageData object and save it
        StorageData storageData = StorageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build();

        return storageDataRepository.save(storageData);
    }


    public byte[] downloadFile(String fileName) throws IOException {
        Optional<StorageData> fileData = storageDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }

    public byte[] downloadFileById(int id) throws IOException {
        Optional<StorageData> fileData = storageDataRepository.findById(id);
        String filePath;
        if (fileData.isPresent()) {
            filePath = fileData.get().getFilePath();
        } else throw new IOException("File not found");


        return Files.readAllBytes(new File(filePath).toPath());
    }


    public StorageData getById(int id) {
        return storageDataRepository.findById(id).orElseThrow();
    }

    public void deleteFile(StorageData storageData) throws IOException {
        if (storageData != null) {
            // Delete the file from the file system
            File file = new File(storageData.getFilePath());
            if (file.exists()) {
                if (!file.delete()) {
                    throw new IOException("Failed to delete the file: " + file.getAbsolutePath());
                }
            }

            // Delete the record from the database
            storageDataRepository.delete(storageData);
        }
    }



}
