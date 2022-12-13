package pl.Marcin.ideas.question.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.Marcin.ideas.question.domain.model.Answer;
import pl.Marcin.ideas.question.service.AnswerService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnswerApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerService answerService;
    @Autowired
    private ObjectMapper objectMapper;
    private Answer answer;
    private List<Answer> answers;

    @BeforeEach
    void setUp() {
        answer = new Answer("A1");
        answers = List.of(
                new Answer("A1"),
                new Answer("A2"),
                new Answer("A3"));

        when(answerService.getAnswers()).thenReturn(answers);
        when(answerService.getAnswer(any())).thenReturn(answer);
        when(answerService.updateAnswer(any(), any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> invocationOnMock.getArguments()[1]);
    }

    @Test
    void shouldGetAnswers() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/answers"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(answers)));
    }

    @Test
    void shouldGetAnswer() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/answers/{id}", answer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(answer)));
    }

    @Test
    void shouldUpdateAnswer() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/answers/{id}", answer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answer)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(objectMapper.writeValueAsString(answer)));
    }

    @Test
    void shouldDeleteAnswer() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/answers/{id}", answer.getId()))
                .andExpect(status().isNoContent());
    }
}