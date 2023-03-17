
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	//Serialisation Identifier

	protected static final long	serialVersionUID	= 1L;

	//Attributes

	int[]						totalAudits;

	double						averageNumAuditingRecords;

	double						deviationNumAuditingRecords;

	double						minNumAuditingRecords;

	double						maxNumAuditingRecords;

	double						averagePeriodAuditingRecords;

	double						deviationPeriodAuditingRecords;

	double						minPeriodAuditingRecords;

	double						maxPeriodAuditingRecords;

}
