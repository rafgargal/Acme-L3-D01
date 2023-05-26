
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String followingCode) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");

		super.checkListingExists();

		super.sortListing(2, "asc");
		super.checkColumnHasValue(recordIndex, 2, code);

		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("code", code);

		super.checkSubmitExists("Delete");
		super.clickOnSubmit("Delete");

		super.checkNotErrorsExist();

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();

		super.checkColumnHasValue(recordIndex, 2, followingCode);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");

		super.checkListingExists();

		super.sortListing(2, "asc");

		super.clickOnListingRecord(recordIndex);

		super.checkNotSubmitExists("Delete");

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		final Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");

		super.checkLinkExists("Sign in");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();
		}

		super.signIn("assistant2", "assistant2");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();
		}
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();
		}
		super.signOut();
	}

}
