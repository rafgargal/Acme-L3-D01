
package acme.features.any.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.banner.Banner;

@ControllerAdvice
public class RandomBannerController {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected RandomBannerService randomBannerService;

	// Constructors -----------------------------------------------------------


	@ModelAttribute("banner")
	public Banner getRandomBanner() {
		return this.randomBannerService.getRandomBanner();
	}

}
