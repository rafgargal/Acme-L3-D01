
package acme.features.auditor.auditingRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.auditing.AuditingRecord;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditingRecordController extends AbstractController<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordListService	listService;

	@Autowired
	protected AuditorAuditingRecordShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		//super.addBasicCommand("create", this.createService);
		//super.addBasicCommand("update", this.updateService);
		//super.addBasicCommand("delete", this.deleteService);
		//super.addCustomCommand("publish", "update", this.publishService);
		//super.addCustomCommand("correct", "create", this.correctService);
	}
}
