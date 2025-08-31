package xyz.iggy.dynamic_scheduling.scheduled_report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduledReportRepository extends JpaRepository<ScheduledReport, Long> {
    List<ScheduledReport> findByScheduledTrue();
}
