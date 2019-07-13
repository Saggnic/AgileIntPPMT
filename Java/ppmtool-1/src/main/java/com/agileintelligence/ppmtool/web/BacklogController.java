package com.agileintelligence.ppmtool.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agileintelligence.ppmtool.domain.ProjectTask;
import com.agileintelligence.ppmtool.service.MapValidationErrorService;
import com.agileintelligence.ppmtool.service.ProjectService;
import com.agileintelligence.ppmtool.service.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private MapValidationErrorService mapValidationService;

	// backlogId=ProjectId: Since e are persisting our backlog into an existing
	// project
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
			@PathVariable String backlog_id) {

		ResponseEntity<?> errorMap = mapValidationService.mapValidatinService(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}

		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);

		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

	}

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectbacklog(@PathVariable String backlog_id) {

		return projectTaskService.findBacklogById(backlog_id);

	}
	
	@GetMapping("{backlog_id}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id,
			@PathVariable String pt_id ){
		
		ProjectTask projectTask= projectTaskService.findPTBySequence(backlog_id,pt_id); 
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
		
	}

}
