
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		final Collection<PracticumSession> sessions;
		int practicumId;

		practicumId = super.getRequest().getData("masterId", int.class);
		sessions = this.repository.findPracticumSessionsByPracticumId(practicumId);

		super.getBuffer().setData(sessions);
	}

	@Override
	public void unbind(final PracticumSession sessions) {
		assert sessions != null;
		int practicumId;
		Tuple tuple;

		practicumId = super.getRequest().getData("masterId", int.class);
		tuple = super.unbind(sessions, "title", "summary");
		super.getResponse().setGlobal("masterId", practicumId);
		super.getResponse().setData(tuple);
	}
}
