package xyz.iggy.dynamic_scheduling.scheduled_report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledReportService {
    private final ScheduledReportRepository scheduledReportRepository;

    public ScheduledReport save(ScheduledReport scheduledReport){
        return scheduledReportRepository.save(scheduledReport);
    }

}
