package jobmanagement.repository;

import jobmanagement.entity.StorageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageDataRepository extends JpaRepository<StorageData,Integer> {
    Optional<StorageData> findByName(String fileName);
}