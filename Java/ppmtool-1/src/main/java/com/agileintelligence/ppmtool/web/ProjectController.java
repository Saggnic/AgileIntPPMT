package com.agileintelligence.ppmtool.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agileintelligence.ppmtool.domain.Project;
import com.agileintelligence.ppmtool.service.MapValidationErrorService;
import com.agileintelligence.ppmtool.service.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

		/*
		 * if(result.hasErrors()) { //return new
		 * ResponseEntity<String>("Invalid Project Object",HttpStatus.BAD_REQUEST);
		 * Map<String,String> errorMap=new HashMap<>(); for(FieldError error:
		 * result.getFieldErrors()) { errorMap.put(error.getField(),
		 * error.getDefaultMessage());
		 * 
		 * }
		 * 
		 * 
		 * return new ResponseEntity<Map<String,String>>(errorMap,
		 * HttpStatus.BAD_REQUEST); }
		 */

		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidatinService(result);
		if (errorMap != null) {
			return errorMap;
		}

		Project project1 = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> findProjectByIdentifier(@PathVariable String projectId) {
		Project project = projectService.fingProjectByIdentifier(projectId);
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Project> getAllProjects() {
		return projectService.findAllProjects();
	}

	@DeleteMapping("/{projectIdentifier}")
	public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectIdentifier) {
		projectService.deleteProjectByIdentifier(projectIdentifier);
		return new ResponseEntity<String>("Project " + projectIdentifier + " was deleted", HttpStatus.OK);
	}
}
