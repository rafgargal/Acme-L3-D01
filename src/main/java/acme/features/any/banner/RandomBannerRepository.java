
package acme.features.any.banner;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface RandomBannerRepository extends AbstractRepository {

	@Query("select b from Banner b where b.displayPeriodStart <= :date and b.displayPeriodEnd > :date")
	List<Banner> findActiveBanners(Date date);
}
