package com.agileintelligence.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agileintelligence.ppmtool.domain.Project;
import com.agileintelligence.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	
	public Project saveOrUpdateProject(Project project) {
		//Logic
		
		return projectRepository.save(project);
		
	}
	
}
