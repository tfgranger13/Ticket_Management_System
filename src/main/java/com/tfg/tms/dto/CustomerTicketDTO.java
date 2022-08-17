package com.tfg.tms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CustomerTicketDTO {

	private Integer departmentId;
	private String detail;
	private String priority;

}
