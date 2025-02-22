package com.example.rea4e.domain.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.rea4e.domain.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID>{

    //save
    //delete
    //findById
    //findAll
    //count
    //existsById
    
    @Query(value="SELECT * FROM CLIENT WHERE CLIENT_ID=:clientId", nativeQuery = true)
    Client findByClientId(String clientId);
}
