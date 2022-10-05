package net.revature.service;

import net.revature.exception.TicketAlreadyProcessedException;
import net.revature.exception.TicketNotFoundException;
import net.revature.model.Reimbursement;
import net.revature.repository.ReimbursementRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ReimbursementService {
    private ReimbursementRepository reimbursementRepository = new ReimbursementRepository();

    public List<Reimbursement> getAllReimbursements() throws SQLException {
        return reimbursementRepository.getAllReimbursements();
    }

    public List<Reimbursement> getAllReimbursementsForEmployee(int empId) throws SQLException {
        return reimbursementRepository.getAllReimbursementsForEmployee(empId);
    }

    public boolean processTicket(int ticketId, String ticketStatus, int manId) throws SQLException, TicketNotFoundException, TicketAlreadyProcessedException {
        // Check if status isn't  Approved  or Denied.
        if (!ticketStatus.equalsIgnoreCase("Approved")) {
            if(!ticketStatus.equalsIgnoreCase("Denied")){

                throw new IllegalArgumentException("Ticket status must be set to 'Approved' or 'Denied'!");

            }
        }

        // Check if ticket does not exist
        Reimbursement reimbursement = reimbursementRepository.getReimbursementById(ticketId);
        if (reimbursement == null) {
            throw new TicketNotFoundException("Ticket with id " + ticketId + " was not found!");
        }

        // Already processed
        if (!reimbursement.getTicketStatus().equalsIgnoreCase("Pending") && manId != 0) {
            throw new TicketAlreadyProcessedException("Ticket with id " + ticketId + " has already been processed!");
        }

        // Processing ticket
        return reimbursementRepository.processTicket(ticketId, ticketStatus, manId);
    }

//    public Reimbursement processTicket(int ticketId, String ticketStatus, int manId) throws SQLException, TicketNotFoundException, TicketAlreadyProcessedException {
//        // Check if status isn't  Approved  or Denied.
//        if (!ticketStatus.equalsIgnoreCase("Approved")) {
//            if(!ticketStatus.equalsIgnoreCase("Denied")){
//
//                throw new IllegalArgumentException("Ticket status must be set to 'Approved' or 'Denied'!");
//
//            }
//        }
//
//        // Check if ticket does not exist
//        Reimbursement reimbursement = reimbursementRepository.getReimbursementById(ticketId);
//        if (reimbursement == null) {
//            throw new TicketNotFoundException("Ticket with id " + ticketId + " was not found!");
//        }
//
//        // Already processed
//        if (!Objects.equals(reimbursement.getTicketStatus(), "Pending")) {
//            throw new TicketAlreadyProcessedException("Ticket with id " + ticketId + " has already been processed!");
//        }
//
//        // Processing ticket
//        return reimbursementRepository.processTicket(reimbursement);
//    }

    public Reimbursement requestTicket(Reimbursement reimbursement) throws SQLException {

        return reimbursementRepository.addTicket(reimbursement);

    }
}
