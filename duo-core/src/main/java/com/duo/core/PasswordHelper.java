package com.duo.core;

import com.duo.modules.system.entity.SystemUser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${password.algorithmName}")
    private String algorithmName = "md5";
    @Value("${password.hashIterations}")
    private int hashIterations = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public void entityEncryptPassword(SystemUser user, String  password) {

        String salt=randomNumberGenerator.nextBytes().toHex();
        user.setUser_salt(salt);

        String newPassword = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(user.getUser_code()+salt),
                hashIterations).toHex();
        user.setUser_password(newPassword);
    }

    public String getEncryptPassword(String usercode,String salt,String  password) {
        String newPassword = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(usercode+salt),
                hashIterations).toHex();

        return newPassword;
    }
}
