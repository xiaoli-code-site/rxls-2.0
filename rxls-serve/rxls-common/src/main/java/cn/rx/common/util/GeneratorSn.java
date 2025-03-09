package cn.rx.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 随机编号生成器
 */
public class GeneratorSn {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMddHHmmssSSS");
    private static final Random RANDOM = new Random();
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    /**
     * 生成订单号
     * @param uid 用户id
     * @return 订单号
     */
    public static String generateSn(String uid) {
        StringBuffer stringBuffer = new StringBuffer();

        // 年月日时分秒微秒
        String timestamp = DATE_FORMAT.format(new Date());
        stringBuffer.append(timestamp);

        // 两位随机数
        int randomTwoDigits = RANDOM.nextInt(99 - 10 + 1) + 10;
        stringBuffer.append(String.format("%02d", randomTwoDigits));

        // 流水号（简单实现使用原子整数递增）
        int sequenceNumber = COUNTER.incrementAndGet();
        if (sequenceNumber > 999999) { // 如果超过6位数，则重置为0
            COUNTER.set(0);
            sequenceNumber = COUNTER.incrementAndGet();
        }
        stringBuffer.append(String.format("%06d", sequenceNumber));

        // 三位随机数
        int randomThreeDigits = RANDOM.nextInt(999 - 100 + 1) + 100;
        stringBuffer.append(randomThreeDigits).append(uid);

        return stringBuffer.toString();
    }


}