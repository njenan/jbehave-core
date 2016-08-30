package org.jbehave.core.steps;

import java.util.HashMap;
import java.util.Map;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;

public class RecyclingScanningStepsFactory extends ScanningStepsFactory {
    private Map<Class<?>, Object> steps = new HashMap<Class<?>, Object>();

    public RecyclingScanningStepsFactory(Configuration configuration, String... packages) {
        super(configuration, packages);

    }

    public RecyclingScanningStepsFactory(MostUsefulConfiguration configuration, Class<?> clazz) {
        super(configuration, clazz);
    }
    
    @Override
    public Object createInstanceOfType(Class<?> type) {
        Object stepsClass = steps.get(type);
        if (stepsClass != null) {
            return stepsClass;
        } else {
            stepsClass = super.createInstanceOfType(type);
            steps.put(type, stepsClass);

            return stepsClass;
        }
    }

}
