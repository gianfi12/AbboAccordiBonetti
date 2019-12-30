package com.SafeStreets;

/**
 * This class contains the information provided by a Municipality in order to retrieve data from their repository
 */
public class DataIntegrationInfo {
    /**
     * Is the ip of the machine from which we can get the data
     */
    private String ip;
    /**
     * Is the port on which the service is exposed
     */
    private String port;
    /**
     * Is the passphrase used to authenticate our system
     */
    private String password;

    /**
     * This is the constructor of a instance of Data Integration
     * @param ip Is the ip of the machine
     * @param port Is the port of the service
     * @param password Is the passphrase for the authentication
     */
    public DataIntegrationInfo(String ip, String port, String password) {
        this.ip = ip;
        this.port = port;
        this.password = password;
    }

    /**
     * This method returns the ip
     * @return Is the ip of the machine
     */
    public String getIp() {
        return ip;
    }

    /**
     * Is the getter of the port
     * @return Is the port of the service
     */
    public String getPort() {
        return port;
    }

    /**
     * This method is the getter of the passphrase
     * @return Is the passphrase
     */
    public String getPassword() {
        return password;
    }
}
