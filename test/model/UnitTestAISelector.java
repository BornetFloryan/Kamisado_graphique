package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTestAISelector {
    @Test
    public void testSetAIEasy() {
        AISelector.setAI(0, "Easy");
        assertEquals("Easy", AISelector.getDecider(0));
    }

    @Test
    public void testSetAIHard() {
        AISelector.setAI(1, "Hard");
        assertEquals("Hard", AISelector.getDecider(1));
    }
}
