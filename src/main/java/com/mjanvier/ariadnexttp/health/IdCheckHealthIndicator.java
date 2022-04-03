package com.mjanvier.ariadnexttp.health;

import com.mjanvier.ariadnexttp.idcheck.HealthResponseDTO;
import com.mjanvier.ariadnexttp.idcheck.api.AdministrationApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IdCheckHealthIndicator implements HealthIndicator {
    private final AdministrationApi administrationApi;

    @Override
    public Health health() {
        try {
            final HealthResponseDTO health = administrationApi.getHealth();
            final HealthResponseDTO.StatusEnum status = health.getStatus();
            if (HealthResponseDTO.StatusEnum.OK.equals(status)) {
                return Health.up()
                        .withDetail("status", status).build();
            } else {
                return Health.down()
                        .withDetail("status", status).build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
