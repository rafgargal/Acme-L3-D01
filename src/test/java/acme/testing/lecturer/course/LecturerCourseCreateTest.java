
package acme.testing.lecturer.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String cAbstract, final String retailPrice, final String furtherInfo) {
		// HINT: this test authenticates as a lecturer and then lists his or her
		// HINT: courses, creates a new one, and check that it's been created properly.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("cAbstract", cAbstract);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("furtherInfo", furtherInfo);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, retailPrice);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("cAbstract", cAbstract);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
		super.checkInputBoxHasValue("furtherInfo", furtherInfo);

		super.clickOnButton("Their lectures");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String cAbstract, final String retailPrice, final String furtherInfo) {
		// HINT: this test attempts to create courses with incorrect data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("cAbstract", cAbstract);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("furtherInfo", furtherInfo);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a course using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/lecturer/course/create");
		super.checkPanicExists();
		super.signOut();
	}

}
