
package acme.features.any.banner;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Any;
import acme.framework.services.AbstractService;

@Service
public class RandomBannerService extends AbstractService<Any, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected RandomBannerRepository repository;

	// AbstractService interface ---------------------------


	public Banner getRandomBanner() {
		List<Banner> objects;

		objects = this.repository.findActiveBanners(new Date());
		Banner object = null;

		// Choose one randomly
		if (!objects.isEmpty()) {
			final Random rand = new Random();
			object = objects.get(rand.nextInt(objects.size()));
		}
		return object;
	}

}
