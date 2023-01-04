package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

/**
 * To sum up, verify(...) is a very nice method that is often used in the "then" section of Mockito tests
 * and that is verifies whether certain mocks were called or not called as expected.
 */
class Test10ArgumentCaptors {
    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;
    private ArgumentCaptor<Double> doubleCaptor;
    //private ArgumentCaptor<BookingRequest> bookingRequestArgumentCaptor;

    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock,roomServiceMock,bookingDAOMock,mailSenderMock);
        this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
    }

    @Test
    void should_PayCorrectPrice_When_InputOK(){
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 5),2,true);

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        verify(paymentServiceMock,times(1)).pay(eq(bookingRequest),doubleCaptor.capture());
        double capturedArgument = doubleCaptor.getValue();

        assertEquals(400.0,capturedArgument);
    }

    @Test
    void should_PayCorrectPrice_When_MultipleCalls(){
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 5),2,true);
        BookingRequest bookingRequest2 = new BookingRequest("1", LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 2),2,true);
        List<Double> expectedValues = Arrays.asList(400.0,100.0);
        //when
        bookingService.makeBooking(bookingRequest);
        bookingService.makeBooking(bookingRequest2);

        //then
        verify(paymentServiceMock,times(2)).pay(any(),doubleCaptor.capture());
        List<Double> capturedArguments = doubleCaptor.getAllValues();

        assertEquals(expectedValues,capturedArguments);
    }


}
