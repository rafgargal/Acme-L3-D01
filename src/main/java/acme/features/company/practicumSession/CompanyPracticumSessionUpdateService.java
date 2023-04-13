
package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
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
	}

	@Override
	public void perform(final PracticumSession session) {
		assert session != null;
		this.repository.save(session);
	}
	@Override
	public void unbind(final PracticumSession session) {
		assert session != null;

	}
}
