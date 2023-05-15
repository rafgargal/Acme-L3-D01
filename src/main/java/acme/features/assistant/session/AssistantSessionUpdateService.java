
package acme.features.assistant.session;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActivityType;
import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionUpdateService extends AbstractService<Assistant, Session> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		// Cannot update if tutorial doesn't belong to you or tutorial has been published

		final Session session = this.repository.findSessionById(super.getRequest().getData("id", int.class));

		final Tutorial tutorial = session.getTutorial();

		final int assistantIdFromTutorial = tutorial.getAssistant().getId();

		final int assistantIdFromLoggedUser = super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(assistantIdFromTutorial == assistantIdFromLoggedUser && !tutorial.isPublished());
	}

	@Override
	public void load() {
		Session object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSessionById(id);

		super.getResponse().setGlobal("canDeleteOrUpdateSession", !object.getTutorial().isPublished());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Session object) {
		assert object != null;

		super.bind(object, "title", "type", "sAbstract", "startDateTime", "endDateTime", "furtherInformation");
	}

	@Override
	public void validate(final Session object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDateTime")) {
			final Date currentDate = MomentHelper.getBaseMoment();

			final Date oneDayAhead = new Date(currentDate.getTime() + 24 * 60 * 60 * 1000);

			super.state(object.getStartDateTime().after(oneDayAhead), "startDateTime", "assistant.session.form.error.one-day-ahead");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDateTime")) {
			super.state(object.getEndDateTime().after(object.getStartDateTime()), "endDateTime", "assistant.session.form.error.is-after");

			final long timeDifference = object.getEndDateTime().getTime() - object.getStartDateTime().getTime();
			final double hoursDifference = timeDifference / (60 * 60 * 1000 + 0.);

			super.state(hoursDifference >= 1 && hoursDifference <= 5, "endDateTime", "assistant.session.form.error.hours");

		}
	}

	@Override
	public void perform(final Session object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Session object) {
		assert object != null;

		final SelectChoices choices;
		Tuple tuple;
		choices = SelectChoices.from(ActivityType.class, object.getType());

		tuple = super.unbind(object, "title", "sAbstract", "startDateTime", "endDateTime", "furtherInformation");
		tuple.put("type", choices.getSelected().getKey());
		tuple.put("types", choices);

		super.getResponse().setGlobal("canDeleteOrUpdateSession", !object.getTutorial().isPublished());

		super.getResponse().setData(tuple);
	}

}
