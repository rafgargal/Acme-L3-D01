
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Size(max = 75)
	protected String			firm;

	@NotBlank
	@Size(max = 25)
	protected String			profesional_ID;

	@NotBlank
	@Size(max = 100)
	protected String			list_Certification;

	@URL
	protected String			link;

}
