package com.musala.sg.drones.domain.core.api;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicationTest {

    @Nested
    class NameTest {
        @Test
        void that_throws_when_name_is_null() {
            assertThrows(NullPointerException.class, () -> new Medication(null, "code1", 2, "url"));
        }

        @Test
        void that_throws_when_name_is_incorrect() {
            expectThrows("1name");
            expectThrows("name1");
            expectThrows("na1me");
            expectThrows("na me");
            expectThrows("name!");
            expectThrows("!name");
            expectThrows("na,me");
        }

        @Test
        void that_doesnt_throw_when_name_is_correct() {
            assertDoesNotThrow(() -> new Medication("A_b-Csas_", "CODE1", 2, "url"));
            assertDoesNotThrow(() -> new Medication("-A_b-Csas-", "CODE1", 2, "url"));
            assertDoesNotThrow(() -> new Medication("_A_b-Csas_", "CODE1", 2, "url"));
            assertDoesNotThrow(() -> new Medication("abcQwert", "CODE1", 2, "url"));
        }

        private void expectThrows(String name) {
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Medication(name, "code1", 2, "url"));
            assertEquals("Name should contains only letters, '_', '-', but it is " + name, exception.getMessage());
        }
    }

    @Nested
    class CodeTest {
        @Test
        void that_throws_when_name_is_null() {
            assertThrows(NullPointerException.class, () -> new Medication("name", null, 2, "url"));
        }
        @Test
        void that_throws_when_code_is_incorrect() {
            expectThrows("coDe");
            expectThrows("CODE-");
            expectThrows("-CODE");
            expectThrows("CO-DE");
            expectThrows("CO DE");
            expectThrows("CODE!");
            expectThrows("!CODE");
            expectThrows("CO,DE");
        }

        @Test
        void that_doesnt_throw_when_code_is_correct() {
            assertDoesNotThrow(() -> new Medication("name", "CODE1", 2, "url"));
            assertDoesNotThrow(() -> new Medication("name", "1CODE", 2, "url"));
            assertDoesNotThrow(() -> new Medication("name", "1CODE1", 2, "url"));
            assertDoesNotThrow(() -> new Medication("name", "_CO_DE_1_", 2, "url"));
        }

        private void expectThrows(String code) {
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Medication("name", code, 2, "url"));
            assertEquals("Code should contains only uppercase letters, '_', numbers, but it is " + code, exception.getMessage());
        }
    }

    @Nested
    class WeightTest {
        @Test
        void that_throws_when_weight_is_incorrect() {
            expectThrows(-1);
            expectThrows(-100);
            expectThrows(0);
        }

        @Test
        void that_doesnt_throw_when_weight_is_correct() {
            assertDoesNotThrow(() -> new Medication("name", "CODE1", 1, "url"));
            assertDoesNotThrow(() -> new Medication("name", "1CODE", 2, "url"));
            assertDoesNotThrow(() -> new Medication("name", "1CODE1", 100, "url"));
        }

        private void expectThrows(int weight) {
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Medication("name", "CODE", weight, "url"));
            assertEquals("Weight should be more than 1g, but it is " + weight, exception.getMessage());
        }
    }
}