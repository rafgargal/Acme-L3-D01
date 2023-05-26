
package acme.testing.lecturer.lectureCourse;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Course;
import acme.testing.TestHarness;
import acme.testing.lecturer.lecture.LecturerLectureTestRepository;

public class LecturerLectureCourseDeleteTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected LecturerLectureTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lectureCourse/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title) {
		// HINT: this test authenticates as an lecturer and checks that he or
		// HINT+ she can delete the expected lectureCourse.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnButton("Their lectures");
		super.clickOnButton("Delete");
		super.fillInputBoxIn("lecture", title);
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to delete a lectureCourse with a role other than "Lecturer".

		Collection<Course> courses;
		String param;

		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses) {
			param = String.format("courseId=%d", course.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture-course/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/lecture-course/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/lecturer/lecture-course/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to delete a lecture of a published course that was registered by the principal.

		Collection<Course> courses;
		String params;

		super.signIn("lecturer1", "lecturer1");
		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses)
			if (!course.isDraftMode()) {
				params = String.format("courseId=%d", course.getId());
				super.request("/lecturer/lecture-course/delete", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to delete a lecture that wasn't registered by the principal.

		Collection<Course> courses;
		String params;

		super.signIn("lecturer2", "lecturer2");
		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses) {
			params = String.format("courseId=%d", course.getId());
			super.request("/lecturer/lecture-course/delete", params);
		}
		super.signOut();
	}

}
