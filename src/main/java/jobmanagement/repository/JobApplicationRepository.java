package jobmanagement.repository;

import jobmanagement.entity.JobApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    public void deleteByJobId(int jobId);

}
