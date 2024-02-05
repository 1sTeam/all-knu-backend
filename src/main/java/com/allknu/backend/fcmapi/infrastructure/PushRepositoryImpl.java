package com.allknu.backend.fcmapi.infrastructure;

import com.allknu.backend.global.exception.errors.FcmClientFailedException;
import com.allknu.backend.fcmapi.domain.PushRepository;
import com.allknu.backend.fcmapi.domain.dto.PushToTopicsRequestDto;
import com.allknu.backend.fcmapi.domain.dto.UpdateSubscribeTopicsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PushRepositoryImpl implements PushRepository {

    private final WebClient fcmClient;

    @Override
    public void pushToTopicsRequest(PushToTopicsRequestDto requestDto) {
        fcmClient.post()
                .uri("/api/v1/push/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(error -> Mono.error(new FcmClientFailedException()))
                .block();
    }

    @Override
    public void updateSubscribeTopicsRequest(UpdateSubscribeTopicsRequestDto requestDto) {
        fcmClient.put()
                .uri("/api/v1/push/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(error -> Mono.error(new FcmClientFailedException()))
                .block();
    }
}
