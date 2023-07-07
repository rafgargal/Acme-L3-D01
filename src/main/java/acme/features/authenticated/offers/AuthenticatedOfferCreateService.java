
package acme.features.authenticated.offers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferCreateService extends AbstractService<Authenticated, Offer> {

	@Autowired
	protected AuthenticatedOfferRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
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
		object = new Offer();
		object.setInstantiationMoment(MomentHelper.getCurrentMoment());
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "heading", "summary", "availabilityPeriodInit", "availabilityPeriodFin", "price", "link");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		final List<String> tmonedas = new ArrayList<>(
			Arrays.asList("USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "HKD", "NZD", "SEK", "KRW", "SGD", "NOK", "MXN", "INR", "RUB", "ZAR", "TRY", "BRL", "TWD", "DKK", "PLN", "THB", "IDR", "HUF", "CZK", "ILS", "CLP", "PHP"));

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodInit"))
			super.state(object.getAvailabilityPeriodInit() != null, "availabilityPeriodInit", "authorise.offer.error.availabilityPeriodFin");

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodFin"))
			super.state(object.getAvailabilityPeriodFin() != null, "availabilityPeriodFin", "authorise.offer.error.availabilityPeriodFin");

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodFin"))
			super.state(MomentHelper.isAfter(object.getAvailabilityPeriodFin(), object.getAvailabilityPeriodInit()), "availabilityPeriodFin", "authorise.offer.error.availabilityPeriodFin");

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getPrice().getAmount() >= 0.0, "price", "authorise.offer.error.priceNegative");

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(tmonedas.contains(object.getPrice().getCurrency()), "price", "authorise.offer.error.price");

	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "heading", "summary", "availabilityPeriodInit", "availabilityPeriodFin", "price", "link");

		super.getResponse().setData(tuple);
	}

}
