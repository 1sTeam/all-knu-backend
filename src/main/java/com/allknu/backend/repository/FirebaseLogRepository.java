package com.allknu.backend.repository;

import com.allknu.backend.entity.FirebaseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirebaseLogRepository extends JpaRepository<FirebaseLog, Long> {
}
