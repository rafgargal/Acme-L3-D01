
package acme.features.lecturer.lectureCourse;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.course.LectureCourse;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerLectureCourseController extends AbstractController<Lecturer, LectureCourse> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureCourseAddService		addService;

	@Autowired
	protected LecturerLectureCourseDeleteService	deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("add", "create", this.addService);
	}

}
