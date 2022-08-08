package com.js.model;

public abstract class FetchInfoTaskEntity {

    private String id;
    private String taskId;
    private long startStamp;
    private long endStamp;
    private String url;
    private int taskState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getStartStamp() {
        return startStamp;
    }

    public void setStartStamp(long startStamp) {
        this.startStamp = startStamp;
    }

    public long getEndStamp() {
        return endStamp;
    }

    public void setEndStamp(long endStamp) {
        this.endStamp = endStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }
}
