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
import pl.Marcin.ideas.question.domain.model.Question;
import pl.Marcin.ideas.question.service.AnswerService;
import pl.Marcin.ideas.question.service.QuestionService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private AnswerService answerService;
    @Autowired
    private ObjectMapper objectMapper;
    private Question question;
    private Answer answer;
    private List<Question> questions;
    private List<Answer> answers;

    @BeforeEach
    void setUp() {
        question = new Question("Q1");
        questions = List.of(question, new Question("Q2"), new Question("Q3"));
        answer = new Answer("A1");
        answers = List.of(new Answer("A1"), new Answer("A2"), new Answer("A3"));

        when(questionService.getQuestions()).thenReturn(questions);
        when(questionService.getQuestion(any())).thenReturn(question);
        when(answerService.findAllByQuestionId(any())).thenReturn(answers);
        when(questionService.updateQuestion(any(), any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> invocationOnMock.getArguments()[1]);
        when(answerService.createAnswer(any(), any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> invocationOnMock.getArguments()[1]);
    }

    @Test
    void shouldGetQuestions() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/questions"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(questions)));
    }

    @Test
    void shouldGetQuestion() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/questions/{id}", question.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(question)));
    }

    @Test
    void shouldFindAllByQuestionId() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/questions/{id}/answers", question.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(answers)));
    }

    @Test
    void shouldUpdateQuestion() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/questions/{id}", question.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(objectMapper.writeValueAsString(question)));
    }

    @Test
    void shouldDeleteQuestion() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/questions/{id}", question.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldCreatAnswer() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/questions/{id}", question.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answer)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(answer)));
    }
}