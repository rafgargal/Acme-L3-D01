
package acme.features.authenticated.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.note.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository repository;


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
		Note object;
		object = new Note();
		Principal principal;
		UserAccount userAccount;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		object.setAuthor(userAccount.getIdentity().getFullName());
		object.setEmail(userAccount.getIdentity().getEmail());
		object.setMoment(MomentHelper.getCurrentMoment());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note object) {
		assert object != null;

		super.bind(object, "title", "message", "furtherInfo");

	}

	@Override
	public void validate(final Note object) {
		assert object != null;

		final boolean res = super.getRequest().getData("check", boolean.class);
		if (res == false)
			super.state(super.getRequest().getData("check", boolean.class), "check", "authenticated.note.error.label.check");

	}

	@Override
	public void perform(final Note object) {

		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Note object) {
		Tuple tuple;

		tuple = super.unbind(object, "title", "message", "furtherInfo");

		super.getResponse().setData(tuple);
	}

}
