
package acme.testing.assistant.tutorial;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String tAbstract, final String code, final String estimatedTotalTime, final String goals, final String course) {
		// HINT: this test logs in as a lecturer, lists his or her courses, 
		// HINT+ selects one of them, updates it, and then checks that 
		// HINT+ the update has actually been performed.

		super.signIn("assistant1", "assistant1");

		List<Tutorial> tutorials = this.repository.findManyTutorialsByAssistantUsernameOrderedByCode("assistant1");
		int i = 0;
		while (i < tutorials.size()) {
			if (tutorials.get(i).getCode().equals(code))
				break;
			i++;
		}

		if (i == tutorials.size())
			i--;

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.clickOnListingRecord(i);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("tAbstract", tAbstract);
		super.fillInputBoxIn("code", code);
		super.checkInputBoxHasValue("published", "false");
		super.fillInputBoxIn("estimatedTotalTime", estimatedTotalTime);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("course", course);

		super.checkSubmitExists("Update");
		super.clickOnSubmit("Update");

		super.checkNotErrorsExist();

		super.sortListing(2, "asc");

		tutorials = this.repository.findManyTutorialsByAssistantUsernameOrderedByCode("assistant1");
		i = 0;
		while (i < tutorials.size()) {
			if (tutorials.get(i).getCode().equals(code))
				break;
			i++;
		}

		if (i == tutorials.size())
			i--;

		super.checkColumnHasValue(i, 2, code);

		super.clickOnListingRecord(i);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("tAbstract", tAbstract);
		super.checkInputBoxHasValue("published", "false");
		super.checkInputBoxHasValue("estimatedTotalTime", estimatedTotalTime);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("course", course);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/update-negative-1.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String tAbstract, final String code, final String estimatedTotalTime, final String goals, final String course) {
		// HINT: this test attempts to update a course with wrong data.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("tAbstract", tAbstract);
		super.checkInputBoxHasValue("published", "false");
		super.fillInputBoxIn("estimatedTotalTime", estimatedTotalTime);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("course", course);

		super.checkSubmitExists("Update");
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/update-negative-2.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int recordIndex, final String title, final String tAbstract, final String code, final String estimatedTotalTime, final String goals, final String course) {
		// HINT: this test attempts to update a course with wrong data.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.checkColumnHasValue(recordIndex, 2, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("published", "true");

		super.checkNotSubmitExists("Update");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a course with a role other than "Lecturer",
		// HINT+ or using an lecturer who is not the owner.

		final Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");

		super.checkLinkExists("Sign in");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
		}

		super.signIn("administrator", "administrator");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
		}
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
		}
		super.signOut();

		super.signIn("assistant2", "assistant2");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
		}
		super.signOut();
	}

}
