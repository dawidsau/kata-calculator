package pl.sauermann.kata.calculator.service;

import org.junit.jupiter.api.Test;
import pl.sauermann.kata.calculator.exceptions.NegativeProhibitedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    @Test
    void shouldReturnZeroWhenNoArg() {
        int result = Calculator.adding("");

        assertEquals(0, result);
    }

    @Test
    void shouldReturnFirstValueWhenOnlyOneArg() {
        int result = Calculator.adding(" 1 ");

        assertEquals(1, result);
    }

    @Test
    void shouldAddTwoArguments() {
        int result = Calculator.adding("1,4");

        assertEquals(5, result);
    }

    @Test
    void shouldAddAllAmountOfArguments() {
        int result = Calculator.adding("1,4,6,4");

        assertEquals(15, result);
    }

    @Test
    void shouldAddAmountOfArgumentsSplittedByNewLine() {
        int result = Calculator.adding("1,\n\n4,6,4");

        assertEquals(15, result);
    }

    @Test
    void shouldSumWithCustomSplitterator() {
        int result = Calculator.adding("//;\n1;\n\n46,4");

        assertEquals(51, result);
    }

    @Test
    void shouldNotSumValuesBiggerThanOneThousand() {
        int result = Calculator.adding("//;\n1001;\n\n4,6,4");

        assertEquals(14, result);
    }

    @Test
    void shouldNotSumValuesLowerThanZero() {

        assertThrows(NegativeProhibitedException.class,
                () -> Calculator.adding("-1,5,7,-5"));
    }

    @Test
    void shouldUseMoreThanOneCustomSplitter() {
        int result = Calculator.adding("//[*][;]\n1*\n\n4*4;5");

        assertEquals(14, result);
    }

    @Test
    void shouldUseMoreThanOneCustomSplitterWithMoreThanOneSign() {
        int result = Calculator.adding("//[***][;%]\n1*\n\n4***4;%   5");

        assertEquals(14, result);
    }
}