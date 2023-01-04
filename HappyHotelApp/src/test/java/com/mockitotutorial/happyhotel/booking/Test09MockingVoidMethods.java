package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class Test09MockingVoidMethods {
    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_ThrowException_When_MailNotReady() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5), 2, false);

        //cannot use the when(...) method with methods that return void values,in other words,that return nothing.
        //when(this.mailSenderMock.sendBookingConfirmation(any())).thenThrow(BusinessException.class);
        doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());

        //when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        //then-throw
        assertThrows(BusinessException.class, executable);
    }

    @Test
    void should_NotThrowException_When_MailNotReady() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 5), 2, false);


        //doNothing is the default behavior for void methods that you can actually skip and the test will work just fine.
        doNothing().when(mailSenderMock).sendBookingConfirmation(any());

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        //no exception thrown

        /*
         * To sum up
         * If you want to throw an exception from a void method,you need to use the doThrow...when...
         * And you can also make sure that a void method does not do anything by using doNothing.
         * Incidentally,doNothing is the default behavior for void methods that you can actually skip and the test will work just fine.
         */
    }
}
