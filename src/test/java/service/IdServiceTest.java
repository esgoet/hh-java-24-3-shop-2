package service;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IdServiceTest {

    @Test
    void generateIdTest_whenGeneratingTwice_expectDifferentIds() {
        //GIVEN
        IdService idService = new IdService();

        //WHEN
        UUID id1 = idService.generateId();
        UUID id2 = idService.generateId();

        //THEN
        assertNotEquals(id1, id2);
    }

    @Test
    void generateIdTest_whenGeneratingId_thenIdIsStored() {
        IdService idService = new IdService();
        UUID newId = idService.generateId();
        assertTrue(idService.getGeneratedIds().contains(newId));
    }

    @Test
    void generateIdTest_whenGenerating1000Times_thenNoDuplicateIds() {
        IdService idService = new IdService();
        Set<UUID> ids = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            UUID newId = idService.generateId();
            ids.add(newId);
        }
        assertEquals(ids.size(),idService.getGeneratedIds().size());
    }
}