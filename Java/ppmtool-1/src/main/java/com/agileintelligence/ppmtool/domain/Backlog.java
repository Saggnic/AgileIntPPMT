package com.agileintelligence.ppmtool.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Backlog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Integer PTSequence = 0;
	private String projectIdentifier;

	// One to one with a specific project
	// One to many with the Project task
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id", nullable = false)
	@JsonIgnore
	private Project project;
	
	
	//one to many projectTasks
	@OneToMany(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER ,mappedBy = "backlog",orphanRemoval = true)
	private List<ProjectTask> projectTask=new ArrayList<>();//initialize in declaration itself.helps
	

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Backlog() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getPTSequence() {
		return PTSequence;
	}

	public void setPTSequence(Integer pTSequence) {
		PTSequence = pTSequence;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public List<ProjectTask> getProjectTask() {
		return projectTask;
	}

	public void setProjectTask(List<ProjectTask> projectTask) {
		this.projectTask = projectTask;
	}

}
