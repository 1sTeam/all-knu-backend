package com.allknu.backend.flyway;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlywayConfiguration {

    private final DataSource dataSource;
    private final LocationResolver locationResolver;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        String location = "classpath:db/migration/{vendor}";
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(locationResolver.resolveLocation(location))
                .baselineOnMigrate(true)
                .load();
    }

}
