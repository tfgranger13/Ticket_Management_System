package com.tfg.tms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CustomerMessageDTO {

	private String content;
	private Integer ticketId;

}
