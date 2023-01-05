package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

//use the @SpringBootTest annotation to create a test with the Spring Context initialized
//This annotation means that all the actual beans such as bookService and all of its dependencies will be injected into the context when we run the test.
//Consequently,the test will take much longer to execute than Sample01,which was a simple unit test with Mockito extension.
//So whenever possible,try to write unit tests like Sample01,because they will execute much quicker.
//When you do need to work with the spring context,however,remember to use the @MockBean annotation or @SpyBean to replace a real bean in the Spring container with a mocked one.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Sample02ControllerSampleTest {

	@LocalServerPort
	private int port;

	private URL base;

	//MockBean replaces the real bean with a mocked bean. And it will be injected into spring context when we run the test.
	@MockBean
	private BookingService bookingService;

	@Autowired
	private TestRestTemplate template;
	
	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/greeting");
	}

	/**
	 * @description:
	 *  If not use MockBean,the SpringBootTest will inject the real BookingService,and the result is 16 guests.
	 *  If use MockBean,the result is 0 guests,because Mockito returns nice default values for primitives such as integers.
	 *  So as usual,we can use the when...thenReturn pattern or bdd style given...willReturn to somehow change the default behavior and the default return value of our model.
	 * @throws Exception
	 */
	@Test
	public void getHello() throws Exception {
		// given
		String expected = "Greetings from The Happy Hotel. We've got enough beds for 10 guests!";
		//We injected a mocked bean,defined its behavior.
		//when(bookingService.getAvailablePlaceCount()).thenReturn(10);
		given(bookingService.getAvailablePlaceCount()).willReturn(10);

		// when
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String actual = response.getBody();

		// then
		assertEquals(expected, actual);
	}

}
