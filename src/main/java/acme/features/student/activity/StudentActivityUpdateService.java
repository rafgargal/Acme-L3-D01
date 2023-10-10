
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.activities.ActivityType;
import acme.entities.enrolments.Enrolment;
import acme.features.student.enrolment.StudentEnrolmentRepository;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentEnrolmentRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		final int id;
		Activity activity;
		Principal principal;
		final Student student;
		Enrolment object;

		id = super.getRequest().getData("id", int.class);

		activity = this.repository.findActivityById(id);
		if (activity == null)
			status = false;
		else {
			final Enrolment enrolment = activity.getEnrolment();
			object = this.repository.findEnrolmentById(enrolment.getId());
			principal = super.getRequest().getPrincipal();
			status = object.getStudent().getId() == principal.getActiveRoleId() && activity.isDraftMode();
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void validate(final Activity object) {
		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(object.getEndDate() != null && object.getStartDate() != null, "endDate", "student.activity.error.date");
		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isAfter(object.getEndDate(), object.getStartDate()), "endDate", "student.activity.error.endDate");
	}

	@Override
	public void load() {
		int id;
		Activity activity;

		id = super.getRequest().getData("id", int.class);
		activity = this.repository.findActivityById(id);

		super.getBuffer().setData(activity);
	}
	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "summary", "activityType", "startDate", "endDate", "moreInfo");
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		final int enrolmentId = object.getEnrolment().getId();

		choices = SelectChoices.from(ActivityType.class, object.getActivityType());

		tuple = super.unbind(object, "title", "summary", "activityType", "startDate", "endDate", "moreInfo", "draftMode");
		tuple.put("activities", choices);
		tuple.put("enrolmentId", enrolmentId);

		super.getResponse().setData(tuple);
	}

}
