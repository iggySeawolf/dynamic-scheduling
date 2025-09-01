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
    //email send time
    private String cronExpression;
    private boolean scheduled;
    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    enum Frequency{
        LAST_24_HOURS,
        YESTERDAY,
        LAST_CALENDAR_WEEK,
        LAST_CALENDAR_MONTH
    }

    @Override
    public String toString(){
        return "id=" + this.reportId + ", emailSubject=" + this.emailSubject;
    }
}


