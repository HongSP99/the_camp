package io.camp.payment.util;

import org.json.JSONObject;

public class TestPrintPayment {

    public void printKakaoApi(String json) {
        JSONObject obj = new JSONObject(json);

        System.out.println("-------------------------------------------------");

        String status = obj.getString("status");
        System.out.println("status: " + status);

        String id = obj.getString("id");
        System.out.println("id: " + id);

        String transactionId = obj.getString("transactionId");
        System.out.println("transactionId: " + transactionId);

        String merchantId = obj.getString("merchantId");
        System.out.println("merchantId: " + merchantId);

        String storeId = obj.getString("storeId");
        System.out.println("storeId: " + storeId);

        System.out.println("-------------------------------------------------");

        JSONObject method = obj.getJSONObject("method");
        System.out.println("method: " + method);

        String method_type = method.getString("type");
        System.out.println("method_type: " + method_type);

        String method_provider = method.getString("provider");
        System.out.println("method_provider: " + method_provider);

        JSONObject method_easyPayMethod = method.getJSONObject("easyPayMethod");
        System.out.println("method_easyPayMethod: " + method_easyPayMethod);

        String method_easyPayMethod_type = method_easyPayMethod.getString("type");
        System.out.println("method_easyPayMethod_type: " + method_easyPayMethod_type);

        System.out.println("-------------------------------------------------");

        JSONObject channel = obj.getJSONObject("channel");
        System.out.println("channel: " + channel);

        String channel_type = channel.getString("type");
        System.out.println("channel_type: " + channel_type);

        String channel_id = channel.getString("id");
        System.out.println("channel_id: " + channel_id);

        String channel_key = channel.getString("key");
        System.out.println("channel_key: " + channel_key);

        String channel_name = channel.getString("name");
        System.out.println("channel_name: " + channel_name);

        String channel_pgProvider = channel.getString("pgProvider");
        System.out.println("channel_pgProvider: " + channel_pgProvider);

        String channel_pgMerchantId = channel.getString("pgMerchantId");
        System.out.println("channel_pgMerchantId: " + channel_pgMerchantId);

        System.out.println("-------------------------------------------------");

        String version = obj.getString("version");
        System.out.println("version: " + version);

        String requestedAt = obj.getString("requestedAt");
        System.out.println("requestedAt: " + requestedAt);

        String updatedAt = obj.getString("updatedAt");
        System.out.println("updatedAt: " + updatedAt);

        String statusChangedAt = obj.getString("statusChangedAt");
        System.out.println("statusChangedAt: " + statusChangedAt);

        String orderName = obj.getString("orderName");
        System.out.println("orderName: " + orderName);

        System.out.println("-------------------------------------------------");

        JSONObject amount = obj.getJSONObject("amount");
        System.out.println("amount: " + amount);

        int amount_total = amount.getInt("total");
        System.out.println("amount_total: " + amount_total);

        int amount_taxFree = amount.getInt("taxFree");
        System.out.println("amount_taxFree: " + amount_taxFree);

        int amount_vat = amount.getInt("vat");
        System.out.println("amount_vat: " + amount_vat);

        int amount_supply = amount.getInt("supply");
        System.out.println("amount_supply: " + amount_supply);

        int amount_discount = amount.getInt("discount");
        System.out.println("amount_discount: " + amount_discount);

        int amount_paid = amount.getInt("paid");
        System.out.println("amount_paid: " + amount_paid);

        int amount_cancelled = amount.getInt("cancelled");
        System.out.println("amount_cancelled: " + amount_cancelled);

        int amount_cancelledTaxFree = amount.getInt("cancelledTaxFree");
        System.out.println("amount_cancelledTaxFree: " + amount_cancelledTaxFree);

        System.out.println("-------------------------------------------------");

        String currency = obj.getString("currency");
        System.out.println("currency: " + currency);

        System.out.println("-------------------------------------------------");

        JSONObject customer = obj.getJSONObject("customer");
        System.out.println("customer: " + customer);

        String customer_id = customer.getString("id");
        System.out.println("customer_id: " + customer_id);

        String customer_name = customer.getString("name");
        System.out.println("customer_name: " + customer_name);

        String customer_email = customer.getString("email");
        System.out.println("customer_email: " + customer_email);

        String customer_phoneNumber = customer.getString("phoneNumber");
        System.out.println("customer_phoneNumber: " + customer_phoneNumber);

        System.out.println("-------------------------------------------------");

        String promotionId = obj.getString("promotionId");
        System.out.println("promotionId: " + promotionId);

        boolean isCulturalExpense = obj.getBoolean("isCulturalExpense");
        System.out.println("isCulturalExpense: " + isCulturalExpense);

        String paidAt = obj.getString("paidAt");
        System.out.println("paidAt: " + paidAt);

        String pgTxId = obj.getString("pgTxId");
        System.out.println("pgTxId: " + pgTxId);

        String pgResponse = obj.getString("pgResponse");
        System.out.println("pgResponse: " + pgResponse);

        String receiptUrl = obj.getString("receiptUrl");
        System.out.println("receiptUrl: " + receiptUrl);

        System.out.println("-------------------------------------------------");
    }
    public void printTossApi(String json) {
        JSONObject obj = new JSONObject(json);

        System.out.println("-------------------------------------------------");

        String status = obj.getString("status");
        System.out.println("status: " + status);

        String id = obj.getString("id");
        System.out.println("id: " + id);

        String transactionId = obj.getString("transactionId");
        System.out.println("transactionId: " + transactionId);

        String merchantId = obj.getString("merchantId");
        System.out.println("merchantId: " + merchantId);

        String storeId = obj.getString("storeId");
        System.out.println("storeId: " + storeId);

        System.out.println("-------------------------------------------------");

        JSONObject method = obj.getJSONObject("method");
        System.out.println("method: " + method);

        String method_type = method.getString("type");
        System.out.println("method_type: " + method_type);

        String method_provider = method.getString("provider");
        System.out.println("method_provider: " + method_provider);

        JSONObject method_easyPayMethod = method.getJSONObject("easyPayMethod");
        System.out.println("method_easyPayMethod: " + method_easyPayMethod);

        String method_easyPayMethod_type = method_easyPayMethod.getString("type");
        System.out.println("method_easyPayMethod_type: " + method_easyPayMethod_type);

        JSONObject method_easyPayMethod_card = method_easyPayMethod.getJSONObject("card");
        System.out.println("method_easyPayMethod_card: " + method_easyPayMethod_card);

        String method_easyPayMethod_card_publisher = method_easyPayMethod_card.getString("publisher");
        System.out.println("method_easyPayMethod_card_publisher: " + method_easyPayMethod_card_publisher);

        String method_easyPayMethod_card_issuer = method_easyPayMethod_card.getString("issuer");
        System.out.println("method_easyPayMethod_card_issuer: " + method_easyPayMethod_card_issuer);

        String method_easyPayMethod_card_brand = method_easyPayMethod_card.getString("brand");
        System.out.println("method_easyPayMethod_card_brand: " + method_easyPayMethod_card_brand);

        String method_easyPayMethod_card_type = method_easyPayMethod_card.getString("type");
        System.out.println("method_easyPayMethod_card_type: " + method_easyPayMethod_card_type);

        String method_easyPayMethod_card_bin = method_easyPayMethod_card.getString("bin");
        System.out.println("method_easyPayMethod_card_bin: " + method_easyPayMethod_card_bin);

        String method_easyPayMethod_card_name = method_easyPayMethod_card.getString("name");
        System.out.println("method_easyPayMethod_card_name: " + method_easyPayMethod_card_name);

        String method_easyPayMethod_card_number = method_easyPayMethod_card.getString("number");
        System.out.println("method_easyPayMethod_card_number: " + method_easyPayMethod_card_number);

        String method_easyPayMethod_approvalNumber = method_easyPayMethod.getString("approvalNumber");
        System.out.println("method_easyPayMethod_approvalNumber: " + method_easyPayMethod_approvalNumber);

        JSONObject method_easyPayMethod_installment = method_easyPayMethod.getJSONObject("installment");
        System.out.println("method_easyPayMethod_installment: " + method_easyPayMethod_installment);

        int method_easyPayMethod_installment_month = method_easyPayMethod_installment.getInt("month");
        System.out.println("method_easyPayMethod_installment_month: " + method_easyPayMethod_installment_month);

        boolean method_easyPayMethod_installment_isInterestFree = method_easyPayMethod_installment.getBoolean("isInterestFree");
        System.out.println("method_easyPayMethod_installment_isInterestFree: " + method_easyPayMethod_installment_isInterestFree);

        System.out.println("-------------------------------------------------");

        JSONObject channel = obj.getJSONObject("channel");
        System.out.println("channel: " + channel);

        String channel_type = channel.getString("type");
        System.out.println("channel_type: " + channel_type);

        String channel_id = channel.getString("id");
        System.out.println("channel_id: " + channel_id);

        String channel_key = channel.getString("key");
        System.out.println("channel_key: " + channel_key);

        String channel_name = channel.getString("name");
        System.out.println("channel_name: " + channel_name);

        String channel_pgProvider = channel.getString("pgProvider");
        System.out.println("channel_pgProvider: " + channel_pgProvider);

        String channel_pgMerchantId = channel.getString("pgMerchantId");
        System.out.println("channel_pgMerchantId: " + channel_pgMerchantId);

        System.out.println("-------------------------------------------------");

        String version = obj.getString("version");
        System.out.println("version: " + version);

        String requestedAt = obj.getString("requestedAt");
        System.out.println("requestedAt: " + requestedAt);

        String updatedAt = obj.getString("updatedAt");
        System.out.println("updatedAt: " + updatedAt);

        String statusChangedAt = obj.getString("statusChangedAt");
        System.out.println("statusChangedAt: " + statusChangedAt);

        String orderName = obj.getString("orderName");
        System.out.println("orderName: " + orderName);

        System.out.println("-------------------------------------------------");

        JSONObject amount = obj.getJSONObject("amount");
        System.out.println("amount: " + amount);

        int amount_total = amount.getInt("total");
        System.out.println("amount_total: " + amount_total);

        int amount_taxFree = amount.getInt("taxFree");
        System.out.println("amount_taxFree: " + amount_taxFree);

        int amount_vat = amount.getInt("vat");
        System.out.println("amount_vat: " + amount_vat);

        int amount_supply = amount.getInt("supply");
        System.out.println("amount_supply: " + amount_supply);

        int amount_discount = amount.getInt("discount");
        System.out.println("amount_discount: " + amount_discount);

        int amount_paid = amount.getInt("paid");
        System.out.println("amount_paid: " + amount_paid);

        int amount_cancelled = amount.getInt("cancelled");
        System.out.println("amount_cancelled: " + amount_cancelled);

        int amount_cancelledTaxFree = amount.getInt("cancelledTaxFree");
        System.out.println("amount_cancelledTaxFree: " + amount_cancelledTaxFree);

        System.out.println("-------------------------------------------------");

        String currency = obj.getString("currency");
        System.out.println("currency: " + currency);

        System.out.println("-------------------------------------------------");

        JSONObject customer = obj.getJSONObject("customer");
        System.out.println("customer: " + customer);

        String customer_id = customer.getString("id");
        System.out.println("customer_id: " + customer_id);

        String customer_name = customer.getString("name");
        System.out.println("customer_name: " + customer_name);

        String customer_email = customer.getString("email");
        System.out.println("customer_email: " + customer_email);

        String customer_phoneNumber = customer.getString("phoneNumber");
        System.out.println("customer_phoneNumber: " + customer_phoneNumber);

        System.out.println("-------------------------------------------------");

        String promotionId = obj.getString("promotionId");
        System.out.println("promotionId: " + promotionId);

        boolean isCulturalExpense = obj.getBoolean("isCulturalExpense");
        System.out.println("isCulturalExpense: " + isCulturalExpense);

        String paidAt = obj.getString("paidAt");
        System.out.println("paidAt: " + paidAt);

        String pgTxId = obj.getString("pgTxId");
        System.out.println("pgTxId: " + pgTxId);

        String pgResponse = obj.getString("pgResponse");
        System.out.println("pgResponse: " + pgResponse);

        String receiptUrl = obj.getString("receiptUrl");
        System.out.println("receiptUrl: " + receiptUrl);

        System.out.println("-------------------------------------------------");
    }
}
