package util;

/**
 * @author Haim Adrian
 * @since 27-Jun-20
 */
public class ComplexityCounter implements ComplexityCounterIfc {
    private long stepsCounter;

    public ComplexityCounter() {
        this(0);
    }

    public ComplexityCounter(long initialValue) {
        this.stepsCounter = initialValue;
    }

    @Override
    public void countStep() {
        stepsCounter++;
    }

    @Override
    public void countSteps(long steps) {
        stepsCounter += steps;
    }

    @Override
    public void add(ComplexityCounterIfc another) {
        stepsCounter += another.getStepsCount();
    }

    @Override
    public long getStepsCount() {
        return stepsCounter;
    }
}

