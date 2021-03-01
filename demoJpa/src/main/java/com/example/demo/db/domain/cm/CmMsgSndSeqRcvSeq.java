package com.example.demo.db.domain.cm;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CmMsgSndSeqRcvSeq implements Serializable {	

	@Id
	@Column(nullable = false, name = "snd_seq")
	long sndSeq;

	@Id
	@Column(nullable = false, name = "rcv_seq")
	long rcvSeq;
}
