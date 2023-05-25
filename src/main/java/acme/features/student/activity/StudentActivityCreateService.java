
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
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentEnrolmentRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		Enrolment object;
		Principal principal;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		object = this.repository.findEnrolmentById(enrolmentId);
		principal = super.getRequest().getPrincipal();

		status = object.getStudent().getId() == principal.getActiveRoleId();

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

		Activity activity;
		Enrolment enrolment;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);

		activity = new Activity();
		activity.setEnrolment(enrolment);

		super.getBuffer().setData(activity);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);

		super.bind(object, "title", "summary", "activityType", "startDate", "endDate", "moreInfo");
		object.setEnrolment(enrolment);
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

		tuple = super.unbind(object, "title", "summary", "activityType", "startDate", "endDate", "moreInfo");
		tuple.put("activities", choices);
		tuple.put("enrolmentId", enrolmentId);

		super.getResponse().setData(tuple);
	}

}
