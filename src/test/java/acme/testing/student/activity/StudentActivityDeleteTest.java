
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.activities.Activity;
import acme.entities.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityDeleteTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String activityType, final String startDate, final String endDate, final String moreInfo) {

		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "My enrolments");

		super.checkListingExists();

		super.clickOnListingRecord(1);
		super.checkFormExists();
		super.clickOnButton("Activities");

		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("activityType", activityType);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it
		// HINT+ doesn't involve any forms.
	}

	@Test
	public void test300Hacking() {
		Activity activity = null;
		final Collection<Enrolment> enrolments = this.repository.findEnrolemntByStudentUsername("student1");
		for (final Enrolment e : enrolments) {
			final Collection<Activity> activities = this.repository.findActivityByEnrolment(e.getId());
			if (activities != null) {
				activity = activities.stream().findFirst().orElse(null);
				break;
			}
		}
		String param;
		param = String.format("id=%d", activity.getId());

		super.checkLinkExists("Sign in");
		super.request("/student/activity/delete", param);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/activity/delete", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student2", "student2");
		super.request("/student/activity/delete", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/student/activity/delete", param);
		super.checkPanicExists();
		super.signOut();
	}

}
