
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p WHERE p.company.id = :companyId")
	Collection<Practicum> findByPracticumByCompanyId(int companyId);

	@Query("SELECT p FROM Practicum p WHERE p.id = :practicumId")
	Practicum findPracticumById(int practicumId);
}
