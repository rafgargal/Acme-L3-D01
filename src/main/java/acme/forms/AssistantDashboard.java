
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalTutorials;

	Double						averageSessionTime;

	Double						deviationSessionTime;

	Double						minimumSessionTime;

	Double						maximumSessionTime;

	Double						averageTutorialTime;

	Double						deviationTutorialTime;

	Double						minimumTutorialTime;

	Double						maximumTutorialTime;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
