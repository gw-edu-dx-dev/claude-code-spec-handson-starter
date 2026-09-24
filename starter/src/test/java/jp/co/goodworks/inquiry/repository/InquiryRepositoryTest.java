package jp.co.goodworks.inquiry.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jp.co.goodworks.inquiry.domain.Inquiry;
import jp.co.goodworks.inquiry.domain.InquiryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

@DataJpaTest
class InquiryRepositoryTest {

    @Autowired
    InquiryRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        repository.save(new Inquiry("VPN接続エラー", "認証に失敗します", InquiryStatus.OPEN));
        repository.save(new Inquiry("PC交換", "端末の交換をお願いします", InquiryStatus.IN_PROGRESS));
        repository.save(new Inquiry("VPN申請", "利用申請の確認", InquiryStatus.CLOSED));
    }

    @Test
    void keywordSearchMatchesTitleAndContent() {
        List<Inquiry> result = repository.search("VPN", null);
        assertThat(result).hasSize(2);
    }

    @Test
    void statusFilterReturnsOnlySelectedStatus() {
        List<Inquiry> result = repository.search("", InquiryStatus.IN_PROGRESS);
        assertThat(result)
                .hasSize(1)
                .allMatch(i -> i.getStatus() == InquiryStatus.IN_PROGRESS);
    }

    @Test
    void keywordAndStatusCanBeCombined() {
        List<Inquiry> result = repository.search("VPN", InquiryStatus.CLOSED);
        assertThat(result)
                .extracting(Inquiry::getTitle)
                .containsExactly("VPN申請");
    }

    @Test
    void blankConditionsReturnAll() {
        List<Inquiry> result = repository.search("", null);
        assertThat(result).hasSize(3);
    }
}
