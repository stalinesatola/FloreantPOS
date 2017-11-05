package com.floreantpos.model.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the TICKET_ITEM_TAX table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TICKET_ITEM_TAX"
 */

public class BaseTicketItemTax implements Comparable, Serializable{

	// constructors
		public BaseTicketItemTax () {
			initialize();
		}

		/**
		 * Constructor for primary key
		 */
		public BaseTicketItemTax (java.lang.Integer id) {
			this.setId(id);
			initialize();
		}

		protected void initialize () {}
		
		private int hashCode = Integer.MIN_VALUE;

		// primary key
		private java.lang.Integer id;

		// fields
		protected java.lang.String name;
		protected java.lang.Double rate;
		protected java.lang.Double amount;

		// many to one
		private com.floreantpos.model.TicketItem ticketItem;
		
		//many to one transient
		private com.floreantpos.model.Tax tax;
		
		public java.lang.Integer getId() {
			return id;
		}

		public void setId(java.lang.Integer id) {
			this.id = id;
		}

		public java.lang.String getName() {
			return name;
		}

		public void setName(java.lang.String name) {
			this.name = name;
		}

		public java.lang.Double getRate() {
			return rate;
		}

		public void setRate(java.lang.Double rate) {
			this.rate = rate;
		}

		public java.lang.Double getAmount() {
			return amount;
		}

		public void setAmount(java.lang.Double amount) {
			this.amount = amount;
		}

		public com.floreantpos.model.Tax getTax() {
			return tax;
		}

		public void setTax(com.floreantpos.model.Tax tax) {
			this.tax = tax;
		}

		public com.floreantpos.model.TicketItem getTicketItem() {
			return ticketItem;
		}

		public void setTicketItem(com.floreantpos.model.TicketItem ticketItem) {
			this.ticketItem = ticketItem;
		}
		
		public boolean equals (Object obj) {
			if (null == obj) return false;
			if (!(obj instanceof com.floreantpos.model.TicketTax)) return false;
			else {
				com.floreantpos.model.TicketTax ticketTax = (com.floreantpos.model.TicketTax) obj;
				if (null == this.getId() || null == ticketTax.getId()) return false;
				else return (this.getId().equals(ticketTax.getId()));
			}
		}

		public int hashCode () {
			if (Integer.MIN_VALUE == this.hashCode) {
				if (null == this.getId()) return super.hashCode();
				else {
					String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
					this.hashCode = hashStr.hashCode();
				}
			}
			return this.hashCode;
		}

		public int compareTo (Object obj) {
			if (obj.hashCode() > hashCode()) return 1;
			else if (obj.hashCode() < hashCode()) return -1;
			else return 0;
		}

		public String toString () {
			return super.toString();
		}
}
