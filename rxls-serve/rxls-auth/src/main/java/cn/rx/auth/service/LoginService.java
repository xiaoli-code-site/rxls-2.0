package cn.rx.auth.service;

/**
 * 登录业务接口
 */
public interface LoginService<T,R> {

   /**
    * 登录接口
    * @param dto 参数对象
    * @return 登录信息
    */
   R handler(T dto);

    void logout();
}
