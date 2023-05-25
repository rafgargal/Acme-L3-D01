
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Course;
import acme.testing.TestHarness;

public class LecturerLectureCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String lAbstract, final String learningTime, final String body, final String activityType) {
		// HINT: this test authenticates as a lecturer and then lists his or her
		// HINT: lectures of a course, creates a new one, and check that it's been created properly.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.clickOnButton("Their lectures");
		super.clickOnButton("Add lecture");
		super.clickOnButton("Create and add a new lecture");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("lAbstract", lAbstract);
		super.fillInputBoxIn("learningTime", learningTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("activityType", activityType);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.clickOnButton("Their lectures");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, learningTime);
		super.checkColumnHasValue(recordIndex, 2, activityType);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("lAbstract", lAbstract);
		super.checkInputBoxHasValue("learningTime", learningTime);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("activityType", activityType);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String lAbstract, final String learningTime, final String body, final String activityType) {
		// HINT: this test attempts to create lectures with incorrect data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.clickOnButton("Their lectures");
		super.clickOnButton("Add lecture");
		super.clickOnButton("Create and add a new lecture");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("lAbstract", lAbstract);
		super.fillInputBoxIn("learningTime", learningTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("activityType", activityType);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a lecture using principals with
		// HINT+ inappropriate roles.

		Collection<Course> courses;
		String param;

		courses = this.repository.findManyCoursesByLecturerUsurname("lecturer1");
		for (final Course course : courses) {
			param = String.format("courseId=%d", course.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/lecture/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/lecturer/lecture/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
