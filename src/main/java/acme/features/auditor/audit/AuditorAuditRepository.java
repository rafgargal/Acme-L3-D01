
package acme.features.auditor.audit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditing.Audit;
import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select x from Audit x where x.id = :id")
	Audit findOneAuditById(int id);

	@Query("select x from Audit x")
	List<Audit> findAllAudit();

	@Query("select x from Audit x where x.auditor.userAccount.id = :id")
	List<Audit> findAllAuditByAuditorId(int id);

	@Query("select y from Auditor y where y.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select y from Auditor y")
	List<Auditor> findAllAuditor();

	@Query("select x from Audit x where x.code = :code")
	Audit findOneAuditByCode(String code);

	@Query("select c from Course c")
	List<Course> findAllCourses();

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

}
