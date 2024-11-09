package com.food.delivery.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageResponseTest {

    private MessageResponse messageResponse;

    @BeforeEach
    void setUp() {
        messageResponse = new MessageResponse("Success");
    }

    @Test
    void testGetMessage() {
        // Check if the getter returns the correct message
        String expectedMessage = "Success";
        assertEquals(expectedMessage, messageResponse.getMessage());
    }

    @Test
    void testSetMessage() {
        // Test the setter method for message
        String newMessage = "Error";
        messageResponse.setMessage(newMessage);
        assertEquals(newMessage, messageResponse.getMessage());
    }

    @Test
    void testMessageResponseConstructor() {
        // Verify the constructor initializes the message correctly
        MessageResponse newMessageResponse = new MessageResponse("Initialized");
        assertEquals("Initialized", newMessageResponse.getMessage());
    }
}
