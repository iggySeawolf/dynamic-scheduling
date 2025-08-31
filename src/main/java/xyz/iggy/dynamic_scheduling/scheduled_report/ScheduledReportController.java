package xyz.iggy.dynamic_scheduling.scheduled_report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduled-report")
@RequiredArgsConstructor
public class ScheduledReportController {
    private final ScheduledReportService scheduledReportService;
    private final ScheduledReportRepository scheduledReportRepository;

    @PostMapping
    public ScheduledReport saveReport(@RequestBody ScheduledReport scheduledReport){
        return scheduledReportService.save(scheduledReport);
    }

    @GetMapping
    public ResponseEntity<?> findAllScheduledReport(){
        return ResponseEntity.ok(scheduledReportRepository.findAll());
    }
}
