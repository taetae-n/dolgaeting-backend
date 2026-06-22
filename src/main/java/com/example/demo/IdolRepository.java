package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IdolRepository extends JpaRepository<Idol, Long> {
    List<Idol> findByGender(String gender);
}