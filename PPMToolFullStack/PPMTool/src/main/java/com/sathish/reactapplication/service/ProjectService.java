package com.sathish.reactapplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sathish.reactapplication.domain.Backlog;
import com.sathish.reactapplication.domain.Project;
import com.sathish.reactapplication.domain.User;
import com.sathish.reactapplication.exception.ProjectIdException;
import com.sathish.reactapplication.exception.ProjectNotFoundException;
import com.sathish.reactapplication.repositories.BacklogRepository;
import com.sathish.reactapplication.repositories.ProjectRepository;
import com.sathish.reactapplication.repositories.UserRepository;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private UserRepository userRepository;

	@CacheEvict(value = "projects", allEntries = true)
//	@CachePut(value = "projects")
	public Project saveOrUpdateProject(Project project, String username) {
		try {
			User user = userRepository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
				backlog.setProject(project);
				project.setBacklog(backlog);
			}
			if (project.getId() != null) {
				Optional<Project> optional = projectRepository.findById(project.getId());
				if (optional.isPresent()) {
					String identifier = optional.get().getProjectIdentifier();
					if (projectRepository.findByProjectIdentifierAndProjectLeader(identifier, username) == null)
						throw new ProjectNotFoundException(
								identifier.toUpperCase() + " doesn't exist on your account");
					else
						project.setBacklog(backlogRepository.findByProjectIdentifier(identifier.toUpperCase()));

				}

			}
			return projectRepository.save(project);
		} catch (ProjectNotFoundException e) {
			throw new ProjectIdException(
					project.getProjectIdentifier() + " doesn't exist on your account");
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
		}
	}

	public Project findByProjectIdentifier(String projectIdentifier, String username) {
		Project project = projectRepository.findByProjectIdentifierAndProjectLeader(projectIdentifier.toUpperCase(),
				username);
		if (project != null)
			return project;
		throw new ProjectIdException("Project ID '" + projectIdentifier.toUpperCase() + "' doesn't exists");
	}

	@Cacheable(value = "projects")
	public List<Project> findAllProjects(String username) {
		return projectRepository.findAllByProjectLeader(username);
	}

	@CacheEvict(value = "projects", allEntries = true)
	public void deleteByProjectIdentifier(String projectIdentifier, String username) {
		Project project = projectRepository.findByProjectIdentifierAndProjectLeader(projectIdentifier.toUpperCase(),
				username);
		if (project != null) {
			projectRepository.deleteById(project.getId());
		} else {
			throw new ProjectIdException("Project ID '" + projectIdentifier.toUpperCase() + "' doesn't exists");
		}
	}
}
