package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdolTagRepository extends JpaRepository<IdolTag, Long> {
    List<IdolTag> findByIdolId(Long idolId);
}