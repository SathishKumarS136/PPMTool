package com.sathish.reactapplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sathish.reactapplication.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	Project findByProjectIdentifier(String projectIdentifier);
	
	List<Project> findAllByProjectLeader(String username);
	
	Project findByProjectIdentifierAndProjectLeader(String projectIdentifier, String username);
	
}
