package ru.nsu.ga.grentseva.checker.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsTest {

    @Test
    void defaultValuesAreCorrect() {

        Settings settings =
                new Settings();

        assertEquals(
                0.5,
                settings.getSoftDeadlinePenalty()
        );

        assertEquals(
                1.0,
                settings.getMaxBonus()
        );

        assertEquals(
                60,
                settings.getTestTimeoutSeconds()
        );
    }

    @Test
    void setSoftDeadlinePenaltyChangesValue() {

        Settings settings =
                new Settings();

        settings.setSoftDeadlinePenalty(0.75);

        assertEquals(
                0.75,
                settings.getSoftDeadlinePenalty()
        );
    }

    @Test
    void setMaxBonusChangesValue() {

        Settings settings =
                new Settings();

        settings.setMaxBonus(3.0);

        assertEquals(
                3.0,
                settings.getMaxBonus()
        );
    }

    @Test
    void setTestTimeoutSecondsChangesValue() {

        Settings settings =
                new Settings();

        settings.setTestTimeoutSeconds(120);

        assertEquals(
                120,
                settings.getTestTimeoutSeconds()
        );
    }

    @Test
    void toStringContainsFields() {

        Settings settings =
                new Settings();

        String text =
                settings.toString();

        assertTrue(
                text.contains("softDeadlinePenalty")
        );

        assertTrue(
                text.contains("maxBonus")
        );

        assertTrue(
                text.contains("testTimeoutSeconds")
        );
    }
}