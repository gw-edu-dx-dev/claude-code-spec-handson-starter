package jp.co.goodworks.inquiry.domain;

public enum InquiryStatus {
    OPEN("未対応"),
    IN_PROGRESS("対応中"),
    CLOSED("完了");

    private final String label;

    InquiryStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
