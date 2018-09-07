package com.cw.biz.user.domain.service;

import com.cw.biz.user.domain.entity.SeUser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;


@Service
public class PasswordHelper {


    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    public static final String ALGORITHM_NAME = "SHA-256";
    public static final int HASH_ITERATIONS = 2;

    public void encryptPassword(SeUser user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                ALGORITHM_NAME,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();

        user.setPassword(newPassword);
    }
    public  String encryptOldPassword(SeUser user,String oldPassword){
       return  new SimpleHash(
                ALGORITHM_NAME,
                oldPassword,
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();
    }

}
