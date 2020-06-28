package util;

/**
 * @author Haim Adrian
 * @since 27-Jun-20
 */
public interface ComplexityCounterIfc {
    void countStep();
    void countSteps(long steps);
    void add(ComplexityCounterIfc another);
    long getStepsCount();
}

