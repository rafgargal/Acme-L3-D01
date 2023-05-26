
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Session;
import acme.testing.TestHarness;

public class AssistantSessionShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
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

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("sAbstract", sAbstract);
		super.checkInputBoxHasValue("startDateTime", startDateTime);
		super.checkInputBoxHasValue("endDateTime", endDateTime);
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("furtherInformation", furtherInformation);

		super.signOut();
	}

	@Test
	public void test200Negative() {
	}

	@Test
	public void test300Hacking() {

		final Collection<Session> sessions;
		String param;

		sessions = this.repository.findManySessionsByAssistantUsername("assistant1");

		super.checkLinkExists("Sign in");
		for (final Session session : sessions) {
			param = String.format("id=%d", session.getId());
			super.request("/assistant/session/show", param);
			super.checkPanicExists();
		}

		super.signIn("assistant2", "assistant2");
		for (final Session session : sessions) {
			param = String.format("id=%d", session.getId());
			super.request("/assistant/session/show", param);
			super.checkPanicExists();
		}
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		for (final Session session : sessions) {
			param = String.format("id=%d", session.getId());
			super.request("/assistant/session/show", param);
			super.checkPanicExists();
		}
		super.signOut();
	}

}
