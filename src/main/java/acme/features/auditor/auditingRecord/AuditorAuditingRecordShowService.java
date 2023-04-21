
package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditing.AuditingRecord;
import acme.entities.auditing.Marks;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordShowService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		AuditingRecord audRec;

		audRec = this.repository.findAuditingRecordById(super.getRequest().getData("id", int.class));

		status = audRec != null && super.getRequest().getPrincipal().hasRole(audRec.getAudit().getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditingRecordById(id);

		super.getBuffer().setData(object);

	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Marks.class, object.getMark());

		final String format = "dd/MM/yyyy hh:mm";

		tuple = super.unbind(object, "subject", "assessment", "link", "draftMode", "correction");

		tuple.put("startDate", MomentHelper.format(format, object.getAuditPeriodInicial()));
		tuple.put("finishDate", MomentHelper.format(format, object.getAuditPeriodFinal()));
		tuple.put("mark", choices);

		super.getResponse().setData(tuple);
	}
}
