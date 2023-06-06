
package acme.features.student.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

	@Query("select lc.lecture from LectureCourse lc where lc.course.id = :id")
	Collection<Lecture> findLectureIdByCourseId(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findLectureById(int id);

}
