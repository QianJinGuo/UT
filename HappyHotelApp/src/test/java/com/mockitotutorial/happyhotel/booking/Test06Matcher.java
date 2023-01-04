package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class Test06Matcher {
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
    void should_NotCompleteBooking_When_PriceTooHigh() {
        //given
        BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 5), 2, true);
        //Either any() / any(BookingRequest.class) is ok.
        //For primitives like doubles,integers,longs or booleans,we can't use any() matcher. Instead,we need to use primitive-specific matchers such as anyDouble.
        //We should use any() for objects,but we should instead use primitive-specific methods for primitives such as doubles,integers,longs or booleans.
        when(this.paymentServiceMock.pay(any(),anyDouble())).thenThrow(BusinessException.class);
        //NullPointerException if you use any() instead of anyDouble()
        //when(this.paymentServiceMock.pay(any(),any())).thenThrow(BusinessException.class);

        //If we use any() matcher with exact values,we need to use the eq() method,which is short for "equals".
        //when(this.paymentServiceMock.pay(any(),400.0)).thenThrow(BusinessException.class);
        //when(this.paymentServiceMock.pay(any(),eq(400.0))).thenThrow(BusinessException.class);

        //You should keep in mind that anyString() not match a null String Object
        //In other words,if you use anyString(), but the method will receive a null string parameter,the invocation will not be matched.
        //Keep in mind that for nullable strin g,we should use any,just like for any other kind of object.
        //anyString()


        //when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        //then-throw
        assertThrows(BusinessException.class, executable);
    }

}
