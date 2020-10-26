package ru.ariona.jsonbexamples.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ariona.jsonbexamples.AbstractIntegrationTest;
import ru.ariona.jsonbexamples.domain.Cat;
import ru.ariona.jsonbexamples.domain.CatProps;
import ru.ariona.jsonbexamples.domain.Slave;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private CatRepository catRepository;

    @BeforeEach
    public void setUp() {
        Slave katya = Slave.builder().name("Katya").age(31).build();
        Slave olya = Slave.builder().name("Olya").age(4).build();
        Cat fenechka = Cat.builder()
                .name("Fenechka")
                .props(CatProps.builder().color("red").tailLength(15L).build())
                .slaves(Arrays.asList(katya, olya))
                .toys(Arrays.asList("boll", "scratching Pad"))
                .build();
        Slave masha = Slave.builder().name("Masha").age(30).build();
        Cat manya = Cat.builder()
                .name("Manya")
                .props(CatProps.builder().color("white").tailLength(25L).build())
                .slaves(Arrays.asList(masha))
                .toys(Arrays.asList("boll", "mouse", "boll"))
                .build();
        Slave oksana = Slave.builder().name("Oksana").age(55).build();
        Slave sasha = Slave.builder().name("Sasha").age(60).build();
        Cat feckla = Cat.builder()
                .name("Manya")
                .props(CatProps.builder().color("gray").tailLength(30L).build())
                .slaves(Arrays.asList(oksana, sasha))
                .toys(Arrays.asList("teaser", "scratching post", "mouse"))
                .build();

        Cat homeless = Cat.builder()
                .name("Homeless")
                .build();

        catRepository.saveAll(List.of(fenechka, manya, feckla, homeless));
    }

    @AfterEach
    public void cleanDb() {
        catRepository.deleteAll();
    }

    @Test
    public void catsIsPresent() {
        List<Cat> all = catRepository.findAll();
        assertEquals(4, all.size());
    }

    @Test
    public void findAllByToyTest() {
        List<Cat> haveBolls = catRepository.findAllByToy("boll");
        assertEquals(2, haveBolls.size());
    }

    @Test
    public void findByAnyToyTest() {
        List<Cat> byAnyToy = catRepository.findByAnyToy(Arrays.asList("boll", "mouse"));
        for (Cat cat : byAnyToy) {
            assertTrue(cat.getToys().contains("boll") || cat.getToys().contains("mouse"));
        }
        assertEquals(3, byAnyToy.size());
    }

    @Test
    public void findByColorTest() {
        List<Cat> white = catRepository.findByColor("white");
        assertEquals("Masha", white.get(0).getSlaves().get(0).getName());
    }

    @Test
    public void findByCatsTailLongerThan() {
        List<Cat> byCatsTailLongerThan = catRepository.findByCatsTailLongerThan(20L);
        assertEquals(2, byCatsTailLongerThan.size());
    }

    @Test
    public void findBySlaveNameTest() {
        List<Cat> bySlave = catRepository.findBySlaveName("Katya");
        List<String> names = bySlave.get(0).getSlaves().stream()
                .map(Slave::getName)
                .collect(Collectors.toList());
        assertTrue(names.contains("Katya"));
    }
}