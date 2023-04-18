
package acme.features.any.peep;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peep.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepCreateService extends AbstractService<Any, Peep> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyPeepRepository repository;

	// AbstractService interface ---------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		final Peep object;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;
		final String nick;

		principal = super.getRequest().getPrincipal();

		final ZoneId defaultZoneId = ZoneId.systemDefault();
		final LocalDate localDate = LocalDate.now();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		if (userAccount.isAnonymous())
			nick = " ";
		else
			nick = userAccount.getIdentity().getName() + " " + userAccount.getIdentity().getSurname();

		object = new Peep();
		object.setMoment(Date.from(localDate.atStartOfDay(defaultZoneId).toInstant()));
		object.setTitle(" ");
		object.setNick(nick);
		object.setMessage(" ");
		object.setEmail(null);
		object.setLink(null);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Peep object) {
		assert object != null;

		super.bind(object, "title", "nick", "message", "email", "link");
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;
	}

	@Override
	public void perform(final Peep object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		Tuple tuple;

		tuple = super.unbind(object, "title", "nick", "message", "email", "link");
		tuple.put("moment", object.getMoment());

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
