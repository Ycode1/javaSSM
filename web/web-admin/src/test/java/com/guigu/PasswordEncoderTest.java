package com.guigu;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    /**
     * 测试这个加密器 每次的运行结果不一样
     */
    @Test
    public void testBCryptPasswordEncoder(){
        //创建加密对象
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //对123456进行加密
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println("encode = " + encode);
        /*
        第一次加密结果：$2a$10$uG7nFdZMcMGMUwhpYxecJu7VQQJzNYXow6zmfktReXTAkqBbW.27W
        第二次加密结果：$2a$10$fgSXQoGn6ooXfR.tRlUeX.JlVMjrgouKEf5AEcI6jp3dAeeEVoUn.
        第三次加密结果：$2a$10$GfVIVsl7HkKHCZ4oupmMGuTLsN8cXzZg/LDzLwFCjFgGo3DTpDdvq
         */
        //单向的哈希 无法解密 所以直接密码匹配
        boolean matches = bCryptPasswordEncoder.matches("123456", "$2a$10$GfVIVsl7HkKHCZ4oupmMGuTLsN8cXzZg/LDzLwFCjFgGo3DTpDdvq");
        System.out.println("matches = " + matches);

    }
}
