package util;

/**
 * @author Haim Adrian
 * @since 27-Jun-20
 */
public class ActionResult<T> implements ComplexityCounterIfc {
    private T result;
    private final ComplexityCounter complexityCounter = new ComplexityCounter();

    public ActionResult() {

    }

    public ActionResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public void countStep() {
        complexityCounter.countStep();
    }

    @Override
    public void countSteps(long steps) {
        complexityCounter.countSteps(steps);
    }

    @Override
    public void add(ComplexityCounterIfc another) {
        complexityCounter.add(another);
    }

    @Override
    public long getStepsCount() {
        return complexityCounter.getStepsCount();
    }

    @Override
    public String toString() {
        return String.valueOf(result);
    }
}

