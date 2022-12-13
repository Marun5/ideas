package pl.marcin.ideas.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.marcin.ideas.category.domain.model.Category;
import pl.marcin.ideas.category.service.CategoryService;
import pl.marcin.ideas.question.domain.model.Question;
import pl.marcin.ideas.question.service.QuestionService;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private QuestionService questionService;
    @Autowired
    private ObjectMapper objectMapper;
    private PageImpl<Category> page;
    private Category category;
    private Question question;
    List<Question> questions;

    @BeforeEach
    void setUp() {
        category = new Category("Cat1");
        page = new PageImpl<>(List.of(category, new Category("Cat2"), new Category("Cate3")));
        question = new Question("Q1");
        questions = new LinkedList<>(List.of(question, new Question("Q2")));

        when(categoryService.getCategories((Pageable) any())).thenReturn(page);
        when(categoryService.getCategory(category.getId())).thenReturn(category);
        when(questionService.findAllByCategoryId(any())).thenReturn(questions);
        when(categoryService.createCategory(any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> invocationOnMock.getArguments()[0]);
        when(categoryService.updateCategory(any(), any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> invocationOnMock.getArguments()[1]);
        when(questionService.createQuestion(any(), any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> invocationOnMock.getArguments()[1]);
    }

    @Test
    void shouldGetCategories() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @Test
    void shouldGetCategory() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/categories/single/{id}", category.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(category)));
    }

    @Test
    void shouldFindAllByCategoryId() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/categories/{id}", category.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(questions)));
    }

    @Test
    void shouldCreateCategory() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(category)));
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/categories/{id}", category.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(objectMapper.writeValueAsString(category)));
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/categories/{id}", category.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldCreateQuestion() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(question)));
    }
}