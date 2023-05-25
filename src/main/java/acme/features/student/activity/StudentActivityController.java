
package acme.features.student.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.activities.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentActivityController extends AbstractController<Student, Activity> {

	@Autowired
	protected StudentActivityListService	listService;

	@Autowired
	protected StudentActivityShowService	showService;

	@Autowired
	protected StudentActivityUpdateService	updateService;

	@Autowired
	protected StudentActivityCreateService	createService;

	@Autowired
	protected StudentActivityDeleteService	deleteService;

	@Autowired
	protected StudentActvityPublishService	publishService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addBasicCommand("delete", this.deleteService);
	}
}
