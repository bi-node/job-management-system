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
    private StorageDataRepository fileDataRepository;

//    private final String FOLDER_PATH="/Users/javatechie/Desktop/MyFIles/";

    private final String FOLDER_PATH = "E:/MyFiles/";



    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath=FOLDER_PATH+file.getOriginalFilename();

        StorageData fileData=fileDataRepository.save(StorageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        return "file uploaded successfully : " + filePath;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<StorageData> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }



}
