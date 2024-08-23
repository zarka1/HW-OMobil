package org.example.model;

public enum WebshopId {
    WS01,
    WS02;

    public static boolean checkIfExist(String name) {
        boolean result = false;
        for( WebshopId webshopId : values()) {
            if (webshopId.name().equalsIgnoreCase(name)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static WebshopId findByName(String name) {
        WebshopId result = null;
        for( WebshopId webshopId : values()) {
            if (webshopId.name().equalsIgnoreCase(name)) {
                result = webshopId;
                break;
            }
        }
        return result;
    }


}
