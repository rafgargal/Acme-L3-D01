
package acme.features.lecturer.course;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select c from Course c")
	List<Course> findAllCourse();

	@Query("select c from Course c where c.lecturer.userAccount.id = :id")
	List<Course> findAllCourseByLecturerId(int id);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select l from Lecturer l")
	List<Lecturer> findAllLecturer();

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

	@Query("select count(lc) > 0 from LectureCourse lc where lc.lecture.draftMode = true AND lc.course.id = :id")
	boolean isAnyLectureInDraftModeByCourseId(int id);

}
