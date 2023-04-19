
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int id;
		Banner object;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

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

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd")) {
			Date minDisplayPeriodEnd;

			minDisplayPeriodEnd = MomentHelper.deltaFromMoment(object.getDisplayPeriodStart(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getDisplayPeriodEnd(), minDisplayPeriodEnd), "displayPeriodEnd", "administrator.banner.form.error.displayPeriodEnd-too-soon");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		Date currentMoment;

		currentMoment = MomentHelper.getCurrentMoment();
		object.setInstantiationOrUpdateMoment(currentMoment);

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
