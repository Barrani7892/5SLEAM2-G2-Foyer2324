package tn.esprit.spring.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class FoyerServiceImplMock {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    @BeforeEach
    void setUp() {

    }

    @Test
    @Order(1)
    void testRetrieveAllFoyers() {
        Foyer foyer1 = new Foyer(1L, "Foyer A", 100L, null, null);
        Foyer foyer2 = new Foyer(2L, "Foyer B", 200L, null, null);
        List<Foyer> foyerList = Arrays.asList(foyer1, foyer2);

        when(foyerRepository.findAll()).thenReturn(foyerList);

        List<Foyer> result = foyerService.retrieveAllFoyers();
        assertEquals(2, result.size());
        assertTrue(result.containsAll(foyerList));
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    void testRetrieveFoyer() {
        Foyer foyer = new Foyer(1L, "Foyer A", 100L, null, null);

        when(foyerRepository.findById(foyer.getIdFoyer())).thenReturn(Optional.of(foyer));

        Foyer result = foyerService.retrieveFoyer(foyer.getIdFoyer());
        assertEquals(foyer, result);
        verify(foyerRepository, times(1)).findById(foyer.getIdFoyer());
    }

    @Test
    @Order(3)
    void testModifyFoyer() {
        Foyer existingFoyer = new Foyer(1L, "Foyer A", 100L, null, null);
        existingFoyer.setCapaciteFoyer(150L); // Utilisation correcte de capaciteFoyer

        when(foyerRepository.save(existingFoyer)).thenReturn(existingFoyer);

        Foyer updatedFoyer = foyerService.modifyFoyer(existingFoyer);

        assertNotNull(updatedFoyer);
        assertEquals(150L, updatedFoyer.getCapaciteFoyer()); // Correction avec capaciteFoyer
        verify(foyerRepository, times(1)).save(existingFoyer);
    }

    @Test
    @Order(4)
    void testRemoveFoyer() {
        Long foyerId = 1L;

        doNothing().when(foyerRepository).deleteById(foyerId);

        foyerService.removeFoyer(foyerId);
        verify(foyerRepository, times(1)).deleteById(foyerId);
    }

    @Test
    @Order(5)
    void testAddFoyer() {
        Foyer newFoyer = new Foyer(3L, "Foyer C", 300L, null, null);

        when(foyerRepository.save(newFoyer)).thenReturn(newFoyer);

        Foyer addedFoyer = foyerService.addFoyer(newFoyer);

        assertNotNull(addedFoyer);
        assertEquals(newFoyer.getNomFoyer(), addedFoyer.getNomFoyer()); // Correction avec nomFoyer
        verify(foyerRepository, times(1)).save(newFoyer);
    }
}