
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Session;
import acme.testing.TestHarness;

public class AssistantSessionUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int tutorialIndex, final String title, final String sAbstract, final String type, final String startDateTime, final String endDateTime, final String furtherInformation) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");
		super.clickOnListingRecord(tutorialIndex);

		super.clickOnButton("View sessions");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("sAbstract", sAbstract);
		super.fillInputBoxIn("startDateTime", startDateTime);
		super.fillInputBoxIn("endDateTime", endDateTime);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("furtherInformation", furtherInformation);

		super.clickOnSubmit("Update");

		super.checkNotErrorsExist();

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");
		super.clickOnListingRecord(tutorialIndex);

		super.clickOnButton("View sessions");

		super.checkListingExists();
		super.sortListing(0, "asc");

		// super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, sAbstract);
		super.checkColumnHasValue(recordIndex, 2, type);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("sAbstract", sAbstract);
		super.checkInputBoxHasValue("startDateTime", startDateTime);
		super.checkInputBoxHasValue("endDateTime", endDateTime);
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("furtherInformation", furtherInformation);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/update-negative-1.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final int tutorialIndex, final String title, final String sAbstract, final String type, final String startDateTime, final String endDateTime, final String furtherInformation) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");
		super.clickOnListingRecord(tutorialIndex);

		super.clickOnButton("View sessions");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("sAbstract", sAbstract);
		super.fillInputBoxIn("startDateTime", startDateTime);
		super.fillInputBoxIn("endDateTime", endDateTime);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("furtherInformation", furtherInformation);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/update-negative-2.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int recordIndex, final int tutorialIndex) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");
		super.clickOnListingRecord(tutorialIndex);

		super.clickOnButton("View sessions");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();

		super.checkNotSubmitExists("Update");

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		final Collection<Session> sessions;
		String param;

		sessions = this.repository.findManySessionsByAssistantUsername("assistant1");

		super.checkLinkExists("Sign in");
		for (final Session session : sessions) {
			param = String.format("id=%d", session.getId());
			super.request("/assistant/session/update", param);
			super.checkPanicExists();
		}

		super.signIn("administrator", "administrator");
		for (final Session session : sessions) {
			param = String.format("id=%d", session.getId());
			super.request("/assistant/session/update", param);
			super.checkPanicExists();
		}
		super.signOut();

		super.signIn("assistant2", "assistant2");
		for (final Session session : sessions) {
			param = String.format("id=%d", session.getId());
			super.request("/assistant/session/update", param);
			super.checkPanicExists();
		}
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		for (final Session session : sessions) {
			param = String.format("id=%d", session.getId());
			super.request("/assistant/session/update", param);
			super.checkPanicExists();
		}
		super.signOut();

	}

}
