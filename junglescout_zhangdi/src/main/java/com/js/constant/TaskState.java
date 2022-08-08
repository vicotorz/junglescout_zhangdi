package com.js.constant;

public enum TaskState {

    //todo：这里可能还需要根据具体场景做进一步状态细化
    FINISHED("完成", 1),
    FAILED("失败", 2);


    private String stateDesc;
    private int stateCode;

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    TaskState(String stateDesc, int stateCode) {
        this.stateDesc = stateDesc;
        this.stateCode = stateCode;
    }
}
