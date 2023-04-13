
package acme.features.assistant.tutorial;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select t from Tutorial t")
	List<Tutorial> findAllTutorials();

	@Query("select t from Tutorial t where t.assistant.userAccount.id = :id")
	List<Tutorial> findTutorialsByAssistantId(int id);

}
