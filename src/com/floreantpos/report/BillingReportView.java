package com.floreantpos.report;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;

import com.floreantpos.Messages;
import com.floreantpos.POSConstants;
import com.floreantpos.model.CreditCardTransaction;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketTax;
import com.floreantpos.model.dao.PosTransactionDAO;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.model.util.DateUtil;
import com.floreantpos.report.service.ReportService;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.util.UiUtil;
import com.floreantpos.util.CurrencyUtil;
import com.floreantpos.util.NumberUtil;

import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.swing.JRViewer;

public class BillingReportView extends JPanel {
	private JXDatePicker fromDatePicker = UiUtil.getCurrentMonthStart();
	private JXDatePicker toDatePicker = UiUtil.getCurrentMonthEnd();
	private JButton btnGo = new JButton(com.floreantpos.POSConstants.GO);
	private JPanel reportContainer;
	
	public BillingReportView() {
		super(new BorderLayout());
		
		JPanel topPanel = new JPanel(new MigLayout());
		
		topPanel.add(new JLabel(com.floreantpos.POSConstants.FROM + ":"), "grow"); //$NON-NLS-1$ //$NON-NLS-2$
		topPanel.add(fromDatePicker); //$NON-NLS-1$
		topPanel.add(new JLabel(com.floreantpos.POSConstants.TO + ":"), "grow"); //$NON-NLS-1$ //$NON-NLS-2$
		topPanel.add(toDatePicker); //$NON-NLS-1$
		topPanel.add(btnGo, "skip 1, al right"); //$NON-NLS-1$
		add(topPanel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(0, 10,10,10));
		centerPanel.add(new JSeparator(), BorderLayout.NORTH);
		
		reportContainer = new JPanel(new BorderLayout());
		centerPanel.add(reportContainer);
		
		add(centerPanel);
		
		btnGo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					viewReport();
				} catch (Exception e1) {
					POSMessageDialog.showError(BillingReportView.this, POSConstants.ERROR_MESSAGE, e1);
				}
			}
			
		});
	}
	
	private void viewReport() throws Exception {
		String currencySymbol = CurrencyUtil.getCurrencySymbol();
		Date fromDate = fromDatePicker.getDate();
		Date toDate = toDatePicker.getDate();
		
		if(fromDate.after(toDate)) {
			POSMessageDialog.showError(com.floreantpos.util.POSUtil.getFocusedWindow(), com.floreantpos.POSConstants.FROM_DATE_CANNOT_BE_GREATER_THAN_TO_DATE_);
			return;
		}
		
		fromDate = DateUtil.startOfDay(fromDate);
		toDate = DateUtil.endOfDay(toDate);
		
		Date currentTime = new Date();
		
		List<Ticket> tickets = (List<Ticket>) TicketDAO.getInstance().findTickets(fromDate, toDate,false);
		
		int ticketCount = 0;
		double totalCgst = 0;
		double totalSgst = 0;
		double totalTax = 0;
		double totalAmount = 0;
		if (tickets.size()>0){
			for (Ticket ticket : tickets) {
				++ticketCount;
				for(final TicketTax ticketTax: ticket.getTicketTaxes() ){
					String ticketTaxName = ticketTax.getName();
					if ( ticketTaxName.equals("SGST"))
						totalSgst = totalSgst + ticketTax.getAmount();
					else if (ticketTaxName.equals("CGST"))
						totalCgst = totalCgst + ticketTax.getAmount();
					}
				totalAmount = totalAmount + ticket.getPaidAmount();
			}
		}
		totalTax = totalSgst + totalCgst;
		HashMap map = new HashMap();
		ReportUtil.populateRestaurantProperties(map);
		map.put("reportTitle", Messages.getString("BillingReport.1")); //$NON-NLS-1$ //$NON-NLS-2$
		map.put("fromDate", ReportService.formatShortDate(fromDate)); //$NON-NLS-1$
		map.put("toDate", ReportService.formatShortDate(toDate)); //$NON-NLS-1$
		map.put("reportTime", ReportService.formatFullDate(currentTime)); //$NON-NLS-1$
		
		map.put("ticketCount", String.valueOf(ticketCount)); //$NON-NLS-1$
		map.put("totalCgst", currencySymbol + " " + NumberUtil.formatNumber(totalCgst,2)); //$NON-NLS-1$
		map.put("totalSgst", currencySymbol + " " + NumberUtil.formatNumber(totalSgst,2)); //$NON-NLS-1$
		map.put("totalTax", currencySymbol + " " + NumberUtil.formatNumber(totalTax,2)); //$NON-NLS-1$
		map.put("totalAmount", currencySymbol + " " + NumberUtil.formatNumber(totalAmount,2));
		
		JasperReport jasperReport = ReportUtil.getReport("billing-report"); //$NON-NLS-1$
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRTableModelDataSource(new BillingReportModel(tickets)));
		JRViewer viewer = new JRViewer(jasperPrint);
		reportContainer.removeAll();
		reportContainer.add(viewer);
		reportContainer.revalidate();
		
	}
	

}
