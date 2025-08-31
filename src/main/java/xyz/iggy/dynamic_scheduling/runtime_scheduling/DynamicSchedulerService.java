package xyz.iggy.dynamic_scheduling.runtime_scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import xyz.iggy.dynamic_scheduling.scheduled_report.ScheduledReportRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class DynamicSchedulerService {

    private final TaskScheduler taskScheduler;
    private final Map<Long, ScheduledFuture<?>> runningJobs = new ConcurrentHashMap<>();
    private final ScheduledReportRepository scheduledReportRepository;

    public Map<?,?> getJobs(){
        return runningJobs;
    }

    /**
     * Add a new cron job
     */
    public void addJob(Long jobId, Runnable task, String cronExpression) {
        if (runningJobs.containsKey(jobId)) {
            throw new IllegalArgumentException("Job with ID " + jobId + " already exists");
        }
        ScheduledFuture<?> future = taskScheduler.schedule(task, new CronTrigger(cronExpression));
        runningJobs.put(jobId, future);
    }

    /**
     * Remove/cancel a job
     */
    public void removeJob(Long jobId) {
        ScheduledFuture<?> future = runningJobs.remove(jobId);
        if (future != null) {
            future.cancel(false);
        }
    }

    /**
     * Update a job (cancel + reschedule)
     */
    public void updateJob(Long jobId, Runnable task, String cronExpression) {
        removeJob(jobId);
        addJob(jobId, task, cronExpression);
    }

    /**
     * Check if job exists
     */
    public boolean hasJob(Long jobId) {
        return runningJobs.containsKey(jobId);
    }

    private void stopAllScheduledFutures(){
        runningJobs.values().forEach(future -> future.cancel(false));
        runningJobs.clear();
    }

    @Scheduled(cron = "*/10 * * * * *")
    private void syncJobsWithDatabase(){
        log.info("Hitting DB for new jobs.");
        stopAllScheduledFutures();
        scheduledReportRepository.findByScheduledTrue()
                .forEach(scheduledReport ->
                        updateJob(
                                scheduledReport.getReportId(),
                                () -> log.info("Emailing sr.id=[{}] with subject=[{}] to recipients=[{}]",
                                        scheduledReport.getReportId(),
                                        scheduledReport.getEmailSubject(),
                                        scheduledReport.getListOfEmailRecipients()),
                                scheduledReport.getCronExpression()
                        )
                );
    }
}

