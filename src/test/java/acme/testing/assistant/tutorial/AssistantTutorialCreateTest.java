
package acme.testing.assistant.tutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String tAbstract, final String code, final String estimatedTotalTime, final String goals, final String course) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("tAbstract", tAbstract);
		super.fillInputBoxIn("estimatedTotalTime", estimatedTotalTime);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Save");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();

		super.sortListing(2, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, course);
		super.checkColumnHasValue(recordIndex, 2, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("tAbstract", tAbstract);
		super.checkInputBoxHasValue("published", "false");
		super.checkInputBoxHasValue("estimatedTotalTime", estimatedTotalTime);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("course", course);

		super.clickOnButton("View sessions");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String tAbstract, final String code, final String estimatedTotalTime, final String goals, final String course) {
		// HINT: this test attempts to create courses with incorrect data.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("tAbstract", tAbstract);
		super.fillInputBoxIn("estimatedTotalTime", estimatedTotalTime);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("course", course);

		super.clickOnSubmit("Save");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a course using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();
		super.signOut();
	}

}
