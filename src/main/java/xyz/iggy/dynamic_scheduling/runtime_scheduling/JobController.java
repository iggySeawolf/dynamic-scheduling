package xyz.iggy.dynamic_scheduling.runtime_scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private static final Logger LOG = LoggerFactory.getLogger(JobController.class);
    private final DynamicSchedulerService dynamicSchedulerService;

    public JobController(DynamicSchedulerService dynamicSchedulerService) {
        this.dynamicSchedulerService = dynamicSchedulerService;
    }

//    @PostMapping("/add")
//    public String addJob(@RequestParam String id, @RequestParam String cron) {
//        dynamicSchedulerService.addJob(id, () -> {
//            System.out.println("Job " + id + " executed at " + System.currentTimeMillis());
//        }, cron);
//        return "Job " + id + " scheduled with cron " + cron;
//    }
    @GetMapping
    public Map<Long, ScheduledFuture<?>> getJobs(){
        return dynamicSchedulerService.getJobs();
    }

    @PostMapping("/add")
    public String addJob(
            @RequestBody AddJobRequestDTO addJobRequestDTO) {
        dynamicSchedulerService.addJob(
                addJobRequestDTO.getId(),
                () -> LOG.info("id: {} says :: {}", addJobRequestDTO.getId(), addJobRequestDTO.getMessage()),
                addJobRequestDTO.getCron());
        return "Job " + addJobRequestDTO.getId() + " scheduled with cron " + addJobRequestDTO.getCron();
    }

    @DeleteMapping("/remove")
    public String removeJob(@RequestParam Long id) {
        LOG.info("Deleting job with id={}", id);
        dynamicSchedulerService.removeJob(id);
        return "Job " + id + " removed";
    }

    @PutMapping("/update")
    public String updateJob(@RequestParam Long id, @RequestParam String cron) {
        dynamicSchedulerService.updateJob(id, () -> {
            System.out.println("Job " + id + " executed at " + System.currentTimeMillis());
        }, cron);
        return "Job " + id + " updated with cron " + cron;
    }
}

