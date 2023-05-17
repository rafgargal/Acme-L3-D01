
package acme.testing.company.practicum;

import acme.testing.TestHarness;

public class CompanyPracticumUpdateTest extends TestHarness {

	public void test100Positive(final int recordIndex, final String code, final String title, final String summary, final String goals) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My company");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("goals", goals);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("goals", goals);

		super.signOut();

	}

	public void test200Negative(final int recordIndex, final String code, final String title, final String summary, final String goals) {

	}
}
