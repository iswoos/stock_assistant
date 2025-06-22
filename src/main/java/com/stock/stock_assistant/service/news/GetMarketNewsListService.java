package com.stock.stock_assistant.service.news;

import com.stock.stock_assistant.application.port.in.news.GetMarketNewsListUseCase;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class GetMarketNewsListService implements GetMarketNewsListUseCase {

    @Value("${finnhub.api-key}")
    private String api_key;

    @Override
    public String getMarketNewsList() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://finnhub.io/api/v1/news?category=general&token=" + api_key))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray articles = new JSONArray(response.body());

            StringBuilder sb = new StringBuilder();
            int count = 0;

            for (int i = 0; i < articles.length(); i++) {
                JSONObject news = articles.getJSONObject(i);

                // MarketWatch 기사만 필터링
                if ("MarketWatch".equalsIgnoreCase(news.optString("source"))) {
                    sb.append("📰 ").append(news.optString("headline")).append("\n")
                            .append("🔗 ").append(news.optString("url")).append("\n\n");
                    count++;
                }

                // 최대 10개까지만 출력
                if (count >= 10) break;
            }

            if (count == 0) {
                return "⚠️ MarketWatch 기사 없음.";
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "뉴스 불러오기 실패: " + e.getMessage();
        }
    }
}
