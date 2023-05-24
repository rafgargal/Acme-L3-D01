
package acme.testing.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activities.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityTestRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.draftMode = true and e.student.userAccount.username = :username")
	Collection<Enrolment> findEnrolmentWithDrafMode(String username);

	@Query("select e from Enrolment e where e.draftMode = false and e.student.userAccount.username = :username")
	Collection<Enrolment> findEnrolmentWithoutDraftMode(String username);

	@Query("select a from Activity a where a.id =:id")
	Activity findActivityById(int id);

	@Query("select e from Enrolment e where e.student.userAccount.username = :username")
	Collection<Enrolment> findEnrolemntByStudentUsername(String username);

	@Query("select a from Activity a where a.enrolment.id = :enrolment")
	Collection<Activity> findActivityByEnrolment(int enrolment);

}
