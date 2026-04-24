package ru.nsu.ga.grentseva.checker.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CheckpointTest {

    @Test
    void testGettersAndSetters() {
        Checkpoint checkpoint = new Checkpoint();

        String name = "Контрольная точка 1";
        LocalDate date = LocalDate.of(2025, 10, 15);

        checkpoint.setName(name);
        checkpoint.setDate(date);

        assertEquals(name, checkpoint.getName());
        assertEquals(date, checkpoint.getDate());
    }

    @Test
    void testEmptyCheckpoint() {
        Checkpoint checkpoint = new Checkpoint();

        assertNull(checkpoint.getName());
        assertNull(checkpoint.getDate());
    }
}