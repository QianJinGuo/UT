package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Test13StrictStubbing {
    @InjectMocks
    private BookingService bookingService;
    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private RoomService roomServiceMock;
    @Spy
    private BookingDAO bookingDAOMock;
    @Mock
    private MailSender mailSenderMock;
    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Test
    void should_InvokePayment_When_Prepaid(){
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 5),2,false);
        //when(paymentServiceMock.pay(any(),anyDouble())).thenReturn("1"); // << stubbing (define behavior).

        // Stubbing in Mockito language,is pretty much the same as defining the behavior of your classes with when() in classic Mockito,or given() in BDD style Mockito.
        // If you define some behavior of the class, but you never use it later on the case,Mockito will detect such unnecessary behavior definition or stubbing.
        // Will throw an exception.This can help you maintain clean code and easy to read unit tests
        // This feature is called strict stubbing and means that you need to use the mocked methods that you define in the given section.
        // However,if for any reason you want to keep the unnecessary stubbing in place,then mockito also provides a method lenient() for that.
        lenient().when(paymentServiceMock.pay(any(),anyDouble())).thenReturn("1");

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        //no exception is thrown

    }
}
