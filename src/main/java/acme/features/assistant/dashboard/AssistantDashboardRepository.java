
package acme.features.assistant.dashboard;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

	@Query("select avg(t.estimatedTotalTime) FROM Tutorial t where t.assistant.userAccount.id = :id")
	Double findAverageTutorialTimeByAssistantId(int id);

	@Query("select min(t.estimatedTotalTime) FROM Tutorial t where t.assistant.userAccount.id = :id")
	Double findMinTutorialTimeByAssistantId(int id);

	@Query("select max(t.estimatedTotalTime) FROM Tutorial t where t.assistant.userAccount.id = :id")
	Double findMaxTutorialTimeByAssistantId(int id);

	@Query("select t.estimatedTotalTime FROM Tutorial t where t.assistant.userAccount.id = :id")
	List<Double> findAllTutorialTimesByAssistantId(int id);

	default Double findDeviationTutorialTimeByAssistantId(final int id) {
		final double avg = this.findAverageTutorialTimeByAssistantId(id);
		return Math.sqrt(this.findAllTutorialTimesByAssistantId(id).stream().mapToDouble(x -> x).map(x -> x - avg).map(x -> x * x).average().orElse(0.0));
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

	/*
	 * @Query("select a from Auditor a where a.userAccount.id = :accountId")
	 * Auditor findAuditorByAccountId(int accountId);
	 * 
	 * @Query("select count(a) from Audit a where a.auditor.id = :id")
	 * Double totalNumberOfAudits(int id);
	 * 
	 * @Query("select avg(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	 * Double averageNumberOfAuditingRecords(int id);
	 * 
	 * @Query("select count(ar) from AuditingRecord ar where ar.audit.auditor.id = :id group by ar.audit ")
	 * List<Integer> numberOfRecordsByAudit(int id);
	 * 
	 * @Query("select min(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	 * Double minimumNumberOfAuditingRecords(int id);
	 * 
	 * @Query("select max(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	 * Double maximumNumberOfAuditingRecords(int id);
	 * 
	 * @Query("select ar from AuditingRecord ar where ar.audit.auditor.id = :id")
	 * List<AuditingRecord> findAllAuditingRecordsByAuditorId(int id);
	 */

}
