package department.di.factory;


import department.di.annotation.Inject;
import department.di.annotation.Injectable;
import department.di.config.Configuration;
import department.di.config.JavaConfiguration;
import department.di.configurator.BeanConfigurator;
import department.di.configurator.JavaBeanConfigurator;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BeanFactory {

    private static final BeanFactory BEAN_FACTORY = new BeanFactory();
    private final Configuration configuration;
    private final BeanConfigurator beanConfigurator;

    private BeanFactory(){
        this.configuration = new JavaConfiguration();
        this.beanConfigurator = new JavaBeanConfigurator(configuration.getPackageToScan(), configuration.getInterfaceToImplementations());
    }

    public static BeanFactory getInstance(){
        return BEAN_FACTORY;
    }

    @SneakyThrows
    public <T> T getBean(Class<T> clazz){
        Class<? extends T> implementationClass = clazz;

        if (implementationClass.isInterface()) {
            if (implementationClass.isAnnotationPresent(Injectable.class)) {
                implementationClass = beanConfigurator.getImplementationClass(implementationClass);
            }
        }
        T bean = implementationClass.getDeclaredConstructor().newInstance();

        for (Field field : Arrays.stream(implementationClass.getDeclaredFields()).filter(field -> field.isAnnotationPresent(Inject.class)).collect(Collectors.toList())) {
            field.setAccessible(true);
            field.set(bean, BEAN_FACTORY.getBean(field.getType()));
        }
        return bean;
    }
}
