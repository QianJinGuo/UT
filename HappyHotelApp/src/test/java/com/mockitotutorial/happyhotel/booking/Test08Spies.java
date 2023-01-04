package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class Test08Spies {
    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        //this.bookingDAOMock = mock(BookingDAO.class);
        this.bookingDAOMock = spy(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_MakeBooking_When_InputOK() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 5), 2, true);

        //when
        String bookingId = bookingService.makeBooking(bookingRequest);

        //then
        verify(bookingDAOMock).save(bookingRequest);
        //If mock(bookingDAOMock),bookId=null, if spy(bookingDAOMock),bookId has values.

        //The differences between mocks and spies is that
        //mocks don't have any logic of the mocked class,and they simply return the default values such as nulls or empty,unless we change their behaviors.
        //spies,in turn,have all the logic from the mocked class,so they behave just like a normal object of the class unless you modify some behaviors.
        //In other words,for spies,we call the actual methods from the actual classes.

        //To sum up
        //mock = dummy object with no real logic
        //spy = real object with real logic that we can modify
        System.out.println("bookId=" + bookingId);
    }

    @Test
    void should_CancelBooking_When_InputOK() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 5), 2, true);
        bookingRequest.setRoomId("1.3");

        String bookingId = "1";

        //mocks: when(mock.method()).thenReturn()
        //spies: doReturn().when(spy()).method()
        doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);

        //when
        bookingService.cancelBooking(bookingId);

        //then
        /*
         * To sum up
         * a spy is a partial mock. You can still modify its behavior make it return the values that you want,
         * but by default,it uses the code from the actual class.
         */
    }

}
