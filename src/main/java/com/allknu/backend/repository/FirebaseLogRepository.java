package com.allknu.backend.repository;

import com.allknu.backend.domain.FirebaseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirebaseLogRepository extends JpaRepository<FirebaseLog, Long> {
}
