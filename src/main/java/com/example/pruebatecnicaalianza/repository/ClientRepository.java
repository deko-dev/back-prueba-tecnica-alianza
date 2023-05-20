package com.example.pruebatecnicaalianza.repository;

import com.example.pruebatecnicaalianza.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(
            value = "SELECT * FROM clients WHERE business_id = :business_id OR phone = :phone OR email = :email OR start_date = :start_date OR end_date = :end_date",
            nativeQuery = true
    )
    public List<Client> findByAttr(
            @Param("business_id") String business_id,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("start_date") Date start_date,
            @Param("end_date") Date end_date
    );

    @Query(
            value = "UPDATE clients SET shared_key = :shared_key, email = :email, business_id = :business_id, start_date = :start_date, end_date = :end_date, phone = :phone WHERE client_id = :client_id",
            nativeQuery = true
    )
    @Modifying
    public int updateClient(
            @Param("shared_key") String shared_key,
            @Param("business_id") String business_id,
            @Param("email") String email,
            @Param("start_date") Date start_date,
            @Param("end_date") Date end_date,
            @Param("phone") String phone,
            @Param("client_id") Long client_id
    );

    @Query(
            value = "SELECT * FROM clients WHERE shared_key = :shared_key",
            nativeQuery = true
    )
    public List<Client> findClientBySharedKey(@Param("shared_key") String shared_key);
}
