package com.tenpo.api.callhistory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "call_history")
@Getter
@Setter
@NoArgsConstructor
public class CallHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime timestamp;

	private String endpoint;

	@Column(columnDefinition = "TEXT")
	private String requestParams;

	@Column(columnDefinition = "TEXT")
	private String responseData;

	private boolean error;
}
