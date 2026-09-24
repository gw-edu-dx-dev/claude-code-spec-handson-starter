package jp.co.goodworks.inquiry.config;

import jp.co.goodworks.inquiry.domain.Inquiry;
import jp.co.goodworks.inquiry.domain.InquiryStatus;
import jp.co.goodworks.inquiry.repository.InquiryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleDataConfig {

    @Bean
    CommandLineRunner seedInquiries(InquiryRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            repository.save(new Inquiry("VPNに接続できない", "在宅勤務環境からVPN接続時に認証エラーになります。", InquiryStatus.OPEN));
            repository.save(new Inquiry("経費精算の申請方法", "交通費の申請手順を確認したいです。", InquiryStatus.CLOSED));
            repository.save(new Inquiry("PCの動作が遅い", "起動後しばらくCPU使用率が高い状態です。", InquiryStatus.IN_PROGRESS));
            repository.save(new Inquiry("共有フォルダの権限", "プロジェクト共有フォルダへのアクセス権を追加してください。", InquiryStatus.OPEN));
            repository.save(new Inquiry("パスワード変更", "社内システムのパスワード変更手順を教えてください。", InquiryStatus.CLOSED));
        };
    }
}
