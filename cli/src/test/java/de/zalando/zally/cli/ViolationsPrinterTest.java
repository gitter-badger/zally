package de.zalando.zally.cli;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class ViolationsPrinterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream outStream = new PrintStream(outContent);

    @Test
    public void testNoViolationsCase() throws IOException {
        ViolationsPrinter violationPrinter = new ViolationsPrinter(outStream);
        List<JsonObject> violations = new ArrayList<>();
        violationPrinter.print(violations, "must");

        assertEquals("", outContent.toString());
    }

    @Test
    public void testWithViolationsCase() throws IOException {
        ViolationsPrinter violationPrinter = new ViolationsPrinter(outStream);
        List<JsonObject> violations = new ArrayList<>();

        JsonObject violationOne = new JsonObject();
        violationOne.add("title", "Violation 1");
        violationOne.add("description", "Violation 1 Description");

        JsonObject violationTwo = new JsonObject();
        violationTwo.add("title", "Violation 2");
        violationTwo.add("description", "Violation 2 Description");

        violations.add(violationOne);
        violations.add(violationTwo);
        violationPrinter.print(violations, "must");

        String expectedResult = "Found the following MUST violations\n" +
                "===================================\n" +
                "Violation 1:\n\tViolation 1 Description\n" +
                "Violation 2:\n\tViolation 2 Description\n";

        assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void formatReturnsProperlyFormattedString() {
        JsonObject violation = new JsonObject();
        violation.add("title", "Test title");
        violation.add("description", "Test description");

        String result = ViolationsPrinter.formatViolation(violation);

        assertEquals("Test title:\n\tTest description", result);
    }
}
