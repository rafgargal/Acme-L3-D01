
package acme.testing.assistant.tutorial;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username")
	Collection<Tutorial> findManyTutorialsByAssistantUsername(final String username);

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username order by t.code")
	List<Tutorial> findManyTutorialsByAssistantUsernameOrderedByCode(final String username);

}
