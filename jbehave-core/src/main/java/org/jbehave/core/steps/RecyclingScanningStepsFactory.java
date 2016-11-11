package org.jbehave.core.steps;

import java.util.HashMap;
import java.util.Map;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;

/**
 * An {@link InjectableStepsFactory} that extends {@link ScanningStepsFactory}
 * to provide the ability to reuse existing instances of steps classes instead
 * of re-instantiating the classes.
 */
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
