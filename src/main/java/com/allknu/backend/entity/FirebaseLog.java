package com.allknu.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name="fcm_log")
@Entity
@Getter
@NoArgsConstructor
public class FirebaseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "link")
    private String link;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date timestamp = new Date();

    @Builder
    public FirebaseLog(String adminEmail, String title, String body, String link) {
        this.adminEmail = adminEmail;
        this.title = title;
        this.body = body;
        this.link = link;
    }
}
