package com.example.model.demo.repo;

import com.example.model.demo.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
