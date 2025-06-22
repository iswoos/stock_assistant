package com.stock.stock_assistant.adapter.in.news;

import com.stock.stock_assistant.application.port.in.news.GetMarketNewsListUseCase;
import com.stock.stock_assistant.dto.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMarketNewsListController {

    private final GetMarketNewsListUseCase getMarketNewsListUseCase;

    // 최신 뉴스 조회
    @GetMapping("/news")
    public ResponseEntity<ApiResponse<String>> GetMarketNews() {
        ApiResponse<String> response = ApiResponse.ok(getMarketNewsListUseCase.getMarketNewsList());
        return ApiResponse.toResponseEntity(response);
    }
}
