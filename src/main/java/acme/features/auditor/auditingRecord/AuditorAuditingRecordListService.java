
package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditing.Audit;
import acme.entities.auditing.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordListService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("auditId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		final Audit audit = this.repository.findAuditById(super.getRequest().getData("auditId", int.class));

		super.getResponse().setAuthorised(super.getRequest().getPrincipal().hasRole(audit.getAuditor()));
	}

	@Override
	public void load() {
		Collection<AuditingRecord> objects;
		int auditId;

		auditId = super.getRequest().getData("auditId", int.class);
		objects = this.repository.findAllAuditingRecordsByAuditId(auditId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;

		final String format = "dd/MM/yyyy hh:mm";

		tuple = super.unbind(object, "subject", "draftMode");

		tuple.put("mark", object.getMark().toString());
		tuple.put("auditPeriodInicial", MomentHelper.format(format, object.getAuditPeriodInicial()));
		tuple.put("auditPeriodFinal", MomentHelper.format(format, object.getAuditPeriodFinal()));

		super.getResponse().setData(tuple);
	}

}
