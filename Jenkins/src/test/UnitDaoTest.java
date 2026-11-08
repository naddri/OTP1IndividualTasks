import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnitDaoTest {
    private Connection connection;
    private UnitDao unitDao;
    private ConversionDao conversionDao;

    @BeforeEach
    void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:" + UUID.randomUUID());
        Database.initialize(connection);
        unitDao = new UnitDao(connection);
        conversionDao = new ConversionDao(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        connection.close();
    }

    @Test
    void returnsUnitNameInSelectedLanguage() throws Exception {
        List<Unit> units = unitDao.findByCategory("temperature", "ja");

        assertEquals("摂氏", units.get(0).name());
        assertEquals("摂氏で表した温度", units.get(0).description());
    }

    @Test
    void fallsBackToEnglishWhenTranslationIsMissing() throws Exception {
        List<Unit> units = unitDao.findByCategory("temperature", "xx");

        assertEquals("Celsius", units.get(0).name());
        assertEquals("Temperature in degrees Celsius", units.get(0).description());
    }

    @Test
    void savesConversionsAndReturnsOnlyTheLatestFive() throws Exception {
        List<Unit> units = unitDao.findByCategory("temperature", "en");
        Unit celsius = units.get(0);
        Unit fahrenheit = units.get(1);
        for (int value = 0; value < 6; value++) {
            conversionDao.save(value, celsius, fahrenheit, value * 9 / 5.0 + 32, "en");
        }

        List<String> history = conversionDao.findLatestFive("ja");

        assertEquals(5, history.size());
        assertTrue(history.get(0).contains("摂氏"));
        assertTrue(history.get(0).contains("華氏"));
        assertTrue(history.get(0).startsWith("5"), history.toString());
    }
}
