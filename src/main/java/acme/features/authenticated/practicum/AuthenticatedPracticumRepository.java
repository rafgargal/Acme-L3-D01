
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p WHERE p.course.id = :id")
	Collection<Practicum> findPracticumByCourseId(int id);

	@Query("SELECT p FROM Practicum p WHERE p.id = :practicumId")
	Practicum findPracticumById(int practicumId);

}
