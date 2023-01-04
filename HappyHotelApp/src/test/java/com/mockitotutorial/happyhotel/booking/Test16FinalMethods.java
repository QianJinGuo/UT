package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class Test16FinalMethods {
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

    /*
     * test final methods should use mockito-inline
     *
     * but for private methods,they are only used internally by the given class,you should never test them with unit test.
     * If you are trying to mock a private method,why don't you mock the actual non-private method within the class and that calls the private methods?
     * Mockito doesn't allow mocking private methods,this is a feature and not a bug
     *
     * To sum up,
     * Final methods >> use mockito-inline
     * Private methods >> don't try to mock them!
     */
    @Test
    void should_CountAvailablePlaces_When_OneRoomAvailable(){
        //given
        //We change the default behavior of our mock,which was returning an empty list into returning a single-element list.
        when(this.roomServiceMock.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room 1",5)));
        int expected = 5;

        //when
        int actual = bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected,actual);
    }

}
