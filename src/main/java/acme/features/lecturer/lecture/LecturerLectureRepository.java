
package acme.features.lecturer.lecture;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.LectureCourse;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select lc.lecture from LectureCourse lc where lc.course.id = :id")
	List<Lecture> findAllLectureByCourseId(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id")
	List<Lecture> findAllLectureByLecturerId(int id);

	@Query("select lc from LectureCourse lc where lc.lecture.id = :id")
	List<LectureCourse> findAllLectureCourseByLectureId(int id);

}
