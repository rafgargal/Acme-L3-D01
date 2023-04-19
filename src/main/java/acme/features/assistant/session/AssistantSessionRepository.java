
package acme.features.assistant.session;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantSessionRepository extends AbstractRepository {

	@Query("select s from Session s where s.id = :id")
	Session findSessionById(int id);

	@Query("select s from Session s")
	List<Session> findAllSessions();

	@Query("select s from Session s where s.tutorial.assistant.userAccount.id = :id")
	List<Session> findSessionsByAssistantId(int id);

	@Query("select s from Session s where s.tutorial.id = :id")
	List<Session> findSessionsByTutorialId(int id);

	@Query("select a from Assistant a where a.id = :id")
	Assistant findAssistantById(int id);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select c from Course c")
	List<Course> findAllCourses();

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

}
