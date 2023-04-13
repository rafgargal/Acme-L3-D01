
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSessions.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionListService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int companyId;
		final int id;
		final Practicum practicum;

		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		id = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(id);

		status = companyId == practicum.getCompany().getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		final Collection<PracticumSession> sessions;
		final int practicumId;

		practicumId = super.getRequest().getData("masterId", int.class);
		sessions = this.repository.findPracticumSessionsByPracticumId(practicumId);
		super.getBuffer().setData(sessions);
		super.getResponse().setGlobal("masterId", practicumId);
	}

	@Override
	public void unbind(final PracticumSession sessions) {
		assert sessions != null;
		final int practicumId;
		Tuple tuple;

		practicumId = super.getRequest().getData("masterId", int.class);
		tuple = super.unbind(sessions, "title", "summary");
		tuple.put("masterId", practicumId);
		super.getResponse().setData(tuple);
	}
}
