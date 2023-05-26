
package acme.testing.assistant.session;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantSessionTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username")
	Collection<Tutorial> findManyTutorialsByAssistantUsername(final String username);

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username order by t.code")
	List<Tutorial> findManyTutorialsByAssistantUsernameOrderedByCode(final String username);

	@Query("select s from Session s where s.tutorial.assistant.userAccount.username = :username")
	Collection<Session> findManySessionsByAssistantUsername(final String username);

}
