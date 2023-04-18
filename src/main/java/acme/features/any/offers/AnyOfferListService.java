
package acme.features.any.offers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyOfferListService extends AbstractService<Any, Offer> {

	@Autowired
	protected AnyOfferRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().getUsername().equals("anonymous");

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Offer> objects;

		objects = this.repository.findAllOffers();

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "heading", "summary");

		super.getResponse().setData(tuple);
	}

}
