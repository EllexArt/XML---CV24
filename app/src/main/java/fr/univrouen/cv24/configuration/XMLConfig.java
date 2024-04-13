package fr.univrouen.cv24.configuration;

import fr.univrouen.cv24.entities.Cv24Type;
import fr.univrouen.cv24.entities.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class XMLConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(ObjectFactory.class); // Include more classes if needed
        return marshaller;
    }
}
