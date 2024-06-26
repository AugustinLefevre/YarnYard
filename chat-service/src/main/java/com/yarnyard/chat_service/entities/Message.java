package com.yarnyard.chat_service.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    @Column(name = "sent_at")
    private Timestamp sentAt;
    @Column(name = "sender_id")
    private String senderId;
    @Column(name = "sender_name")
    private String senderName;

}
