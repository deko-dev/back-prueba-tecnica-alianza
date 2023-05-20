package com.example.pruebatecnicaalianza.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@Entity
@Table( name = "clients" )
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long clientId;
    @Column(unique = true, nullable = false)
    private String sharedKey;
    @Column(unique = true, nullable = false)
    private String businessId;
    @Column(unique = true, nullable = false)
    private String email;
    private String phone;
    private Date startDate;
    private Date endDate;
    private LocalDateTime createdAt;


    public Client() {
        this.createdAt = LocalDateTime.now();
    }

}
