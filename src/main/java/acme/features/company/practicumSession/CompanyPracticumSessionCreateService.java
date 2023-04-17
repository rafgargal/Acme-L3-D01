
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
public class CompanyPracticumSessionCreateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {

		//boolean status;

		//status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(true);
	}
	@Override
	public void authorise() {
		boolean status;
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		PracticumSession session;
		final int practicumId;
		final Practicum practicum;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		session = new PracticumSession();
		session.setPracticum(practicum);

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
		int masterId;
		Tuple tuple;
		masterId = super.getRequest().getData("masterId", int.class);
		tuple = super.unbind(session, "title", "summary", "startDate", "endDate", "moreInfoLink");
		tuple.put("masterId", masterId);
		super.getResponse().setData(tuple);
	}

}
