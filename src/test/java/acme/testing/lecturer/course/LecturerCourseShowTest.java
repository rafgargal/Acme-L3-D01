
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Course;
import acme.testing.TestHarness;

public class LecturerCourseShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String cAbstract, final String draftMode, final String retailPrice, final String furtherInfo, final String activityType) {
		// HINT: this test signs in as an lecturer, then lists the courses,
		// HINT+ and checks that the listing shows the expected data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("cAbstract", cAbstract);
		super.checkInputBoxHasValue("draftMode", draftMode);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
		super.checkInputBoxHasValue("furtherInfo", furtherInfo);
		super.checkInputBoxHasValue("activityType", activityType);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it
		// HINT+ doesn't involve any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show courses that the principal cannot show.

		Collection<Course> courses;
		String param;

		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses) {
			param = String.format("id=%d", course.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/course/show", param);
			super.checkPanicExists();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/course/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/lecturer/course/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
