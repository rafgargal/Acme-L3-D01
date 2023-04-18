
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activities.Activity;
import acme.entities.course.Course;
import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.student.id = :id")
	Collection<Enrolment> findEnrolmentByStudentId(int id);

	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findEnrolmentById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select e.course from Enrolment e where e.student.id = :id")
	Collection<Course> findAllCoursesByStudentId(int id);

	@Query("select s from Student s where s.id = :id")
	Student findStudentById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select c from Course c where c.code = :code")
	Course findCourseByCode(String code);

	@Query("select a from Activity a where a.enrolment.id = :id")
	Collection<Activity> findActivitiesByEnrolmentId(int id);

}
