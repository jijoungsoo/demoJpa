package com.example.demo.db.domain.mig_av;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class MigAvMvActrIdx implements Serializable {
	@Id
	@Column(nullable = false,unique=true ,name="dvd_idx")
	long dvdIdx;

	@Id
	@Column(nullable = true,unique=false, name="actor_idx")
	long actrIdx;
}
