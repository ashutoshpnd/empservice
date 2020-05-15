package com.xyz.empservice.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Embeddable 
@Access(AccessType.FIELD)
public class Address {
@Column
@Size(min=20,max=50)
@NotNull
private String addressLine1;
@Column
@NotNull
@Size(min=20,max=50)
private String addressLine2;
@Column
private String addressLine3;

}
