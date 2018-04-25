package com.floreantpos.bo.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.report.ReportViewer;
import com.floreantpos.report.BillingReport;
import com.floreantpos.report.BillingReportView;
import com.floreantpos.report.CreditCardReportView;

public class BillingReportAction extends AbstractAction {
	public BillingReportAction() {
		super(com.floreantpos.POSConstants.BILLING_REPORT);
	}
	public BillingReportAction(String name) {
		super(name);
	}
	public BillingReportAction(String name, Icon icon) {
		super(name, icon);
	}
	public void actionPerformed(ActionEvent e) {
		BackOfficeWindow window = com.floreantpos.util.POSUtil.getBackOfficeWindow();
		JTabbedPane tabbedPane = window.getTabbedPane();

		BillingReportView reportView = null;
		int index = tabbedPane.indexOfTab(com.floreantpos.POSConstants.BILLING_REPORT);
		if (index == -1) {
			reportView = new BillingReportView();
			tabbedPane.addTab(com.floreantpos.POSConstants.BILLING_REPORT, reportView);
		}
		else {
			reportView = (BillingReportView) tabbedPane.getComponentAt(index);
		}
		
		tabbedPane.setSelectedComponent(reportView);
	}
}
