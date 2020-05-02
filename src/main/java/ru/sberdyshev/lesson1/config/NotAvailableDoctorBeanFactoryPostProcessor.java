package ru.sberdyshev.lesson1.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import ru.sberdyshev.lesson1.hospital.doctor.NotAvailableDoctor;

@Component
public class NotAvailableDoctorBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            String className = beanDefinition.getBeanClassName();
            try {
                Class<?> beanClass = Class.forName(className);
                NotAvailableDoctor annotation = (NotAvailableDoctor) beanClass.getAnnotation(NotAvailableDoctor.class);
                if (annotation != null) {
                    Class newDoctorName = annotation.goToDoctor();
                    beanDefinition.setBeanClassName(newDoctorName.getName());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
