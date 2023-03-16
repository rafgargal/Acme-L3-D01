
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	//Serialisation Identifier

	protected static final long	serialVersionUID	= 1L;

	//Attributes

	Map<String, Integer>		totalPracticaByMonth;

	double						averageSessionLength;

	double						deviationSessionLength;

	double						minimunSessionLength;

	double						maximumSessiongLength;
}
