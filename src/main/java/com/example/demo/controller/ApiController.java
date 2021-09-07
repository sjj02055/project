package com.example.demo.controller;

import com.example.demo.domain.cctv;
import com.example.demo.service.apiService;
import lombok.RequiredArgsConstructor;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class ApiController {

    private final apiService apiService;

    @GetMapping ("/api")
    public String calledApi() throws IOException{
        StringBuilder result = new StringBuilder();

        String keyword="서울특별시 ";
        String keyword2="송파구청";

        keyword = URLEncoder.encode(keyword, "UTF-8").replaceAll("\\+", "%20");
        keyword2 = URLEncoder.encode(keyword2, "UTF-8");

        String urlStr = "http://api.data.go.kr/openapi/tn_pubr_public_cctv_api?"+
                "serviceKey=Ai%2FeAVfEAFcwNCftpi8kxvtp1jWQfJrJImDmZVn0%2Fm96kt3EuO%2FVbsUPwToH0KFcoSNmxJ%2FMs1iFiDhBf7rkfw%3D%3D"+
                "&pageNo=1"+
                "&numOfRows=499"+
                "&type=json"+
                "&institutionNm="+keyword +keyword2
                ;

        URL url = new URL(urlStr);
        System.out.println(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));

        String returnLine;

        while((returnLine = br.readLine()) != null){
            result.append(returnLine+"\n");
        }
        urlConnection.disconnect();;

        String jsonData=  result.toString();
        try {
            JSONObject jObj;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonData);
            JSONObject parseResponse = (JSONObject) jsonObj.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONArray array = (JSONArray) parseBody.get("items");

            for(int i=0; i<array.size(); i++){
                jObj=(JSONObject) array.get(i);

                cctv cctv = new cctv();

                cctv.setInstitutionNm(jObj.get("institutionNm").toString());
                cctv.setLatitude(jObj.get("latitude").toString());
                cctv.setLongitude(jObj.get("longitude").toString());

                apiService.join(cctv);
            }
        }catch (Exception e){
            e.printStackTrace();;
        }
        return "redirect:/";
    }

}