package com.xyz.empservice.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name = "Employee")
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int empId;
	@Column(length = 20)
	@Size(min = 2, max = 20)
	@NotNull
	private String firstName;
	@Column(length = 20)
	@Size(min = 2, max = 20)
	@NotNull
	private String lastName;
	@Column
	@NotNull
	private String email;
	@Column(length = 10)
	@NotNull
	private String phone;
	@Column(length = 1)
	@NotNull
	private String gender;
	@Column
	@NotNull
	private LocalDate dateOfBirth;//yyyy-MM-dd
	@Column(length = 20)
	@Size(min = 5, max = 20)
	@NotNull
	private String designation;
	@Embedded
    private Address address;
	@Column(length = 10)
	@NotNull
	private double salary;
	@Column
	@CreationTimestamp
	private LocalDate dateOfJoining;//yyyy-MM-dd
	
	
}
