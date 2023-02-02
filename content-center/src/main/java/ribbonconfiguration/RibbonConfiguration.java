package ribbonconfiguration;

import com.example.configuration.NacosWeightedRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author word
 */

@Configuration
public class RibbonConfiguration {
    @Bean
    public IRule ribbonRule() {
        return new NacosWeightedRule();
    }
}
