package jp.co.goodworks.inquiry.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import jp.co.goodworks.inquiry.domain.InquiryStatus;
import jp.co.goodworks.inquiry.service.InquiryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(InquiryController.class)
class InquiryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    InquiryService inquiryService;

    @Test
    void listPassesKeywordAndStatusToService() throws Exception {
        when(inquiryService.findAll(any(), any())).thenReturn(List.of());

        mockMvc.perform(get("/inquiries")
                        .param("keyword", "VPN")
                        .param("status", "OPEN"))
                .andExpect(status().isOk())
                .andExpect(view().name("inquiries/list"))
                .andExpect(model().attribute("keyword", "VPN"))
                .andExpect(model().attribute("selectedStatus", InquiryStatus.OPEN));

        verify(inquiryService).findAll("VPN", InquiryStatus.OPEN);
    }

    @Test
    void validFormCreatesInquiry() throws Exception {
        mockMvc.perform(post("/inquiries")
                        .param("title", "テスト問い合わせ")
                        .param("content", "問い合わせ内容")
                        .param("status", "OPEN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inquiries"));

        verify(inquiryService).create("テスト問い合わせ", "問い合わせ内容", InquiryStatus.OPEN);
    }

    @Test
    void invalidFormReturnsForm() throws Exception {
        mockMvc.perform(post("/inquiries")
                        .param("title", "")
                        .param("content", "")
                        .param("status", "OPEN"))
                .andExpect(status().isOk())
                .andExpect(view().name("inquiries/form"))
                .andExpect(model().attributeHasFieldErrors("inquiryForm", "title", "content"));
    }
}
