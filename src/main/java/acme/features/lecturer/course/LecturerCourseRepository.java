
package acme.features.lecturer.course;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.ActivityType;
import acme.entities.activities.Activity;
import acme.entities.auditing.Audit;
import acme.entities.course.Course;
import acme.entities.course.LectureCourse;
import acme.entities.enrolments.Enrolment;
import acme.entities.tutorial.Tutorial;
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

	@Query("select count(lc) > 0 from LectureCourse lc where lc.lecture.draftMode = true and lc.course.id = :id")
	boolean isAnyLectureInDraftModeByCourseId(int id);

	@Query("select lc from LectureCourse lc where lc.course.id = :id")
	List<LectureCourse> findAllLectureCourseByCourseId(int id);

	@Query("select a from Audit a where a.course.id = :id")
	List<Audit> findAllAuditByCourseId(int id);

	@Query("select e from Enrolment e where e.course.id = :id")
	List<Enrolment> findAllEnrolmentByCourseId(int id);

	@Query("select a from Activity a where a.enrolment.id = :id")
	List<Activity> findAllActivityByEnrolmentId(int id);

	@Query("select t from Tutorial t where t.course.id = :id")
	List<Tutorial> findAllTutorialByCourseId(int id);

	@Query("select count(lc) from LectureCourse lc where lc.lecture.activityType = :type and lc.course.id = :id")
	Integer numOfLecturesOfOneTypeByCourseId(int id, ActivityType type);

	default ActivityType findActivityType(final int id) {
		final Integer theoreticalLectures = this.numOfLecturesOfOneTypeByCourseId(id, ActivityType.THEORETICAL);
		final Integer handsOnLectures = this.numOfLecturesOfOneTypeByCourseId(id, ActivityType.HANDS_ON);

		if (theoreticalLectures > handsOnLectures)
			return ActivityType.THEORETICAL;
		else if (theoreticalLectures < handsOnLectures)
			return ActivityType.HANDS_ON;
		else
			return ActivityType.BALANCED;

	}

}
