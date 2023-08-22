package com.example.userlocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.userlocation.entity.UserLocation;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    List<UserLocation> findByExcludedFalse();
}
