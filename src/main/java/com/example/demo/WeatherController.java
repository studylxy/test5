package com.example.demo;

// WeatherController.java
// 创建一个名为 'WeatherController' 的类，并添加以下内容

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @GetMapping("/getWeather")
    public List<String> getWeatherData(@RequestParam String city) throws Exception {
        String apiURL = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx/getWeatherbyCityName?theCityName=" + city;
        RestTemplate restTemplate = new RestTemplate();
        String xmlResult = restTemplate.getForObject(apiURL, String.class);

        // 解析 XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlResult)));

        NodeList nodes = doc.getElementsByTagName("string");
        List<String> weatherData = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            weatherData.add(nodes.item(i).getTextContent());
        }

        return weatherData;
    }
}
