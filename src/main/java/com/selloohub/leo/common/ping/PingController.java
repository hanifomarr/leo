package com.selloohub.leo.common.ping;

import com.selloohub.leo.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/api/v1/admin/ping")
    public ApiResponse<String> ping() {
        return ApiResponse.success("Pong");
    }
}
