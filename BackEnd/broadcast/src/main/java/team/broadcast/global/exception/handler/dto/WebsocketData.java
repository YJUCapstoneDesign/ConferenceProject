package team.broadcast.global.exception.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebsocketData {
    private Long id;
    private Map<String, Object> data;
}
