
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantSessionCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int tutorialIndex, final String title, final String sAbstract, final String type, final String startDateTime, final String endDateTime, final String furtherInformation) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");
		super.clickOnListingRecord(tutorialIndex);

		super.clickOnButton("View sessions");

		super.checkListingExists();

		super.clickOnButton("Create new Session");

		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("sAbstract", sAbstract);
		super.fillInputBoxIn("startDateTime", startDateTime);
		super.fillInputBoxIn("endDateTime", endDateTime);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("furtherInformation", furtherInformation);

		super.clickOnSubmit("Save");

		super.checkNotErrorsExist();

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");
		super.clickOnListingRecord(tutorialIndex);

		super.clickOnButton("View sessions");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, sAbstract);
		super.checkColumnHasValue(recordIndex, 2, type);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final int tutorialIndex, final String title, final String sAbstract, final String type, final String startDateTime, final String endDateTime, final String furtherInformation) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");
		super.clickOnListingRecord(tutorialIndex);

		super.clickOnButton("View sessions");

		super.checkListingExists();

		super.clickOnButton("Create new Session");

		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("sAbstract", sAbstract);
		super.fillInputBoxIn("startDateTime", startDateTime);
		super.fillInputBoxIn("endDateTime", endDateTime);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("furtherInformation", furtherInformation);

		super.clickOnSubmit("Save");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		final Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");

		super.checkLinkExists("Sign in");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("tutorialId=%d", tutorial.getId());
			super.request("/assistant/session/create", param);
			super.checkPanicExists();
		}

		super.signIn("assistant2", "assistant2");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("tutorialId=%d", tutorial.getId());
			super.request("/assistant/session/create", param);
			super.checkPanicExists();
		}
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("tutorialId=%d", tutorial.getId());
			super.request("/assistant/session/create", param);
			super.checkPanicExists();
		}
		super.signOut();

	}

}
