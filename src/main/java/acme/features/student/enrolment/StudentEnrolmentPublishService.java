
package acme.features.student.enrolment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentPublishService extends AbstractService<Student, Enrolment> {

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
		boolean status;
		Enrolment object;
		Principal principal;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(enrolmentId);
		principal = super.getRequest().getPrincipal();

		status = object.getStudent().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("holderName")) {
			String holderName;
			holderName = object.getHolderName();

			super.state(holderName.length() != 0, "holderName", "student.enrolment.error.holderName.null");
		}

		if (!super.getBuffer().getErrors().hasErrors("lowerNibble")) {
			String lowerNibble;
			lowerNibble = object.getLowerNibble();

			super.state(lowerNibble.length() != 0, "lowerNibble", "student.enrolment.error.lowerNibble.null");
			super.state(lowerNibble.length() >= 13, "lowerNibble", "student.enrolment.error.lowerNibble.notValidNumber");
			super.state(lowerNibble.length() <= 18, "lowerNibble", "student.enrolment.error.lowerNibble.notValidNumber");
		}

		final String cvc = super.getRequest().getData("cvc", String.class);
		if (!super.getBuffer().getErrors().hasErrors("cvc"))
			super.state(cvc.length() != 0 && cvc.matches("^\\d{3}$"), "cvc", "student.enrolment.error.cvc.matches");

		final String expiryDate = super.getRequest().getData("expiryDate", String.class);

		if (!super.getBuffer().getErrors().hasErrors("expiryDate"))
			super.state(expiryDate.length() != 0 && expiryDate.matches("^\\d{2}\\/\\d{2}$"), "expiryDate", "student.enrolment.error.expiryDate.matches");

	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		object.setDraftMode(false);

		final String lowerNibble = super.getRequest().getData("lowerNibble", String.class);
		object.setLowerNibble(lowerNibble.substring(12, 16));

		final String holderName = super.getRequest().getData("holderName", String.class);
		object.setHolderName(holderName);

		this.repository.save(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		Integer enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);

		super.bind(object, "code", "motivation", "goal", "holderName", "lowerNibble");

	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;
		final String cvc = "";
		final String expiryDate = "";

		final Course course = object.getCourse();

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "lowerNibble", "holderName", "draftMode");
		tuple.put("courses", course.getCode());
		tuple.put("cvc", cvc);
		tuple.put("expiryDate", expiryDate);

		super.getResponse().setData(tuple);
	}

}
