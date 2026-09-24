package jp.co.goodworks.inquiry.service;

import jp.co.goodworks.inquiry.domain.Inquiry;
import jp.co.goodworks.inquiry.domain.InquiryStatus;
import jp.co.goodworks.inquiry.repository.InquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public List<Inquiry> findAll(String keyword, InquiryStatus status) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim();
        return inquiryRepository.search(normalizedKeyword, status);
    }

    @Transactional
    public Inquiry create(String title, String content, InquiryStatus status) {
        return inquiryRepository.save(new Inquiry(title, content, status));
    }
}
