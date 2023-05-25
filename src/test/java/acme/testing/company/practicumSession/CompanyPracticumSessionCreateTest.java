
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.Practicum;
import acme.testing.TestHarness;
import acme.testing.company.practicum.CompanyPracticumTestRepository;

public class CompanyPracticumSessionCreateTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String startDate, final String endDate, final String moreInfoLink) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();

		super.clickOnListingRecord(0);
		super.checkFormExists();

		super.clickOnButton("Practicum Sessions");
		super.checkListingExists();
		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfoLink", moreInfoLink);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);

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
		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfoLink", moreInfoLink);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();

	}
	@Test
	public void test300hacking() {
		Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practicums) {
			param = String.format("id=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
