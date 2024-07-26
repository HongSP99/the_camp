package io.camp.payment.util;

import org.json.JSONObject;

public class PrintPayment {

    public void camelCase(String json, String keyName) {
        JSONObject obj = new JSONObject(json);

        if (obj.isEmpty()) {
            return;
        } else {
            for (String key : obj.keySet()) {
                if (obj.optJSONObject(key) == null) {
                    if (keyName.equals("")) {
                        System.out.println(key + " : " + obj.opt(key));
                    } else {
                        System.out.println(keyName + key.substring(0, 1).toUpperCase() + key.substring(1) + " : " + obj.opt(key));
                    }

                } else {
                    if (keyName.equals("")) {
                        System.out.println(key + " : " + obj.optJSONObject(key));
                        camelCase(obj.getJSONObject(key).toString(), key);
                    } else {
                        if (obj.optJSONObject(key) != null) {
                            System.out.println(keyName + key.substring(0, 1).toUpperCase() + key.substring(1) + " : " + obj.optJSONObject(key));
                        } else {
                            System.out.println(keyName + " : " + obj.optJSONObject(key));
                        }
                        camelCase(obj.getJSONObject(key).toString(), keyName + key.substring(0, 1).toUpperCase() + key.substring(1));
                    }
                }
            }
        }
    }

    public void snakeCase(String json, String keyName) {
        JSONObject obj = new JSONObject(json);

        if (obj.isEmpty()) {
            return;
        } else {
            for (String key : obj.keySet()) {
                if (obj.optJSONObject(key) == null) {
                    if (keyName.equals("")) {
                        System.out.println(key + " : " + obj.opt(key));
                    } else {
                        System.out.println(keyName + "_" + key + " : " + obj.opt(key));
                    }

                } else {
                    if (keyName.equals("")) {
                        System.out.println(key + " : " + obj.optJSONObject(key));
                        snakeCase(obj.getJSONObject(key).toString(), key);
                    } else {
                        if (obj.optJSONObject(key) != null) {
                            System.out.println(keyName + "_" + key + " : " + obj.optJSONObject(key));
                        } else {
                            System.out.println(keyName + " : " + obj.optJSONObject(key));
                        }
                        snakeCase(obj.getJSONObject(key).toString(), keyName + "_" + key);
                    }
                }
            }
        }
    }
}
