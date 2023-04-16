package parsing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static parsing.Moto.*;

public class ParsingJsonTest {

    private ClassLoader cl = FileParsingTest.class.getClassLoader();

    @Test
    @DisplayName("Разбор json файла")
    void jsonTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("Moto.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            Moto moto = objectMapper.readValue(isr, Moto.class);

            Assertions.assertEquals("Honda", moto.fabricator);
            Assertions.assertEquals("Sport", moto.products);
            Assertions.assertEquals(List.of("CB1000R BLACK EDITION", "CBR650R"), moto.model);
        }
    }
}
