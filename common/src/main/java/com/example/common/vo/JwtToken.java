package com.example.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 授权中心授权后给客户端的token
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {

    private String token;

    public static void main(String[] args) {
        System.out.println("eyJhbGciOiJSUzI1NiJ9.eyJlLWNvbW1lcmNlLXVzZXIiOiJ7XCJpZFwiOjEwLFwidXNlcm5hbWVcIjpcImFhYVwifSIsImp0aSI6IjQ2MzZiMmZkLWU4N2QtNGZiMC05NWFmLTBkN2IzNWRkZWY2YSIsImV4cCI6MTY4MDg4MzIwMH0.aOm38a036zj8s9_FFN2hDq9m4l9olOAGdWEKIs8w9LT5p34sIJg6oBsn8FSQcyPqvZndxyw0rllLTrUKIdYU7MOwUCRW_46n2-GjEtMUcUGJNkMA-5jenUjOa2Vh1fHfHv-uueThUNIX822LlFnbZSNWuQyoZLEEnbruGoGlkr2qR9aJeS6fbbwjHlJreU0gOV5aXou1fH8W5iIXBzy5BYfPtbXOuqFb0Q4iaOpgR728WDDL0xBPhWuIsbCiyIJ6V5Ib8vBfOv5oLqc36i370C1rLSc1782iAxsOMN8xjZAUQTTuv5MbdE4rr49MKanemWTUidjbPp-bR9TAhWl4tQ".length());
    }

}
