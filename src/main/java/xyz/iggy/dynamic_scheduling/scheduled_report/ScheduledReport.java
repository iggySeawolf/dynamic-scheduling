package xyz.iggy.dynamic_scheduling.scheduled_report;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "SCHEDULED_REPORT")
@Data
public class ScheduledReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reportId;
    @Convert(converter = StringListConverter.class)
    private List<String> listOfEmailRecipients;
    private String emailSubject;
    private String cronExpression;
    private boolean scheduled;
}


