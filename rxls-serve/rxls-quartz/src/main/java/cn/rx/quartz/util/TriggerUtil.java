package cn.rx.quartz.util;

import cn.rx.common.enums.CommonStateEnum;
import cn.rx.quartz.entity.SchedulerTask;
import cn.rx.quartz.enums.ErroneousEnum;
import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TriggerUtil {

    /**
     * 支持的时间单位
     */
    private final static Map<String, TimeUnit> TIME_MAP = new HashMap<>();

    /**
     * 支持的误点模式
     */
    private final static Map<String, ErroneousEnum> ERRONEOUS_MAP = new HashMap<>();


    static {
        TIME_MAP.put("hour", TimeUnit.HOURS);
        TIME_MAP.put("minutes", TimeUnit.MINUTES);
        TIME_MAP.put("seconds", TimeUnit.SECONDS);
        ERRONEOUS_MAP.put("fireNow", ErroneousEnum.FIRE_NOW);
        ERRONEOUS_MAP.put("ignoreMisfires", ErroneousEnum.IGNORE_MISFIRES);
        ERRONEOUS_MAP.put("withNextFireTime", ErroneousEnum.WITH_NEXT_FIRE_TIME);
    }


    /**
     * 获取触发器
     *
     * @param model      1 获取Simple触发器  0 获取Cron触发器
     * @param expression 表达式
     * @param unit       单位
     * @return 触发器
     */
    public static ScheduleBuilder get(Integer model, String expression, String unit) {
        if (CommonStateEnum.OK.code.equals(model)) {
            String[] split = expression.split("-");
            return getSimpleTrigger(Integer.valueOf(split[0]), Integer.valueOf(split[1]), TIME_MAP.get(unit));
        } else {
            return getCronTrigger(expression, ERRONEOUS_MAP.get(unit));
        }
    }


    /**
     * 获取Simple触发器
     *
     * @param frequency 重复次数
     * @param interval  间隔时间
     * @param unit      间隔时间单位
     * @return Simple触发器实例
     */
    private static SimpleScheduleBuilder getSimpleTrigger(Integer frequency, Integer interval, TimeUnit unit) {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        //重复次数
        if (frequency.equals(0)) {
            simpleScheduleBuilder.repeatForever();
        } else {
            simpleScheduleBuilder.withRepeatCount(frequency);
        }
        //设置间隔时间
        switch (unit) {
            case HOURS:
                simpleScheduleBuilder.withIntervalInHours(interval);
            case MINUTES:
                simpleScheduleBuilder.withIntervalInMinutes(interval);
            default:
                simpleScheduleBuilder.withIntervalInSeconds(interval);
        }

        return simpleScheduleBuilder;

    }

    /**
     * 获取Cron触发器
     *
     * @param cron          cron表达式
     * @param erroneousEnum 误点补偿模式
     * @return Cron触发器实例
     */
    private static CronScheduleBuilder getCronTrigger(String cron, ErroneousEnum erroneousEnum) {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        if (ErroneousEnum.IGNORE_MISFIRES.equals(erroneousEnum)) {
            cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
        } else if (ErroneousEnum.FIRE_NOW.equals(erroneousEnum)) {
            cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
        } else {
            cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
        }
        return cronScheduleBuilder;
    }
}
