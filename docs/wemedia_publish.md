# We-media Platform Login
## The login process is the same as the App login process, and the login interface is as follows:

![](/resources/wemedia.png)

# We-media Material Management
* ①：The front-end sends a request for uploading images. The type is MultipartFile
* ②：After the token is resolved, the gateway stores the parsed user information in the header
* ③：The we-media microservice uses an interceptor to get the user information in the header and put it into threadlocal
* ④：First upload the picture to minIO and get the path of the picture request
* ⑤：Saves the user id and the path on the image to the material table in Mysql

# Upload Picture Interface Definition
|          | **Introduction**                        |
| -------- | ------------------------------- |
| Interface path | /api/v1/material/upload_picture |
| request method | POST                            |
| parameter     | MultipartFile                   |
| Response result | ResponseResult                  |

# Query Material List Interface Definition
|          | **Introduction**              |
| -------- | --------------------- |
| Interface path | /api/v1/material/list |
| request method | POST                  |
| parameter     | WmMaterialDto         |
| Response result | ResponseResult        |

# Query Channel List Interfaces Definition
|          | **Introduction**                 |
| -------- | ------------------------ |
| Interface path | /api/v1/channel/channels |
| request method | POST                     |
| parameter     | Null                       |
| Response result | ResponseResult           |

# Query Article List Interfaces Definition
|          | **Introduction**                 |
| -------- | ------------------------ |
| Interface path | /api/v1/news/list |
| request method | POST                     |
| parameter     | WmNewsPageReqDto                       |
| Response result | ResponseResult           |

# Article (new/modified) save process
![](/resources/saveArticle.png)

* Front-end commit to publish or save as a draft
* The background determines whether the article id is included in the request
* If the id is not included, it is added
** Perform the operation to add an article
** Build the relationship between the image and the material related to the content of the article
** Build the relationship between the article cover picture and the material
* If the id is included, it is a modification request
** Delete all relationships between the article and the material
** Perform a modification
** Build the relationship between the image and the material related to the content of the article
** Build the relationship between the article cover picture and the material

# Save Article Interface Definition
|          | **Introduction**               |
| -------- | ---------------------- |
| Interface path | /api/v1/channel/submit |
| request method | POST                   |
| parameter     | WmNewsDto              |
| Response result | ResponseResult         |

# Automatic review process of we-media articles
![](/resources/article_audit.png)

1 After the article is published on the media side, the article is reviewed

2 The main review is to review the content of the article (text content and pictures).

3 Review text with interfaces provided by third parties

4 Use the interface provided by a third party to review the images, since the images are stored in minIO, they need to be downloaded before they can be reviewed

5 If the audit fails, you need to modify the status of the we-media article. status:2 Audit fails Status :3 Switch to manual audit

6 If the audit is successful, you need to create the article required by the app side in the article microservice

# Baidu intelligent cloud content review platform 
* Service link: https://cloud.baidu.com/doc/ANTIPORN/index.html
* Image Audit Interface：
```java
public class GreenImageScan {
    //Set APPID/AK/SK
    private String APP_ID;
    private String API_KEY;
    private String SECRET_KEY;

    public Map<String, String> imageScan(byte[] imgByte) {
        // Initial a AipContentCensor
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        Map<String, String> resultMap = new HashMap<>();
        JSONObject res = client.imageCensorUserDefined(imgByte, null);
        System.out.println(res.toString(2));
        //returen response result
        Map<String, Object> map = res.toMap();
//        get special feild
        String conclusion = (String) map.get("conclusion");

        if (conclusion.equals("合规")) {
            resultMap.put("conclusion", conclusion);
            return resultMap;
        }
//        get special conllection feild
        JSONArray dataArrays = res.getJSONArray("data");
        String msg = "";
        for (Object result : dataArrays) {
            //get reasons
            msg = ((JSONObject) result).getString("msg");
        }

        resultMap.put("conclusion", conclusion);
        resultMap.put("msg", msg);
        return resultMap;

    }
}
```

* Text Audit Interface：
```java
public class GreenTextScan {
    //Set APPID/AK/SK
    private String APP_ID;
    private String API_KEY;
    private String SECRET_KEY;

    public Map<String, String> textScan(String content) {
        // Initial a AipContentCensor
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        Map<String, String> resultMap = new HashMap<>();
        JSONObject res = client.textCensorUserDefined(content);
        System.out.println(res.toString(2));
        //return response result
        Map<String, Object> map = res.toMap();
//        get special feild
        String conclusion = (String) map.get("conclusion");

        if (conclusion.equals("合规")) {
            resultMap.put("conclusion", conclusion);
            return resultMap;
        }
//        get special collection feild
        JSONArray dataArrays = res.getJSONArray("data");
        String msg = "";
        for (Object result : dataArrays) {
            //get reasons
            msg = ((JSONObject) result).getString("msg");
        }

        resultMap.put("conclusion", "合格");
        resultMap.put("msg", msg);
        return resultMap;
    }
}
```

## After the article review is successful, it is necessary to add article data to the article library of the app
1.Save ap article information in ap_article

2.Save the article configuration information in ap_article_config

3.Save article content in ap_article_content

## The we-media microservice invokes the article microservice remotely through the feign interface

|          | **Intruduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/article/save |
| request method | POST                 |
| parameter     | ArticleDto           |
| response result | ResponseResult       |

# feign Remote interface invocation mode
![](/resources/feignRemote.png)

# The feign service is degraded
* Service degradation is a way for a service to protect itself, or a way to protect downstream services, to ensure that the service does not become unavailable due to a sudden surge in requests, and that the service does not crash.

* Service degradation can cause requests to fail, but it does not cause blocking.

# Implementation Step
①：Write fallback logic in the heima-leadnews-feign-api, add classes to the we-media microservice, and scan packages for fallback code classes

②：The remote interface points to the downgrade code

③：The fallback heima-leadnews-wemedia function is enabled on the client

# Asynchronous thread way to censorship articles
①：Add @Async annotation to automatically audited methods（indicating asynchronous call）

②：Call the censorship method after the article has been published successfully

③：Enable asynchronous calls using the @EnableAsync annotation in the bootstrap class

## Maintain a set of sensitive words yourself, DFA implementation
* The full name of DFA is Deterministic Finite Automaton, that is, deterministic finite automaton.

* Storage: All sensitive words are stored in multiple maps at once, which is the structure shown in the following figure:

![](/resources/DFA结构.png)

# Filter out sensitive words in the image text
* Optical Character Recognition (OCR) is the process by which electronic devices (such as scanners or digital cameras) examine characters printed on paper, determine their shape by detecting dark and light patterns, and then translate the shape into computer text using character recognition methods

# Tess4j sample:
```java
    /**
     * Recognize the text in the picture
     * @param args
     */
    public static void main(String[] args) throws TesseractException {
//        Create an instance
        ITesseract tesseract = new Tesseract();
//        Set the font library path
        tesseract.setDatapath("/opt/local/share/tessdata");
//        Setting language -- Simplified Chinese
        tesseract.setLanguage("chi_sim");

        File file = new File("/Users/yangjianing/Desktop/test.png");

//        Identify picture
        String result = tesseract.doOCR(file);
        System.out.println("The results of the identification are:" + result);
    }
```

# The definition of the up and down interface
|          | **Introduction**                |
| -------- | ----------------------- |
| interface path | /api/v1/news/down_or_up |
| request method | POST                    |
| parameter     | DTO                     |
| response result | ResponseResult          |