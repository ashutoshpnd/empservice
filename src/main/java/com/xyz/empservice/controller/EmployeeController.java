package com.xyz.empservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.empservice.exception.EmployeeServiceException;
import com.xyz.empservice.exception.ResourceNotFoundException;
import com.xyz.empservice.model.Employee;
import com.xyz.empservice.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/employees")
@Slf4j
@CrossOrigin(origins = "*")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	ConcurrentMapCacheManager cacheManager;

	@GetMapping(produces = { "application/json", "application/xml" })
	public ResponseEntity<?> findAll() {
		if (employeeService.listEmployees().isEmpty()) {
			log.info("No Resource found.");
			ResponseEntity.ok("No Resource found.");
		}
		return ResponseEntity.ok(employeeService.listEmployees());
	}

	@PostMapping(produces = { "application/json", "application/xml" }, consumes = { "application/json",
			"application/xml" })
	public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) {
		if (employeeService.findByEmail(employee.getEmail()).isPresent())
			throw new EmployeeServiceException("Employee already exists!");
		return ResponseEntity.ok(employeeService.addEmployee(employee));
	}

	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
	@Cacheable(cacheNames = "employees", condition = "#id>=1")
	public ResponseEntity<Employee> findById(@PathVariable Integer id) throws Exception {
		return employeeService.getEmployeeById(id).map(e -> {
			return ResponseEntity.ok(e);
		}).orElseThrow(() -> new ResourceNotFoundException("Emp ID :: " + id + " does not exist."));
	}

	@PutMapping(value = "/{id}", consumes = { "application/json", "application/xml" })
	public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Employee employee) {
		return employeeService.getEmployeeById(id).map(e -> {
			employee.setEmpId(e.getEmpId());
			employeeService.addEmployee(employee);
			return ResponseEntity.noContent().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Emp ID :: " + id + " does not exist."));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		return employeeService.getEmployeeById(id).map(e -> {
			employeeService.removeEmployee(id);
			cacheManager.getCache("employees").evict(id);
			return ResponseEntity.noContent().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Emp ID :: " + id + " does not exist."));
		
	}

}
