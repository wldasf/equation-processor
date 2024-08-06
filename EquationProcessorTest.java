import org.example.EquationProcessor;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EquationProcessorTest {
    private EquationProcessor processor;

    @Before
    public void SetUp() {
        processor = new EquationProcessor();
    }

    @Test
    public void givenEmptyStacksAndHashmap_whenInputSimpleAddition_thenExceptedResultIsReturned() throws Exception {
        String equation = "3+2";
        double result = processor.evaluateEquation(equation);
        assertEquals(5.0, result, 0.001);
    }

    @Test
    public void givenEmptyStacksAndHashmap_whenInputSimpleSubtraction_thenExpectedResultIsReturned() throws Exception {
        String equation = "10-4";
        double result = processor.evaluateEquation(equation);
        assertEquals(6.0, result, 0.001);
    }

    @Test
    public void givenEmptyStacksAndHashmap_whenInputSimpleMultiplication_thenExpectedResultIsReturned() throws Exception {
        String equation = "4*5";
        double result = processor.evaluateEquation(equation);
        assertEquals(20.0, result, 0.001);
    }

    @Test
    public void givenEmptyStacksAndHashmap_whenInputSimpleDivision_thenExpectedResultIsReturned() throws Exception {
        String equation = "20/4";
        double result = processor.evaluateEquation(equation);
        assertEquals(5.0, result, 0.001);

    }

    @Test
    public void givenEmptyStacksAndHashmap_whenInputComplexExpression_thenExpectedResultIsReturned() throws Exception {
        String equation = "3+5*2-(8/4)";
        double result = processor.evaluateEquation(equation);
        assertEquals(11.0, result, 0.001);
    }

    @Test
    public void givenEmptyStacksAndHashmap_whenInputParenthesesExpression_thenExceptionResultIsReturned() throws Exception {
        String equation = "(3+2)*2";
        double result = processor.evaluateEquation(equation);
        assertEquals(10.0, result, 0.001);
    }

    @Test
    public void givenEmptyStacksAndHashmap_whenInputVariable_thenExpectedResultIsReturned() throws Exception {
        String equation = "x+2";
        processor.variables.put('x', 3.0);
        double result = processor.evaluateEquation(equation);
        assertEquals(5.0, result, 0.001);
    }

    @Test(expected = Exception.class)
    public void givenEmptyStacksAndHashmap_whenDivisionByZero_thenExpectedResultIsReturned() throws Exception {
        String equation = "1/0";
        processor.evaluateEquation(equation);
    }

    @Test(expected = Exception.class)
    public void givenEmptyStacksAndHashmap_whenInputMismatchedParentheses_thenExpectedResultIsReturned() throws Exception {
        String equation = "(3+2";
        processor.evaluateEquation(equation);
    }

//    @Test(expected = Exception.class)
//    public void givenEmptyStacksAndHashmap_whenInputInvalidCharacter_thenAnInputPromptOpens() throws Exception {
//        String equation = "3+2a";
//        processor.evaluateEquation(equation);
//    }
}
