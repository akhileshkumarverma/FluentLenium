package org.fluentlenium.cucumber.adapter;

import cucumber.api.Scenario;
import org.fluentlenium.adapter.FluentTestRunnerAdapter;

public class FluentCucumberTest extends FluentTestRunnerAdapter {
    public FluentCucumberTest() {
        super(new FluentCucumberSharedMutator());
    }

    // It's not allowed by Cucumber JVM to add @Before in the base class.
    public void before(Scenario scenario) {
        starting(scenario.getId());
    }

    // It's not allowed by Cucumber JVM to add @After in the base class.
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            failed(scenario.getId());
        }

        finished(scenario.getId());
    }
}