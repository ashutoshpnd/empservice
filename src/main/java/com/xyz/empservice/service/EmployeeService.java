package com.xyz.empservice.service;

import java.util.List;
import java.util.Optional;

import com.xyz.empservice.model.Employee;

public interface EmployeeService {

	Optional<Employee> findByEmail(String email);

	Employee addEmployee(Employee emp);

	void updateEmployee(Employee emp);

	List<Employee> listEmployees();

	Optional<Employee> getEmployeeById(int id);

	void removeEmployee(int id);

}
