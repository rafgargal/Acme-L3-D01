
package acme.features.auditor.auditingRecord;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditing.Audit;
import acme.entities.auditing.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditingRecordRepository extends AbstractRepository {

	@Query("select y from AuditingRecord y where y.audit.id = :auditId")
	List<AuditingRecord> findAllAuditingRecordsByAuditId(int auditId);

	@Query("select x from Audit x where x.id = :auditId")
	Audit findAuditById(int auditId);

	@Query("select x from Audit x where x.draftMode = false")
	List<Audit> findAllPublishedAudits();

	@Query("select y from AuditingRecord y where y.id = :id")
	AuditingRecord findAuditingRecordById(int id);

}
