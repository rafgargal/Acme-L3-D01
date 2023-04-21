
package acme.features.administrator.bulletin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinCreateService extends AbstractService<Administrator, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBulletinRepository repository;

	// AbstractService interface ----------------------------------------------


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

		final Bulletin object = new Bulletin();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Bulletin object) {
		assert object != null;

		super.bind(object, "moment", "title", "message", "critical", "moreInfoLink");
	}

	@Override
	public void validate(final Bulletin object) {
		assert object != null;
	}

	@Override
	public void perform(final Bulletin object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "title", "message", "critical", "moreInfoLink");

		super.getResponse().setData(tuple);
	}

}
