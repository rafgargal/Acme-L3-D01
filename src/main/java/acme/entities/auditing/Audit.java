
package acme.entities.auditing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	protected String	code;

	@NotBlank
	@Length(max = 100)
	protected String	conclusion;

	@NotBlank
	@Length(max = 100)
	protected String	weakPoints;

	@NotBlank
	@Length(max = 100)
	protected String	strongPoints;

	@Enumerated(EnumType.STRING)
	protected Marks		mark;

}
