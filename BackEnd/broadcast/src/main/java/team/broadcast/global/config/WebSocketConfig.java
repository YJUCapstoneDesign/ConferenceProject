package team.broadcast.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import team.broadcast.global.handler.MindMapHandler;
import team.broadcast.global.handler.RoomHandler;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final MindMapHandler mindMapHandler;
    private final RoomHandler roomHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(mindMapHandler, "/app")
                .setAllowedOrigins("*");

        registry.addHandler(roomHandler, "/room")
                .setAllowedOrigins("*");
    }
}
