package com.example.homecastfileserver.configs;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

@Configuration
@ConfigurationProperties(prefix = "homecast")
@Getter
@Setter
public class HomeCastConfig {
    private static final Logger logger = LoggerFactory.getLogger(HomeCastConfig.class);
    private String ip;
    private String homecastdir;
    private String imagesdir;
    private String mp4dir;

    private static String getAddressIP() throws Exception{
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                String ipAddress = i.getHostAddress();
                if(isPrivateClassCIPAddress(ipAddress)) {
                    ipAddress = "http://" + ipAddress + ":8080";
                    logger.info("Adress IP ustawiony na: " + ipAddress);
                    return ipAddress;
                }
            }
        }
        return null;
    }

    public static boolean isPrivateClassCIPAddress(String ipAddress){
        String regex = "^192\\.168\\.[0-9]{1,3}\\.[0-9]{1,3}$";
        return Pattern.compile(regex).matcher(ipAddress).matches();
    }

    public void setIpAdress() throws Exception {
        this.ip = getAddressIP();
    }
}
