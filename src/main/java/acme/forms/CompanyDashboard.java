
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	//Serialisation Identifier

	protected static final long	serialVersionUID	= 1L;

	//Attributes

	int[]						totalPracticaByMonth;

	Double						averageSessionLength;

	Double						deviationSessionLength;

	Double						minimunSessionLength;

	Double						maximumSessiongLength;
}
