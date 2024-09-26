package jobmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String jobIdNo;

    private String title;

    @NotNull
    private String companyName;

    private String address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Ensures jobDescription is automatically saved with Job
    @JoinColumn(name = "job_description_id") // Link to StorageData entity
    private StorageData jobDescription;

    private String email;
    private String hiringManager;
    private String hiringManagerPhoneNumber;
}
