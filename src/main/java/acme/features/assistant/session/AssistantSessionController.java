
package acme.features.assistant.session;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.tutorial.Session;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantSessionController extends AbstractController<Assistant, Session> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionListService	listService;

	@Autowired
	protected AssistantSessionShowService	showService;

	@Autowired
	protected AssistantSessionCreateService	createService;

	@Autowired
	protected AssistantSessionDeleteService	deleteService;

	@Autowired
	protected AssistantSessionUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
	}

}
