
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.activities.Activity;
import acme.entities.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityPublishTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String activityType, final String startDate, final String endDate, final String moreInfo) {
		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "My enrolments");

		super.checkListingExists();

		super.clickOnListingRecord(1);
		super.checkFormExists();
		super.clickOnButton("Activities");

		super.clickOnListingRecord(1);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("activityType", activityType);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.clickOnSubmit("Finalise");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String summary, final String activityType, final String startDate, final String endDate, final String moreInfo) {
		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "My enrolments");

		super.checkListingExists();

		super.clickOnListingRecord(1);
		super.checkFormExists();
		super.clickOnButton("Activities");

		super.clickOnListingRecord(0);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Finalise");
		super.checkErrorsExist();

		super.signOut();
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
		super.request("/student/activity/publish", param);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/activity/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student2", "student2");
		super.request("/student/activity/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/student/activity/publish", param);
		super.checkPanicExists();
		super.signOut();
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to publish a activity that was published already.
		String params;
		Collection<Activity> activities = null;
		int id;

		super.signIn("student1", "student1");
		final Collection<Enrolment> enrolments = this.repository.findEnrolemntByStudentUsername("student1");
		for (final Enrolment e : enrolments) {
			id = e.getId();
			activities = this.repository.findActivityByEnrolment(e.getId());
			for (final Activity a : activities)
				if (!a.isDraftMode()) {
					params = String.format("id=%d", a.getId());
					super.request("/student/activity/publish", params);
					super.checkPanicExists();
				}

		}
		super.signOut();

	}

}
