package jobmanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class JobApplicationResponse {
    private int id;
    private int jobId;
    private LocalDate jobApplicationDate;
    private LocalDate interviewDate;
    private int resumeId;
    private int coverLetterId;
    private int otherDocumentId;
}
