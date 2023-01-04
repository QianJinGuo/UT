package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * To sum up, verify(...) is a very nice method that is often used in the "then" section of Mockito tests
 * and that is verifies whether certain mocks were called or not called as expected.
 */
//Junit5
@ExtendWith(MockitoExtension.class)
//Junit4
//@RunWith
class Test11Annotations {
    //We need to inject the mocks annotated with the @Mock annotation,it will also inject spies.
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
