package com.sathish.reactapplication.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sathish.reactapplication.domain.ProjectTask;
import com.sathish.reactapplication.service.MapValidationErrorService;
import com.sathish.reactapplication.service.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin(origins = "http://localhost:3000")
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@PathVariable String backlog_id, @Valid @RequestBody ProjectTask projectTask, BindingResult result, Principal principal){
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if(errorMap!=null) {
			return errorMap;
		}
		return new ResponseEntity<ProjectTask>(projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName()),HttpStatus.CREATED);
	}
	@GetMapping("/{backlog_id}")
	public ResponseEntity<?> getProjectBacklog(@PathVariable String backlog_id, Principal principal){
		return new ResponseEntity<List<ProjectTask>>(projectTaskService.findAllProjectTaskByIdentifier(backlog_id, principal.getName()), HttpStatus.OK);		
	}
	
	@GetMapping("/{backlog_id}/{sequence}")
	public ResponseEntity<?> getProjectTaskBySequence(@PathVariable String backlog_id, @PathVariable String sequence, Principal principal){
		ProjectTask projectTask = projectTaskService.findProjectTaskBySequence(backlog_id, sequence, principal.getName());	
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}
	
	@PatchMapping("/{backlog_id}/{sequence}")
	public ResponseEntity<?> updateProjectTaskBySequence(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, @PathVariable String sequence, Principal principal){
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if(errorMap!=null) {
			return errorMap;
		}
		ProjectTask updateProjectTask = projectTaskService.updateProjectTaskBySequence(projectTask, backlog_id, sequence, principal.getName());
		return new ResponseEntity<ProjectTask>(updateProjectTask, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{backlog_id}/{sequence}")
	public ResponseEntity<?> deleteProjectTaskBySequence(@PathVariable String backlog_id, @PathVariable String sequence, Principal principal){
		projectTaskService.deleteProjectTaskBySequence(backlog_id, sequence, principal.getName());	
		return new ResponseEntity<String>("Project Task '"+sequence.toUpperCase()+"' deleted successfully", HttpStatus.OK);
	}
}
