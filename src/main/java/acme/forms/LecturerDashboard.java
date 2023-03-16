
package acme.forms;

import acme.framework.data.AbstractForm;

public class LecturerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						theoryLectures;

	Integer						handsOnLectures;

	Double						averageLTLectures;

	Double						deviationLTLectures;

	Double						minimumLTLectures;

	Double						maximumLTLectures;

	Double						averageLTCourses;

	Double						deviationLTCourses;

	Double						minimumLTCourses;

	Double						maximumLTCourses;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
