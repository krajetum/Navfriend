package com.nightfall.navfriend.manager;

/**
 * Created by Dev on 28/05/2015.
 */
public class RestServiceManager {
    private static RestServiceManager instance;
    private String address;
    private int port;
    private static String protocoll = "http://";

    private RestServiceManager(){
        address ="www.aminformatica.it";
        port = 8182;
    }



    public static RestServiceManager getInstance(){
        if(instance==null){
            instance = new RestServiceManager();
        }
        return instance;
    }

    public String getURI(String resource){

        return getURIAddress().concat("/"+resource);
    }

    public String getURIAddress(){
        return protocoll+address+":"+port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
