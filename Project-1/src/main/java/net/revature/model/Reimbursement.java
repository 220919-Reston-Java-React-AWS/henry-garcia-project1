package net.revature.model;

import java.util.Objects;

public class Reimbursement {

    private int id;
    private int amount; // Not negative
    private String desc; //Reason/Description
    private String ticketStatus; // 0 by default
    private int empid;
    private int manid;

    public Reimbursement(){}

    public Reimbursement(int id, int amount, String desc, String ticketStatus, int empid, int manid){

        this.id = id;
        this.amount = amount;
        this.desc = desc;
        this.ticketStatus = ticketStatus;
        this.empid = empid;
        this.manid = manid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public int getManid() {
        return manid;
    }

    public void setManid(int manid) {
        this.manid = manid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return id == that.id && amount == that.amount && empid == that.empid && manid == that.manid && desc.equals(that.desc) && Objects.equals(ticketStatus, that.ticketStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, desc, ticketStatus, empid, manid);
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", amount=" + amount +
                ", desc='" + desc + '\'' +
                ", ticketStatus='" + ticketStatus + '\'' +
                ", empid=" + empid +
                ", manid=" + manid +
                '}';
    }
}
