package team.broadcast.domain.mindmap.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MindMapServiceTest {
    @Autowired
    private MindMapService mindMapService;

    @Test
    @DisplayName("연결 테스트")
    void test() {
        String response = mindMapService.testCode();

        Assertions.assertThat(response).isEqualTo("stored");
    }
}