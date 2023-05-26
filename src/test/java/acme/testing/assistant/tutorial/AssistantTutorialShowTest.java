
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String tAbstract, final String code, final String estimatedTotalTime, final String goals, final String course, final String published) {
		// HINT: this test signs in as an lecturer, then lists the courses,
		// HINT+ and checks that the listing shows the expected data.

		super.signIn("assistant1", "assistant1");

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
		super.checkInputBoxHasValue("published", published);
		super.checkInputBoxHasValue("estimatedTotalTime", estimatedTotalTime);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("course", course);

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

		final Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");

		super.checkLinkExists("Sign in");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/show", param);
			super.checkPanicExists();
		}

		super.signIn("assistant2", "assistant2");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/show", param);
			super.checkPanicExists();
		}
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/show", param);
			super.checkPanicExists();
		}
		super.signOut();

	}

}
