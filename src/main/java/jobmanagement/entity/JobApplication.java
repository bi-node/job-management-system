package jobmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int jobId;

    @Temporal(TemporalType.DATE)
    private LocalDate jobApplicationDate;

    @Temporal(TemporalType.DATE)
    private LocalDate interviewDate;

    @OneToOne
    @JoinColumn(name="resume_id")
    private StorageData resume;

    @OneToOne
    @JoinColumn(name="cover_letter_id")
    private StorageData coverLetter;

    @OneToOne
    @JoinColumn(name="other_document_id")
    private StorageData otherDocument;

}
