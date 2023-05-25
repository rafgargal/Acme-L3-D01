
package acme.testing.any.peep;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class anyPeepShowTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String moment, final String title, final String poster, final String message, final String email, final String link) {

		super.clickOnMenu("Anonymous", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("moment", moment);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("nick", poster);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("link", link);
	}

}
