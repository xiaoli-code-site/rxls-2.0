package cn.rx.system.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MpConfig {
   @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        //配置拦截器  给分页查询使用
        //定义拦截器
        MybatisPlusInterceptor mp = new MybatisPlusInterceptor();
        //使用方法添加具体的拦截器
        mp.addInnerInterceptor(new PaginationInnerInterceptor());
        //添加乐观锁  解决并发问题
        mp.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mp;
    }


}
