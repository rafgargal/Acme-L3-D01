
package acme.features.student.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;

public interface StudentCourseRepository extends AbstractRepository {

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select c from Course c WHERE c.id = :id")
	Course findOneCourseById(int id);

}
