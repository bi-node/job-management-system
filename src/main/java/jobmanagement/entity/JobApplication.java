package jobmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Job job;

    @Temporal(TemporalType.DATE)
    private LocalDate jobApplicationDate;

    @Temporal(TemporalType.DATE)
    private LocalDate interviewDate;

    @OneToOne
    private StorageData resume;

    @OneToOne
    private StorageData coverLetter;

    @OneToMany
    private List<StorageData> otherDocument;

}
