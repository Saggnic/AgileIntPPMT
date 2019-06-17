package com.agileintelligence.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agileintelligence.ppmtool.domain.Project;
import com.agileintelligence.ppmtool.exception.ProjectIdException;
import com.agileintelligence.ppmtool.exception.ProjectIdentifierNotFoundException;
import com.agileintelligence.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdateProject(Project project) {
		// Logic

		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);

		} catch (Exception e) {
			throw new ProjectIdException(
					"Project Id" + project.getProjectIdentifier().toUpperCase() + "already exists");
		}

	}

	public Project fingProjectByIdentifier(String projectIdentifier) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

		if (project == null) {
			throw new ProjectIdentifierNotFoundException("Project ID " + projectIdentifier + "does not exist");

		}
		return project;
	}

	public Iterable<Project> findAllProjects() {
		return projectRepository.findAll();
	}

	public void deleteProjectByIdentifier(String projectIdentifier) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

		if (project == null) {
			throw new ProjectIdentifierNotFoundException(
					"Project ID " + projectIdentifier + " does not exist, hence cannot be deleted");

		}

		projectRepository.delete(project);

	}

}
