package jp.co.goodworks.inquiry.repository;

import jp.co.goodworks.inquiry.domain.Inquiry;
import jp.co.goodworks.inquiry.domain.InquiryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    @Query("""
        select i from Inquiry i
        where (:keyword = ''
               or lower(i.title) like lower(concat('%', :keyword, '%'))
               or lower(i.content) like lower(concat('%', :keyword, '%')))
          and (:status is null or i.status = :status)
        order by i.createdAt desc
        """)
    List<Inquiry> search(@Param("keyword") String keyword,
                         @Param("status") InquiryStatus status);
}
