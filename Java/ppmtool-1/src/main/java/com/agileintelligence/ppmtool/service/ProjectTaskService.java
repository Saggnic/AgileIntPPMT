package com.agileintelligence.ppmtool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agileintelligence.ppmtool.domain.Backlog;
import com.agileintelligence.ppmtool.domain.Project;
import com.agileintelligence.ppmtool.domain.ProjectTask;
import com.agileintelligence.ppmtool.exception.ProjectIdException;
import com.agileintelligence.ppmtool.exception.ProjectIdentifierNotFoundException;
import com.agileintelligence.ppmtool.exception.ProjectNotFoundExeption;
import com.agileintelligence.ppmtool.repository.BacklogRepository;
import com.agileintelligence.ppmtool.repository.ProjectRepository;
import com.agileintelligence.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectService projectService; 

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask,String username) {

		// All Project tasks to be added to a specific project i.e project !=null
		// If project is null, BackLog also exists
		// set the backlog to the project task which sets the relationship
		// Set the project-sequence , which should be ever increasing even on deleting
		// smthng
		// update the backlog sequence
		// Add sequence to project task
		// INITIAL priority when priority is null
		// Same for status

		try {
			//Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

			//after JWT
			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, 
					username).getBacklog();
			
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
		} catch (Exception e) {
			throw new ProjectIdException("Project ID doesnot  exists");
		}
		return projectTaskRepository.save(projectTask);

	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id, String username) {
		Project project = projectRepository.findByProjectIdentifier(backlog_id);

		projectService.findProjectByIdentifier(backlog_id, username);
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);

	}

	public ProjectTask findPTBySequence(String backlog_id, String pt_id,String username) {

		/*
		 * Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		 * 
		 * if (backlog == null) { throw new ProjectNotFoundExeption("project with id '"
		 * + backlog_id + "' doesnot exist"); }
		 */
		
		projectService.findProjectByIdentifier(backlog_id, username); 

		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

		if (projectTask == null) {
			throw new ProjectNotFoundExeption("project with id '" + pt_id + "' doesnot exist");
		}
		if (!projectTask.getProjectIdentifier().equalsIgnoreCase(backlog_id)) {

			throw new ProjectNotFoundExeption(
					"Backlog_id '" + backlog_id + "' doesnot contain" + "project task '" + pt_id + "'");
		}

		return projectTask;

	}

	public ProjectTask updateProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id,
			String username) {

		ProjectTask projectTask = findPTBySequence(backlog_id, pt_id,username);
		// ProjectTask
		// projectTask=projectTaskRepository.findByProjectSequence(updatedTask.getProjectSequence());

		projectTask = updatedTask;
		return projectTaskRepository.save(projectTask);

	}

	public void deletePTByProjectSequence(String backlog_id, String pt_id,String username) {
		
		ProjectTask projectTask = findPTBySequence(backlog_id, pt_id,username);
		projectTaskRepository.delete(projectTask);

		
	}
	
}
