package xyz.iggy.dynamic_scheduling.runtime_scheduling;

class AddJobRequestDTO {
    private Long id;
    private String cron;
    private String message;

    public AddJobRequestDTO(Long id, String cron, String message) {
        this.id = id;
        this.cron = cron;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
