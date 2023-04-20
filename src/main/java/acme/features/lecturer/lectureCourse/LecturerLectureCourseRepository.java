
package acme.features.lecturer.lectureCourse;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerLectureCourseRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select distinct(l) from Lecture l where l not in (select l from Lecture l left join LectureCourse lc on l.id = lc.lecture.id where lc.course.id != :courseId) and l.lecturer.id = :lecturerId")
	List<Lecture> findAllLectureForACourse(int courseId, int lecturerId);

}
