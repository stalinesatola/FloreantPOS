package com.floreantpos.model.base;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * This is an object that contains data related to the TICKET_TAX table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TICKET_TAX"
 */

public class BaseTicketTax implements Comparable, Serializable{
	
	// constructors
		public BaseTicketTax () {
			initialize();
		}

		/**
		 * Constructor for primary key
		 */
		public BaseTicketTax (java.lang.Integer id) {
			this.setId(id);
			initialize();
		}

		protected void initialize () {}
		
		private int hashCode = Integer.MIN_VALUE;

		// primary key
		private java.lang.Integer id;

		// fields
		
		// many to one transient
		protected java.lang.String name;
		protected java.lang.Double rate;	
		protected java.lang.Double amount;

		// many to one
		private com.floreantpos.model.Ticket ticket;
		
		public java.lang.Integer getId() {
			return id;
		}

		public void setId(java.lang.Integer id) {
			this.id = id;
			this.hashCode = Integer.MIN_VALUE;
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

		public com.floreantpos.model.Ticket getTicket() {
			return ticket;
		}

		public void setTicket(com.floreantpos.model.Ticket ticket) {
			this.ticket = ticket;
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
