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
public class MigAvMvActrMainIdx implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -9000356169488989425L;

	@Id
	@Column(nullable = false,unique=false ,name="dvd_idx")
	long dvdIdx;

	@Id
	@Column(nullable = false,unique=false, name="actor_idx")
	long actrIdx;
}
