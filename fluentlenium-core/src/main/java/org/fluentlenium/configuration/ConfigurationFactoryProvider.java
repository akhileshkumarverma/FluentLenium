package org.fluentlenium.configuration;

import org.fluentlenium.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;

public abstract class ConfigurationFactoryProvider {
    private static ConfigurationFactory bootstrapFactory = new DefaultConfigurationFactory();

    public static ConfigurationFactory getConfigurationFactory(Class<?> container) {
        ConfigurationProperties configuration = bootstrapFactory.newConfiguration(container, new ConfigurationDefaults());
        Class<? extends ConfigurationFactory> configurationFactoryClass = configuration.getConfigurationFactory();
        if (configurationFactoryClass != null) {
            try {
                return ReflectionUtils.newInstance(configurationFactoryClass);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new ConfigurationException("Can't initialize ConfigurationFactory " + configurationFactoryClass.getName(), e);
            }
        }
        return bootstrapFactory;
    }

    public static Configuration newConfiguration(Class<?> container) {
        ConfigurationFactory configurationFactory = getConfigurationFactory(container);
        Configuration configuration = configurationFactory.newConfiguration(container, new ConfigurationDefaults());

        if (configuration.getConfigurationDefaults() != null && configuration.getConfigurationDefaults() != ConfigurationDefaults.class) {
            ConfigurationProperties configurationDefaults;
            try {
                configurationDefaults = configuration.getConfigurationDefaults().getConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                throw new ConfigurationException(configuration.getConfigurationDefaults() + " should have a public default constructor.", e);
            } catch (Exception e) {
                throw new ConfigurationException(configuration.getConfigurationDefaults() + " can't be instantiated.", e);
            }

            configuration = configurationFactory.newConfiguration(container, configurationDefaults);
        }
        return configuration;

    }
}
