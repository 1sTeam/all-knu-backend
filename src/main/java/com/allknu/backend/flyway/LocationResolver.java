package com.allknu.backend.flyway;

import java.sql.DatabaseMetaData;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Service;

@Service
class LocationResolver {

    private static final String VENDOR_PLACEHOLDER = "{vendor}";
    private final DataSource dataSource;

    LocationResolver(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    String resolveLocation(String location) {
        if (this.usesVendorLocation(location)) {
            DatabaseDriver databaseDriver = this.getDatabaseDriver();
            return this.replaceVendorLocation(location, databaseDriver);
        } else {
            return location;
        }
    }

    private String replaceVendorLocation(String location, DatabaseDriver databaseDriver) {
        if (databaseDriver == DatabaseDriver.UNKNOWN) {
            return location;
        } else {
            String vendor = databaseDriver.getId();
            return location.replace(VENDOR_PLACEHOLDER, vendor);
        }
    }

    private DatabaseDriver getDatabaseDriver() {
        try {
            String url = (String) JdbcUtils.extractDatabaseMetaData(this.dataSource, DatabaseMetaData::getURL);
            return DatabaseDriver.fromJdbcUrl(url);
        } catch (MetaDataAccessException var2) {
            throw new IllegalStateException(var2);
        }
    }

    private boolean usesVendorLocation(String location) {
        return location.contains(VENDOR_PLACEHOLDER);
    }
}
