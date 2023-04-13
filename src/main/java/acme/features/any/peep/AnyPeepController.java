
package acme.features.any.peep;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.peep.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

public class AnyPeepController extends AbstractController<Any, Peep> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyPeepListService	listService;

	@Autowired
	protected AnyPeepShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
