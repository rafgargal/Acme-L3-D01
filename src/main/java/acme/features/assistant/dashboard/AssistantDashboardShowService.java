
package acme.features.assistant.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.AssistantDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantDashboardRepository repository;

	// AbstractService interface ---------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		//super.getResponse().setAuthorised(super.getRequest().getPrincipal().hasRole(Auditor.class));
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		final int id = super.getRequest().getPrincipal().getAccountId();

		AssistantDashboard dashboard;
		final Integer totalTutorials;
		final Double averageSessionTime;
		final Double deviationSessionTime;
		final Double minimumSessionTime;
		final Double maximumSessionTime;
		final Double averageTutorialTime;
		final Double deviationTutorialTime;
		final Double minimumTutorialTime;
		final Double maximumTutorialTime;

		totalTutorials = this.repository.findNumberOfTutorialsByAssistantId(id);
		averageSessionTime = this.repository.findAverageSessionTimeByAssistantId(id);
		deviationSessionTime = this.repository.findDeviationSessionTimeByAssistantId(id);
		minimumSessionTime = this.repository.findMinSessionTimeByAssistantId(id);
		maximumSessionTime = this.repository.findMaxSessionTimeByAssistantId(id);
		averageTutorialTime = this.repository.findAverageTutorialTimeByAssistantId(id);
		deviationTutorialTime = this.repository.findDeviationTutorialTimeByAssistantId(id);
		minimumTutorialTime = this.repository.findMinTutorialTimeByAssistantId(id);
		maximumTutorialTime = this.repository.findMaxTutorialTimeByAssistantId(id);

		dashboard = new AssistantDashboard();
		dashboard.setTotalTutorials(totalTutorials);
		dashboard.setAverageSessionTime(averageSessionTime);
		dashboard.setDeviationSessionTime(deviationSessionTime);
		dashboard.setMinimumSessionTime(minimumSessionTime);
		dashboard.setMaximumSessionTime(maximumSessionTime);
		dashboard.setAverageTutorialTime(averageTutorialTime);
		dashboard.setDeviationTutorialTime(deviationTutorialTime);
		dashboard.setMinimumTutorialTime(minimumTutorialTime);
		dashboard.setMaximumTutorialTime(maximumTutorialTime);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalTutorials", "averageSessionTime", "deviationSessionTime", "minimumSessionTime", "maximumSessionTime", "averageTutorialTime", "deviationTutorialTime", "minimumTutorialTime", "maximumTutorialTime");

		super.getResponse().setData(tuple);
	}

}
