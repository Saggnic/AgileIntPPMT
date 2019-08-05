package com.agileintelligence.ppmtool.service;

import com.agileintelligence.ppmtool.domain.Backlog;
import com.agileintelligence.ppmtool.domain.Project;
import com.agileintelligence.ppmtool.domain.User;
import com.agileintelligence.ppmtool.exception.ProjectIdException;
import com.agileintelligence.ppmtool.exception.ProjectNotFoundExeption;
import com.agileintelligence.ppmtool.repository.BacklogRepository;
import com.agileintelligence.ppmtool.repository.ProjectRepository;
import com.agileintelligence.ppmtool.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private UserRepository userRepo;
	

	public Project saveOrUpdateProject(Project project,String username) {
		
		//if the project is existing check if the user is authorized
		if(project.getId()!=null) {
			Project existingProject =projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			
			if(existingProject!=null && 
					!(existingProject.getProjectLeader().equalsIgnoreCase(username))) {
				throw new ProjectNotFoundExeption("Project not found in your account");
			}else if(existingProject==null) {
				throw new ProjectNotFoundExeption("project "+project.getProjectIdentifier()
						+ " which you are trying to update cannot be found");
			}
		}
		
		try {
			
			User user= userRepo.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(username.toUpperCase());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

			
			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

			}

			//if project is existing then update the project and set the new backlog
			else {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));

			}

			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
		}

	}

	public Project findProjectByIdentifier(String projectId,String username) {

		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if (project == null) {
			throw new ProjectIdException("Project ID '" + projectId + "' does not exist");
		}
		
		
		if(!project.getProjectLeader().equalsIgnoreCase(username)) {
			throw new ProjectNotFoundExeption("Project doesnot belong to your account");
		}

		return project;
	}

	public Iterable<Project> findAllProjects(String username) {
		
		return projectRepository.findAllByProjectLeader(username);
		
		//return projectRepository.findAll();
	}

	public void deleteProjectByIdentifier(String projectid,String username) {
		/*
		 * Project project =
		 * projectRepository.findByProjectIdentifier(projectid.toUpperCase());
		 * 
		 * if (project == null) { throw new
		 * ProjectIdException("Cannot Project with ID '" + projectid +
		 * "'. This project does not exist"); }
		 */

		projectRepository.delete(findProjectByIdentifier(projectid,username));
	}

}