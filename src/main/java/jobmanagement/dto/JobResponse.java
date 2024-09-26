package jobmanagement.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobResponse {
    private int id;
    private String jobIdNo;
    private String title;
    private String companyName;
    private String address;
    private String email;
    private String hiringManager;
    private String hiringManagerPhoneNumber;
    private int jobDescriptionFileId;
}
