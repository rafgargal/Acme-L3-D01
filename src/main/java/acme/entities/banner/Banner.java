
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@Past
	protected Date				instantiationOrUpdateMoment;

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date				displayPeriodStart;

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date				displayPeriodEnd;

	@URL
	@NotBlank
	protected String			pictureLink;

	@NotBlank
	@Length(max = 75)
	protected String			slogan;

	@NotBlank
	@URL
	protected String			webDocLink;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
