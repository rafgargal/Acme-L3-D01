
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;

		object = new Banner();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationOrUpdateMoment", "displayPeriodStart", "displayPeriodEnd", "pictureLink", "slogan", "webDocLink");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodStart") || !super.getBuffer().getErrors().hasErrors("displayPeriodEnd")) {
			super.state(MomentHelper.isAfterOrEqual(object.getDisplayPeriodStart(), object.getInstantiationOrUpdateMoment()), "displayPeriodStart", "administrator.banner.form.error.displayPeriodStart-before-instantiationOrUpdateMoment");
			super.state(MomentHelper.isAfter(object.getDisplayPeriodEnd(), object.getDisplayPeriodStart()), "displayPeriodEnd", "administrator.banner.form.error.displayPeriodEnd-before-displayPeriodStart");
			super.state(MomentHelper.isLongEnough(object.getDisplayPeriodStart(), object.getDisplayPeriodEnd(), 7, ChronoUnit.DAYS), "*", "administrator.banner.form.error.displayPeriodEnd-too-soon");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationOrUpdateMoment", "displayPeriodStart", "displayPeriodEnd", "pictureLink", "slogan", "webDocLink");

		super.getResponse().setData(tuple);
	}

}
