
package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSessions.PracticumSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionShowService extends AbstractService<Company, PracticumSession> {

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
		boolean status;
		PracticumSession session;
		Principal principal;
		int practicumId;

		practicumId = super.getRequest().getData("id", int.class);
		session = this.repository.findPracticumSessionById(practicumId);
		principal = super.getRequest().getPrincipal();
		status = session.getPracticum().getCompany().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession session;
		final int id;
		boolean draftMode;
		id = super.getRequest().getData("id", int.class);
		session = this.repository.findPracticumSessionById(id);
		final Practicum practicum = this.repository.findPracticumById(session.getPracticum().getId());
		draftMode = practicum.getDraftMode();

		super.getResponse().setGlobal("draftMode", draftMode);
		super.getBuffer().setData(session);
	}

	@Override
	public void unbind(final PracticumSession session) {
		assert session != null;

		Tuple tuple;

		tuple = super.unbind(session, "title", "summary", "startDate", "endDate", "moreInfoLink");
		tuple.put("masterId", session.getPracticum().getId());
		super.getResponse().setData(tuple);
	}

}
