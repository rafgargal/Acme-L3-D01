
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicumSessions.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumSessionTestRepository extends AbstractRepository {

	@Query("select sp from PracticumSession sp where sp.practicum.company.userAccount.username = :username")
	Collection<PracticumSession> findManyPracticumsSessionByCompanyUsername(String username);
}
