package dev.nozturk.userservice.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class RequestLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "objectid")
	@JsonIgnore
	private Integer id;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Column(length = 100)
	private String username;
	
	@Column(length = 100, nullable = false)
	private String resource;

	@Lob
	private String requestBody;

	private String queryParams;
}
