
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.entities.practicumSessions.PracticumSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p WHERE p.company.id = :companyId")
	Collection<Practicum> findByPracticumByCompanyId(int companyId);

	@Query("SELECT p FROM Practicum p WHERE p.id = :practicumId")
	Practicum findPracticumById(int practicumId);

	@Query("SELECT c FROM Company c WHERE c.id = :companyId")
	Company findCompanyById(int companyId);

	@Query("SELECT c FROM  Course c WHERE c.id = :courseId")
	Course findCourseById(int courseId);

	@Query("SELECT c FROM Course c")
	Collection<Course> findAllCourses();

	@Query("SELECT ps FROM PracticumSession ps WHERE ps.practicum.id = :practicumId")
	Collection<PracticumSession> findPracticumSessionsByPracticumId(int practicumId);

}
