package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

/**
 * To sum up, verify(...) is a very nice method that is often used in the "then" section of Mockito tests
 * and that is verifies whether certain mocks were called or not called as expected.
 */
class Test07VerifyingBehaviour {
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

        this.bookingService = new BookingService(paymentServiceMock,roomServiceMock,bookingDAOMock,mailSenderMock);
    }

    @Test
    void should_InvokePayment_When_Prepaid(){
        //BDD Style
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 5),2,true);

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        //verify(paymentServiceMock).pay(bookingRequest,500.0);
        //verify(paymentServiceMock).pay(bookingRequest,400.0);
        //We can also add a second argument here,times(1),to make sure that it was called exactly once.
        verify(paymentServiceMock,times(1)).pay(bookingRequest,400.0);
        //verify(paymentServiceMock,times(2)).pay(bookingRequest,400.0);

        //This Mockito method checks if any other methods from this mock were called
        //If pay(...) is called again for a second time,this will also throw an exception
        //This means that paymentService was only used once when we checked the makingBooking method call and pay(...) method was invoked exactly once.
        verifyNoMoreInteractions(paymentServiceMock);
    }

    @Test
    void should_InvokePayment_When_NotPrepaid(){
        //BDD Style
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 5),2,false);

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        //Verify that paymentService was never called and the method pay(...) was never invoked for any kind of input.
        verify(paymentServiceMock,never()).pay(any(),anyDouble());
        verifyNoInteractions(paymentServiceMock);
    }
}
