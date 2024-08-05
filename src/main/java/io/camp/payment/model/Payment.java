package io.camp.payment.model;

import io.camp.audit.BaseEntity;
import io.camp.common.exception.ExceptionCode;
import io.camp.common.exception.payment.PaymentException;
import io.camp.reservation.model.Reservation;
import io.camp.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String paymentId;
    private String amount;
    private int amountTotal;
    private String amountTaxFree;
    private String amountCancelledTaxFree;
    private String amountVat;
    private String amountPaid;
    private String amountDiscount;
    private String amountCancelled;
    private String amountSupply;

    @Column(length = 1000)
    private String pgResponse;

    @Column(length = 500)
    private String method;

    @Column(length = 500)
    private String methodEasyPayMethod;

    private String methodEasyPayMethodInstallment;
    private String methodEasyPayMethodInstallmentMonth;
    private String methodEasyPayMethodInstallmentIsInterestFree;
    private String methodEasyPayMethodType;
    private String methodEasyPayMethodApprovalNumber;
    private String methodEasyPayMethodCard;
    private String methodEasyPayMethodCardOwnerType;
    private String methodEasyPayMethodCardNumber;
    private String methodEasyPayMethodCardBin;
    private String methodEasyPayMethodCardName;
    private String methodEasyPayMethodCardPublisher;
    private String methodEasyPayMethodCardType;
    private String methodEasyPayMethodCardBrand;
    private String methodEasyPayMethodCardIssuer;
    private String methodProvider;
    private String methodType;
    private String pgTxId;
    private String channel;
    private String channelPgProvider;
    private String channelName;
    private String channelId;
    private String channelType;
    private String channelKey;
    private String channelPgMerchantId;
    private String storeId;
    private String version;
    private String transactionId;
    private String promotionId;
    private String statusChangedAt;
    private String merchantId;
    private String requestedAt;
    private String paidAt;
    private String currency;
    private String isCulturalExpense;
    private String id;
    private String receiptUrl;
    private String status;
    private String updatedAt;
    private String orderName;
    private String customer;
    private String customerPhoneNumber;
    private String customerName;
    private String customerId;
    private String customerEmail;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_seq")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_seq")
    private Reservation reservation;

    @OneToOne(mappedBy = "payment" , cascade = CascadeType.ALL)
    private PaymentCancellation paymentCancellation;

    public void setPaymentInstanceVariable(PaymentType paymentType, String input) {
        switch (paymentType) {
            case paymentId:
                this.paymentId = input;
                break;
            case amount:
                this.amount = input;
                break;
            case amountTotal:
                this.amountTotal = Integer.valueOf(input);
                break;
            case amountTaxFree:
                this.amountTaxFree = input;
                break;
            case amountCancelledTaxFree:
                this.amountCancelledTaxFree = input;
                break;
            case amountVat:
                this.amountVat = input;
                break;
            case amountPaid:
                this.amountPaid = input;
                break;
            case amountDiscount:
                this.amountDiscount = input;
                break;
            case amountCancelled:
                this.amountCancelled = input;
                break;
            case amountSupply:
                this.amountSupply = input;
                break;
            case pgResponse:
                this.pgResponse = input;
                break;
            case method:
                this.method = input;
                break;
            case methodEasyPayMethod:
                this.methodEasyPayMethod = input;
                break;
            case methodEasyPayMethodInstallment:
                this.methodEasyPayMethodInstallment = input;
                break;
            case methodEasyPayMethodInstallmentMonth:
                this.methodEasyPayMethodInstallmentMonth = input;
                break;
            case methodEasyPayMethodInstallmentIsInterestFree:
                this.methodEasyPayMethodInstallmentIsInterestFree = input;
                break;
            case methodEasyPayMethodType:
                this.methodEasyPayMethodType = input;
                break;
            case methodEasyPayMethodApprovalNumber:
                this.methodEasyPayMethodApprovalNumber = input;
                break;
            case methodEasyPayMethodCard:
                this.methodEasyPayMethodCard = input;
                break;
            case methodEasyPayMethodCardOwnerType:
                this.methodEasyPayMethodCardOwnerType = input;
                break;
            case methodEasyPayMethodCardNumber:
                this.methodEasyPayMethodCardNumber = input;
                break;
            case methodEasyPayMethodCardBin:
                this.methodEasyPayMethodCardBin = input;
                break;
            case methodEasyPayMethodCardName:
                this.methodEasyPayMethodCardName = input;
                break;
            case methodEasyPayMethodCardPublisher:
                this.methodEasyPayMethodCardPublisher = input;
                break;
            case methodEasyPayMethodCardType:
                this.methodEasyPayMethodCardType = input;
                break;
            case methodEasyPayMethodCardBrand:
                this.methodEasyPayMethodCardBrand = input;
                break;
            case methodEasyPayMethodCardIssuer:
                this.methodEasyPayMethodCardIssuer = input;
                break;
            case methodProvider:
                this.methodProvider = input;
                break;
            case methodType:
                this.methodType = input;
                break;
            case pgTxId:
                this.pgTxId = input;
                break;
            case channel:
                this.channel = input;
                break;
            case channelPgProvider:
                this.channelPgProvider = input;
                break;
            case channelName:
                this.channelName = input;
                break;
            case channelId:
                this.channelId = input;
                break;
            case channelType:
                this.channelType = input;
                break;
            case channelKey:
                this.channelKey = input;
                break;
            case channelPgMerchantId:
                this.channelPgMerchantId = input;
                break;
            case storeId:
                this.storeId = input;
                break;
            case version:
                this.version = input;
                break;
            case transactionId:
                this.transactionId = input;
                break;
            case promotionId:
                this.promotionId = input;
                break;
            case statusChangedAt:
                this.statusChangedAt = input;
                break;
            case merchantId:
                this.merchantId = input;
                break;
            case requestedAt:
                this.requestedAt = input;
                break;
            case paidAt:
                this.paidAt = input;
                break;
            case currency:
                this.currency = input;
                break;
            case isCulturalExpense:
                this.isCulturalExpense = input;
                break;
            case id:
                this.id = input;
                break;
            case receiptUrl:
                this.receiptUrl = input;
                break;
            case status:
                this.status = input;
                break;
            case updatedAt:
                this.updatedAt = input;
                break;
            case orderName:
                this.orderName = input;
                break;
            case customer:
                this.customer = input;
                break;
            case customerPhoneNumber:
                this.customerPhoneNumber = input;
                break;
            case customerName:
                this.customerName = input;
                break;
            case customerId:
                this.customerId = input;
                break;
            case customerEmail:
                this.customerEmail = input;
                break;
            default:
                throw new PaymentException(ExceptionCode.PAYMENT_IMPORT_TYPE);
        }
    }
}
