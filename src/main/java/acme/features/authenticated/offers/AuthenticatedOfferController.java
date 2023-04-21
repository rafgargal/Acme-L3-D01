
package acme.features.authenticated.offers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedOfferController extends AbstractController<Authenticated, Offer> {

	@Autowired
	protected AuthenticatedOfferListService		listService;

	@Autowired
	protected AuthenticatedOfferShowService		showService;

	@Autowired
	protected AuthenticatedOfferDeleteService	deleteService;

	@Autowired
	protected AuthenticatedOfferUpdateService	updateService;

	@Autowired
	protected AuthenticatedOfferCreateService	createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("create", this.createService);
	}

}
