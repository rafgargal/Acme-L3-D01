
package acme.features.authenticated.practicum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedPracticumController extends AbstractController<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumListingService listingService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listingService);
	}

}
