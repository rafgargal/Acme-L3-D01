
package acme.entities.course;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.ActivityType;
import acme.entities.lecture.Lecture;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			cAbstract;

	@NotNull
	protected boolean			draftMode;

	@NotNull
	protected Money				retailPrice;

	@URL
	protected String			furtherInfo;

	// Derived attributes -----------------------------------------------------


	@Transient
	protected ActivityType getActivityType(final List<Lecture> lectures) {
		ActivityType courseType = ActivityType.BALANCED;
		int handsOnLectures = 0;
		int theoreticalLectures = 0;
		if (!lectures.isEmpty())
			for (final Lecture l : lectures) {
				final ActivityType lectureType = l.getActivityType();
				if (lectureType.equals(ActivityType.HANDS_ON))
					handsOnLectures++;
				else
					theoreticalLectures++;
			}
		if (handsOnLectures > theoreticalLectures)
			courseType = ActivityType.HANDS_ON;
		else if (handsOnLectures < theoreticalLectures)
			courseType = ActivityType.THEORETICAL;
		return courseType;
	}

	// Relationships ----------------------------------------------------------


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Lecturer lecturer;

}
