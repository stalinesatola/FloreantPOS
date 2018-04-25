package com.floreantpos.report;

import java.text.SimpleDateFormat;
import java.util.List;

import com.floreantpos.model.CreditCardTransaction;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketTax;
import com.floreantpos.swing.ListTableModel;
import com.floreantpos.util.NumberUtil;

public class BillingReportModel extends ListTableModel<Ticket> {
	final String DATE_FORMAT = "dd-MM-yyyy";
	public BillingReportModel(List<Ticket> tickets) {
		super(new String[] { "ticketId", "ticketDate", "taxSgst", "taxCgst", "totalTax","ticketTotal"}, tickets); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Ticket ticket = getRowData(rowIndex);
		switch (columnIndex) {
		case 0:
			return String.valueOf(ticket.getId());

		case 1:
			return ticket.getCreateDateFormatted();
			
		case 2:
			for(final TicketTax ticketTax: ticket.getTicketTaxes() ){
				if ( ticketTax.getName().equals("SGST"))
					return NumberUtil.formatNumber(ticketTax.getAmount(),2);
					
			}
			return "0.00";
		case 3:
			for(final TicketTax ticketTax: ticket.getTicketTaxes() ){
				if ( ticketTax.getName().equals("CGST"))
					return NumberUtil.formatNumber(ticketTax.getAmount(),2);
					
			}
			return "0.00";
		case 4:
			return NumberUtil.formatNumber(ticket.getTaxAmount(),2);
		case 5:
			return NumberUtil.formatNumber(ticket.getPaidAmount(),2);
	}
		return null;
	}

}
