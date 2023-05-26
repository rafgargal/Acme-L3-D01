
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.Practicum;
import acme.testing.TestHarness;
import acme.testing.company.practicum.CompanyPracticumTestRepository;

public class CompanyPracticumSessionDeleteTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String startDate, final String endDate, final String moreInfoLink) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();

		super.clickOnListingRecord(0);
		super.checkFormExists();

		super.clickOnButton("Practicum Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("moreInfoLink", moreInfoLink);

		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.signOut();

	}

	@Test
	public void test300Hacking() {

		Collection<Practicum> practicums;
		String params;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practicums)
			if (practicum.getDraftMode()) {
				params = String.format("id=%d", practicum.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicumSession/delete", params);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/company/practicumSession/delete", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/company/practicumSession/delete", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company2", "company2");
				super.request("/company/practicumSession/delete", params);
				super.checkPanicExists();
				super.signOut();
			}

	}

}
