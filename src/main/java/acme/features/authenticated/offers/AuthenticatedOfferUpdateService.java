
package acme.features.authenticated.offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferUpdateService extends AbstractService<Authenticated, Offer> {

	@Autowired
	protected AuthenticatedOfferRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodInit", "availabilityPeriodFin", "price", "link");
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodInit", "availabilityPeriodFin", "price", "link");

		super.getResponse().setData(tuple);

	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodInit"))
			super.state(object.getAvailabilityPeriodInit() != null, "availabilityPeriodInit", "authorise.offer.error.availabilityPeriodFin");

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodFin"))
			super.state(object.getAvailabilityPeriodFin() != null, "availabilityPeriodFin", "authorise.offer.error.availabilityPeriodFin");

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodFin"))
			super.state(!MomentHelper.isAfter(object.getAvailabilityPeriodFin(), object.getAvailabilityPeriodInit()), "availabilityPeriodFin", "authorise.offer.error.availabilityPeriodFin");

	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

}
