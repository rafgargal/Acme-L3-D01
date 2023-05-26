
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantSessionListTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int tutorialIndex, final String title, final String sAbstract, final String type) {
		// HINT: this test signs in as a lecturer, then lists the lectures,
		// HINT+ and checks that the listing shows the expected data.

		super.signIn("assistant1", "assistant1");

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

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it
		// HINT+ doesn't involve any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show lectures that the principal cannot show.

		final Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");

		super.checkLinkExists("Sign in");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("tutorialId=%d", tutorial.getId());
			super.request("/assistant/session/list", param);
			super.checkPanicExists();
		}

		super.signIn("assistant2", "assistant2");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("tutorialId=%d", tutorial.getId());
			super.request("/assistant/session/list", param);
			super.checkPanicExists();
		}
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("tutorialId=%d", tutorial.getId());
			super.request("/assistant/session/list", param);
			super.checkPanicExists();
		}
		super.signOut();
	}

}
