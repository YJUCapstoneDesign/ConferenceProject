package team.broadcast.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import team.broadcast.global.handler.HatHandler;
import team.broadcast.global.handler.MindMapHandler;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final MindMapHandler mindMapHandler;
    private final HatHandler hatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // MindMap 소켓 주소 /app
        registry.addHandler(mindMapHandler, "/app")
                .setAllowedOrigins("*");

        // 생각 모자 소켓 주소 /hat
        registry.addHandler(hatHandler, "/hat")
                .setAllowedOrigins("*");
    }
}
