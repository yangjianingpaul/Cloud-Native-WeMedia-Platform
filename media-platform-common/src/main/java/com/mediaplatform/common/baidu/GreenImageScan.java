package com.mediaplatform.common.baidu;

import com.baidu.aip.contentcensor.AipContentCensor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.HashMap;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "baidu")
public class GreenImageScan {
    // Set APPID/AK/SK
    private String APP_ID;
    private String API_KEY;
    private String SECRET_KEY;

    public Map<String, String> imageScan(byte[] imgByte) {
        // Initialize an AipContentCensor
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        Map<String, String> resultMap = new HashMap<>();
        JSONObject res = client.imageCensorUserDefined(imgByte, null);
        System.out.println(res.toString(2));
        // Response result
        Map<String, Object> map = res.toMap();
//        Get special fields
        String conclusion = (String) map.get("conclusion");

        if (conclusion.equals("compliant")) {
            resultMap.put("conclusion", conclusion);
            return resultMap;
        }
//        Get special collection fields
        JSONArray dataArrays = res.getJSONArray("data");
        String msg = "";
        for (Object result : dataArrays) {
            // Get reason
            msg = ((JSONObject) result).getString("msg");
        }

        resultMap.put("conclusion", conclusion);
        resultMap.put("msg", msg);
        return resultMap;

    }

}
