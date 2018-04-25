package com.floreantpos.report;

public class BillingReport extends Report {
	private SalesReportModel itemReportModel;
	private SalesReportModel modifierReportModel;

	public BillingReport() {
		super();
	}

	@Override
	public void refresh() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDateRangeSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTypeSupported() {
		// TODO Auto-generated method stub
		return false;
	}

}
