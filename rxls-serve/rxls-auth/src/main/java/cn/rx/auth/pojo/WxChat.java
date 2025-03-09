package cn.rx.auth.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxChat {
   /**
    * 用户唯一标识
    */
   private String openid;
   /**
    * 会话秘钥
    */
   private String session_key;

}
