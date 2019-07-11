package com.agileintelligence.ppmtool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agileintelligence.ppmtool.domain.Backlog;
import com.agileintelligence.ppmtool.domain.ProjectTask;
import com.agileintelligence.ppmtool.repository.BacklogRepository;
import com.agileintelligence.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		// All Project tasks to be added to a specific project i.e project !=null
		// If project is null, BackLog also exists
		// set the backlog to the project task which sets the relationship
		// Set the project-sequence , which should be ever increasing even on deleting
		// smthng
		// update the backlog sequence
		// Add sequence to project task
		// INITIAL priority when priority is null
		// Same for status

		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

		projectTask.setBacklog(backlog);

		Integer backlogSequence = backlog.getPTSequence();
		backlogSequence++;
		backlog.setPTSequence(backlogSequence);

		projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);

		if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
			projectTask.setPriority(3);
		}

		if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
			projectTask.setStatus("TO DO");
		}

		return projectTaskRepository.save(projectTask);

	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);

	}

}
