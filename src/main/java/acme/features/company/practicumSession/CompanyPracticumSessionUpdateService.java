
package acme.features.company.practicumSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSessions.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionUpdateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}
	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {
		PracticumSession session;
		int id;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findPracticumSessionById(id);
		final Practicum practicum = this.repository.findPracticumById(session.getPracticum().getId());

		super.getResponse().setGlobal("draftMode", practicum.getDraftMode());

		super.getBuffer().setData(session);

	}

	@Override
	public void bind(final PracticumSession session) {

		assert session != null;

		super.bind(session, "title", "summary", "startDate", "endDate", "moreInfoLink");

	}

	@Override
	public void validate(final PracticumSession session) {

		assert session != null;

		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isAfter(session.getEndDate(), session.getStartDate()), "endDate", "company.practicum.error.label.endDate");

		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isLongEnough(session.getStartDate(), session.getEndDate(), 7, ChronoUnit.DAYS), "endDate", "company.practicum.error.label.difference");

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			Date minimunDate;
			minimunDate = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(session.getStartDate(), minimunDate), "startDate", "company.practicum.error.label.startDate");
		}

	}

	@Override
	public void perform(final PracticumSession session) {
		assert session != null;
		this.repository.save(session);
	}
	@Override
	public void unbind(final PracticumSession session) {

		assert session != null;
		Tuple tuple;
		tuple = super.unbind(session, "title", "summary", "startDate", "endDate", "moreInfoLink");

		super.getResponse().setData(tuple);

	}
}
