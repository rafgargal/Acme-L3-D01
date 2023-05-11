
package acme.features.authenticated.company;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface AuthenticatedCompanyRepository extends AbstractRepository {

	@Query("SELECT u FROM UserAccount u WHERE u.id = :id")
	UserAccount findUserAccountById(int id);

	@Query("SELECT c FROM Company c WHERE c.userAccount.id = :id")
	Company findCompanyByUserAccountById(int id);

	@Query("SELECT c FROM Company c WHERE c.VATnumber = :VATnumber")
	Company findCompanyByVATnumber(String VATnumber);
}
