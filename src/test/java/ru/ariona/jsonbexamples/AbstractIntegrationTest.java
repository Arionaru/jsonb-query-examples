package ru.ariona.jsonbexamples;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.stream.Stream;

import static java.lang.String.format;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class AbstractIntegrationTest {

    public static GenericContainer<?> postgres = new PostgreSQLContainer<>("postgres:11.3")
            .withInitScript("db/clean_db.sql")
            .withReuse(TestcontainersConfiguration.getInstance().environmentSupportsReuse());

    static {
        Startables.deepStart(Stream.of(postgres)).join();
        System.setProperty("spring.datasource.url", format("jdbc:postgresql://%s:%d/test", postgres.getContainerIpAddress(), postgres.getMappedPort(5432)));
    }
}
