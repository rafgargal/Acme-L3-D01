
package acme.features.assistant.dashboard;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select t from Tutorial t")
	List<Tutorial> findAllTutorials();

	@Query("select t from Tutorial t where t.assistant.userAccount.id = :id")
	List<Tutorial> findTutorialsByAssistantId(int id);

	@Query("select t from Tutorial t where t.code = :code")
	Tutorial findTutorialByCode(String code);

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

	@Query("select a from Assistant a where a.userAccount.id = :id")
	Assistant findAssistantByUserAccountId(int id);

	@Query("select c from Course c")
	List<Course> findAllCourses();

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	// ######################################
	@Query("select count(t) FROM Tutorial t where t.assistant.userAccount.id = :id")
	Integer findNumberOfTutorialsByAssistantId(int id);

	@Query("select t.estimatedTotalTime FROM Tutorial t where t.assistant.userAccount.id = :id")
	List<Double> findAllTutorialTimesByAssistantId(int id);

	default List<Double> findTutorialTimesByAssistantId(final int id) {
		final List<Tutorial> tutorials = this.findTutorialsByAssistantId(id);

		return tutorials.stream().mapToDouble(t -> this.findSessionsByTutorialId(t.getId()).stream().mapToDouble(s -> TimeUnit.MILLISECONDS.toHours(s.getEndDateTime().getTime() - s.getStartDateTime().getTime())).sum()).boxed().collect(Collectors.toList());
	}

	default Double findAverageTutorialTimeByAssistantId(final int id) {
		return this.findTutorialTimesByAssistantId(id).stream().mapToDouble(x -> x).average().orElse(0.0);
	}

	default Double findDeviationTutorialTimeByAssistantId(final int id) {
		final double avg = this.findAverageTutorialTimeByAssistantId(id);
		return Math.sqrt(this.findTutorialTimesByAssistantId(id).stream().mapToDouble(x -> x).map(x -> x - avg).map(x -> x * x).average().orElse(0.0));
	}

	default Double findMaxTutorialTimeByAssistantId(final int id) {
		return this.findTutorialTimesByAssistantId(id).stream().mapToDouble(x -> x).max().orElse(0.0);
	}

	default Double findMinTutorialTimeByAssistantId(final int id) {
		return this.findTutorialTimesByAssistantId(id).stream().mapToDouble(x -> x).min().orElse(0.0);
	}

	default Double findAverageSessionTimeByAssistantId(final int id) {
		final List<Session> sessions = this.findSessionsByAssistantId(id);
		return sessions.stream().mapToDouble(s -> TimeUnit.MILLISECONDS.toHours(s.getEndDateTime().getTime() - s.getStartDateTime().getTime())).average().orElse(0.0);
	}

	default Double findMaxSessionTimeByAssistantId(final int id) {
		final List<Session> sessions = this.findSessionsByAssistantId(id);
		return sessions.stream().mapToDouble(s -> TimeUnit.MILLISECONDS.toHours(s.getEndDateTime().getTime() - s.getStartDateTime().getTime())).max().orElse(0.0);
	}

	default Double findMinSessionTimeByAssistantId(final int id) {
		final List<Session> sessions = this.findSessionsByAssistantId(id);
		return sessions.stream().mapToDouble(s -> TimeUnit.MILLISECONDS.toHours(s.getEndDateTime().getTime() - s.getStartDateTime().getTime())).min().orElse(0.0);
	}

	default Double findDeviationSessionTimeByAssistantId(final int id) {
		final List<Session> sessions = this.findSessionsByAssistantId(id);
		final double avg = this.findAverageSessionTimeByAssistantId(id);
		return Math.sqrt(sessions.stream().mapToDouble(s -> TimeUnit.MILLISECONDS.toHours(s.getEndDateTime().getTime() - s.getStartDateTime().getTime())).map(x -> x - avg).map(x -> x * x).average().orElse(0.0));
	}

}
