package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageResponseTest {

    private MessageResponse messageResponse;

    @BeforeEach
    void setUp() {
        messageResponse = new MessageResponse("Success");
    }

    @Test
    void testGetMessage() {
        String expectedMessage = "Success";
        assertEquals(expectedMessage, messageResponse.getMessage());
    }

    @Test
    void testSetMessage() {
        String newMessage = "Error";
        messageResponse.setMessage(newMessage);
        assertEquals(newMessage, messageResponse.getMessage());
    }

    @Test
    void testMessageResponseConstructor() {
        MessageResponse newMessageResponse = new MessageResponse("Initialized");
        assertEquals("Initialized", newMessageResponse.getMessage());
    }
}
