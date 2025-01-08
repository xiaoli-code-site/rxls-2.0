package cn.rx.quartz.enums;

public enum ErroneousEnum {
    /**
     * 不触发立即执行，等待下次Cron触发频率到达时刻开始按照Cron频率依次执行。
     */
    WITH_NEXT_FIRE_TIME,
    /**
     * 以当前时间为触发频率立刻触发一次执行，然后按照Cron频率依次执行
     */
    FIRE_NOW,
    /**
     * 以错过的第一个频率时间立刻开始执行，重做错过的所有频率周期后，
     * 当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
     */
    IGNORE_MISFIRES;
}
