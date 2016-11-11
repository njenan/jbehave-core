package org.jbehave.core.steps;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.junit.Test;

public class RecyclingScanningStepsFactoryBehaviour {

    @Test
    public void shouldCreateCandidateSteps() {
        RecyclingScanningStepsFactory factory = new RecyclingScanningStepsFactory(new MostUsefulConfiguration(), this.getClass());
        List<CandidateSteps> createCandidateSteps = factory.createCandidateSteps();
        
        assertThat(createCandidateSteps.size(), Matchers.greaterThan(2));
    }
    
    @Test
    public void shouldReuseCandidateSteps() {
        RecyclingScanningStepsFactory factory = new RecyclingScanningStepsFactory(new MostUsefulConfiguration(), this.getClass());
        MySteps firstInstance = (MySteps) factory.createInstanceOfType(MySteps.class);
        firstInstance.increaseCounter();
        
        MySteps secondInstance = (MySteps) factory.createInstanceOfType(MySteps.class);
        secondInstance.shouldEqual();
    }
    
    static class MySteps {
        private int i = 0;
        
        @When("I increase the counter")
        public void increaseCounter() {
            i++;
        }
        
        @Then("It should equal 1")
        public void shouldEqual() {
            assertThat(i, Matchers.equalTo(1));
        }
    }
}
