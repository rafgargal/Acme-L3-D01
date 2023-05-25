
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.lecture.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureDeleteTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected LecturerLectureTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title) {
		// HINT: this test authenticates as an lecturer and checks that he or
		// HINT+ she can delete the expected lecture.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
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
		// HINT: this test tries to delete a lecture with a role other than "Lecturer".

		Collection<Lecture> lectures;
		String params;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (lecture.isDraftMode()) {
				params = String.format("id=%d", lecture.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture/delete", params);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/lecture/delete", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/lecturer/lecture/delete", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to delete a published lecture that was registered by the principal.

		Collection<Lecture> lectures;
		String params;

		super.signIn("lecturer1", "lecturer1");
		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (!lecture.isDraftMode()) {
				params = String.format("id=%d", lecture.getId());
				super.request("/lecturer/lecture/delete", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to delete a lecture that wasn't registered by the principal.

		Collection<Lecture> lectures;
		String params;

		super.signIn("lecturer2", "lecturer2");
		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures) {
			params = String.format("id=%d", lecture.getId());
			super.request("/lecturer/lecture/delete", params);
		}
		super.signOut();
	}

}
