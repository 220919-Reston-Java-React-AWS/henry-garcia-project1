package net.revature.service;

import net.revature.exception.TicketAlreadyProcessedException;
import net.revature.exception.TicketNotFoundException;
import net.revature.model.Reimbursement;
import net.revature.repository.ReimbursementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReimbursementServiceTest {

    @Mock
    private ReimbursementRepository reimbursementRepository;

    @InjectMocks
    private ReimbursementService rs;

    @Test
    public void testInvalidTicketStatus() throws SQLException, TicketAlreadyProcessedException, TicketNotFoundException {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {

            rs.processTicket(24,"Aproved",1);

        });

    }

    @Test
    public void testTicketDoesNotExist() throws SQLException {

        when(reimbursementRepository.getReimbursementById(eq(240))).thenReturn(null);

        Assertions.assertThrows(TicketNotFoundException.class, () -> {

            rs.processTicket(240,"Approved",1);

        });
    }

    @Test
    public void testTicketAlreadyProcessed() throws SQLException {

        when(reimbursementRepository.getReimbursementById(eq(20))).thenReturn(new Reimbursement(20,500,"Travel Expenses","Denied",2,1));

        Assertions.assertThrows(TicketAlreadyProcessedException.class, () -> {

            rs.processTicket(20,"Approved",1);

        });
    }

    @Test
    public void testProcessTicketSuccess() throws SQLException, TicketAlreadyProcessedException, TicketNotFoundException {

        when(reimbursementRepository.getReimbursementById(eq(1))).thenReturn(new Reimbursement(1,200,"Test Description","Pending",3,0));

        when(reimbursementRepository.processTicket(eq(1), eq("Approved"), eq(2))).thenReturn(true);

        boolean st = rs.processTicket(1,"Approved",2);

        Assertions.assertTrue(st);

    }


}
