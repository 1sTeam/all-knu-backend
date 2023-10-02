package com.allknu.backend.fcmapi.domain;

import com.allknu.backend.fcmapi.domain.FirebaseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirebaseLogRepository extends JpaRepository<FirebaseLog, Long> {
}
