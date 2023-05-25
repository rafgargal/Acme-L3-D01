
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicumSessions.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionUpdateTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String startDate, final String endDate, final String moreInfoLink) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();

		super.clickOnListingRecord(0);
		super.checkFormExists();

		super.clickOnButton("Practicum Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfoLink", moreInfoLink);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, summary);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("moreInfoLink", moreInfoLink);

		super.signOut();

	}
	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String summary, final String startDate, final String endDate, final String moreInfoLink) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Practicum Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfoLink", moreInfoLink);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	public void test300Hacking() {
		Collection<PracticumSession> ls;
		String params;

		ls = this.repository.findManyPracticumsSessionByCompanyUsername("company1");
		for (final PracticumSession ps : ls) {

			params = String.format("id=%d", ps.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/update", params);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/company/practicum-session/update", params);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/update", params);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/update", params);
			super.checkPanicExists();
			super.signOut();

		}
	}
}
