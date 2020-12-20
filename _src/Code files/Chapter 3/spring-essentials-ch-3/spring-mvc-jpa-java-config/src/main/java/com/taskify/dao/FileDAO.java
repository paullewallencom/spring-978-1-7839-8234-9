package com.taskify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskify.domain.File;

public interface FileDAO extends JpaRepository<File, Long> {
	
}
