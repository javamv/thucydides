package net.thucydides.core.model;

import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.List;

import static net.thucydides.core.model.TestResult.FAILURE;
import static net.thucydides.core.model.TestResult.IGNORED;
import static net.thucydides.core.model.TestResult.PENDING;
import static net.thucydides.core.model.TestResult.SKIPPED;
import static net.thucydides.core.model.TestResult.SUCCESS;

/**
 * A list of test results, used to determine the overall test result.
 */
public class TestResultList {

    private final List<TestResult> testResults;

    protected TestResultList(final List<TestResult> testResults) {
        this.testResults = testResults;
    }

    public static TestResultList of(final List<TestResult> testResults) {
        return new TestResultList(testResults);
    }

    public boolean isEmpty() {
        return testResults.isEmpty();
    }

    public TestResult getOverallResult() {
        if (testResults.isEmpty()) {
            return PENDING;
        }

        if (testResults.contains(FAILURE)) {
            return FAILURE;
        }

        if (testResults.contains(PENDING)) {
            return PENDING;
        }

        if (containsOnly(IGNORED)) {
            return IGNORED;
        }

        if (containsOnly(SKIPPED)) {
            return SKIPPED;
        }

        if (containsOnly(SUCCESS, IGNORED, SKIPPED)) {
            return SUCCESS;
        }
        return SUCCESS;
    }

    private boolean containsOnly(final TestResult... values) {
        Preconditions.checkState(!isEmpty());

        List<TestResult> authorizedTypes = Arrays.asList(values);
        for (TestResult result : testResults) {
            if (!authorizedTypes.contains(result)) {
                return false;
            }
        }

        return true;
    }

}
