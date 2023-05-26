
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialPublishTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected AssistantTutorialTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String tAbstract, final String estimatedTotalTime, final String goals, final String course) {
		// HINT: this test authenticates as a lecturer, lists his or her courses,
		// HINT: then selects one of them, and publishes it.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");
		super.checkColumnHasValue(recordIndex, 2, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkSubmitExists("Publish");
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();
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
		super.checkInputBoxHasValue("published", "true");
		super.checkInputBoxHasValue("estimatedTotalTime", estimatedTotalTime);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("course", course);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code) {
		// HINT: this test attempts to publish a course that cannot be published, yet.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");
		super.checkColumnHasValue(recordIndex, 2, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkNotSubmitExists("Publish");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to publish a job with a role other than "Lecturer".

		Collection<Tutorial> tutorials;
		String params;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");

		super.checkLinkExists("Sign in");
		for (final Tutorial tutorial : tutorials)
			if (!tutorial.isPublished()) {
				params = String.format("id=%d", tutorial.getId());
				super.request("/assistant/tutorial/publish", params);
				super.checkPanicExists();
			}

		super.signIn("administrator", "administrator");
		for (final Tutorial tutorial : tutorials)
			if (!tutorial.isPublished()) {
				params = String.format("id=%d", tutorial.getId());
				super.request("/assistant/tutorial/publish", params);
				super.checkPanicExists();
			}
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		for (final Tutorial tutorial : tutorials)
			if (!tutorial.isPublished()) {
				params = String.format("id=%d", tutorial.getId());
				super.request("/assistant/tutorial/publish", params);
				super.checkPanicExists();
			}
		super.signOut();
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to publish a published course that was registered by the principal.

		Collection<Tutorial> tutorials;
		String params;

		super.signIn("assistant1", "assistant1");
		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials)
			if (tutorial.isPublished()) {
				params = String.format("id=%d", tutorial.getId());
				super.request("/assistant/tutorial/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to publish a course that wasn't registered by the principal,
		// HINT+ be it published or unpublished.

		final Collection<Tutorial> tutorials;
		String params;

		super.signIn("assistant2", "assistant2");
		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials) {
			params = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/publish", params);
		}
		super.signOut();
	}

}
