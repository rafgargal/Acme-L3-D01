
package acme.entities.auditing;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditingRecord extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			subject;

	@NotBlank
	@Length(max = 100)
	protected String			assessment;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				auditPeriodInicial;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				auditPeriodFinal;

	@Enumerated(EnumType.STRING)
	protected Marks				mark;

	@URL
	protected String			link;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Audit				audit;

}
