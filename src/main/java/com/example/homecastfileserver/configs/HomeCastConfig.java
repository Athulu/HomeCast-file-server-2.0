package com.example.homecastfileserver.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.homecastfileserver.Application;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

@Configuration
@ConfigurationProperties(prefix = "homecast")
public class HomeCastConfig {
    private static final Logger logger = LoggerFactory.getLogger(HomeCastConfig.class);
    private String ip;
    private String homecastdir;
    private String imagesdir;
    private String mp4dir;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setIpAdress() throws Exception {
        this.ip = getAddressIP();
    }

    public String getHomecastdir() {
        return homecastdir;
    }
    public void setHomecastdir(String homecastdir) {
        this.homecastdir = homecastdir;
    }

    public String getImagesdir() {
        return imagesdir;
    }

    public void setImagesdir(String imagesdir) {
        this.imagesdir = imagesdir;
    }

    public String getMp4dir() {
        return mp4dir;
    }

    public void setMp4dir(String mp4dir) {
        this.mp4dir = mp4dir;
    }

    public static String getAddressIP() throws Exception{
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
}
