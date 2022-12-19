package com.eroldme.d66.subreddit.controller;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.eroldme.d66.subreddit.controller.CustomUtils;
import com.eroldme.d66.subreddit.controller.VoteController;
import com.eroldme.d66.subreddit.dto.VoteDto;
import com.eroldme.d66.subreddit.mapper.EntityMapper;
import com.eroldme.d66.subreddit.mapper.VoteMapper;
import com.eroldme.d66.subreddit.service.VoteService;
import com.eroldme.d66.subreddit.vote.Vote;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@Transactional
public class VoteControllerTest {
  private static final String ENDPOINT_URL = "/api/vote";
  @InjectMocks
  private VoteController voteController;
  @Mock
  private VoteService voteService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders
            .standaloneSetup(voteController)
            //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            //.addFilter(CustomFilter::doFilter)
            .build();
  }

  @Test
  public void findAllByPage() throws Exception {
    Page<VoteDto> page = new PageImpl<>(Collections.singletonList(VoteBuilder.getDto()));

    Mockito.when(voteService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);

    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

    Mockito.verify(voteService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
    Mockito.verifyNoMoreInteractions(voteService);

  }

  @Test
  public void getById() throws Exception {
    Mockito.when(voteService.findById(ArgumentMatchers.anyLong())).thenReturn(VoteBuilder.getDto());

    mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    Mockito.verify(voteService, Mockito.times(1)).findById("1");
    Mockito.verifyNoMoreInteractions(voteService);
  }

  @Test
  public void save() throws Exception {
    Mockito.when(voteService.save(ArgumentMatchers.any(VoteDto.class))).thenReturn(VoteBuilder.getDto());

    mockMvc.perform(
                    MockMvcRequestBuilders.post(ENDPOINT_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(CustomUtils.asJsonString(VoteBuilder.getDto())))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    Mockito.verify(voteService, Mockito.times(1)).save(ArgumentMatchers.any(VoteDto.class));
    Mockito.verifyNoMoreInteractions(voteService);
  }

  @Test
  public void update() throws Exception {
    Mockito.when(voteService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(VoteBuilder.getDto());

    mockMvc.perform(
                    MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(CustomUtils.asJsonString(VoteBuilder.getDto())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(voteService, Mockito.times(1)).update(ArgumentMatchers.any(VoteDto.class), ArgumentMatchers.anyLong());
    Mockito.verifyNoMoreInteractions(voteService);
  }

  @Test
  public void delete() throws Exception {
    Mockito.doNothing().when(voteService).deleteById(ArgumentMatchers.anyLong());
    mockMvc.perform(
            MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(CustomUtils.asJsonString(VoteBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(voteService, Mockito.times(1)).deleteById(Mockito.anyLong());
    Mockito.verifyNoMoreInteractions(voteService);
  }
}