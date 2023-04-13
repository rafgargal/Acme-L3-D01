
package acme.features.authenticated.company;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedCompanyRepository extends AbstractRepository {

	@Query("SELECT u FROM UserAccount u WHERE u.id = :id")
	UserAccount findUserAccountById(int id);

}
