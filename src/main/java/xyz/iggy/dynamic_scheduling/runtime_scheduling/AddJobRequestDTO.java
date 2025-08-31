package xyz.iggy.dynamic_scheduling.runtime_scheduling;

import lombok.Data;

@Data
class AddJobRequestDTO {
    private Long id;
    private String cron;
    private String message;
}
