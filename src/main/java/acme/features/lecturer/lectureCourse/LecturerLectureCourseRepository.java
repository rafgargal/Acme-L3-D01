
package acme.features.lecturer.lectureCourse;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.LectureCourse;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerLectureCourseRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecture l where l.lecturer.id = :lecturerId and l.id not in (select lc.lecture.id from LectureCourse lc where lc.course.id = :courseId)")
	List<Lecture> findAllLectureForACourse(int courseId, int lecturerId);

	@Query("select lc.lecture from LectureCourse lc where lc.course.id = :courseId")
	List<Lecture> findAllLectureOfACourse(int courseId);

	@Query("select lc from LectureCourse lc where lc.course = :course and lc.lecture = :lecture")
	LectureCourse findOneLectureCourseByCourseAndLecture(Course course, Lecture lecture);

}
