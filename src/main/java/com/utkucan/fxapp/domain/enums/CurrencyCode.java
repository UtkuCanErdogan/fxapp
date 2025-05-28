package com.utkucan.fxapp.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Getter
@Schema(description = "Supported currency codes")
public enum CurrencyCode {
    AED("AED"),
    ARS("ARS"),
    AUD("AUD"),
    BGN("BGN"),
    BHD("BHD"),
    BRL("BRL"),
    CAD("CAD"),
    CHF("CHF"),
    CLP("CLP"),
    CNY("CNY"),
    COP("COP"),
    CZK("CZK"),
    DKK("DKK"),
    EGP("EGP"),
    EUR("EUR"),
    GBP("GBP"),
    GHS("GHS"),
    HKD("HKD"),
    HUF("HUF"),
    IDR("IDR"),
    INR("INR"),
    ISK("ISK"),
    JOD("JOD"),
    JPY("JPY"),
    KRW("KRW"),
    KWD("KWD"),
    LKR("LKR"),
    MAD("MAD"),
    MXN("MXN"),
    MYR("MYR"),
    NGN("NGN"),
    NOK("NOK"),
    NZD("NZD"),
    PEN("PEN"),
    PHP("PHP"),
    PKR("PKR"),
    PLN("PLN"),
    QAR("QAR"),
    RON("RON"),
    RUB("RUB"),
    SAR("SAR"),
    SEK("SEK"),
    SGD("SGD"),
    THB("THB"),
    TRY("TRY"),
    TWD("TWD"),
    UAH("UAH"),
    USD("USD"),
    VND("VND"),
    ZAR("ZAR");

    private final String code;

    CurrencyCode(String code) {
        this.code = code;
    }

    public static Optional<CurrencyCode> fromCode(String code) {
        return Arrays.stream(CurrencyCode.values())
                .filter(c -> c.code.equalsIgnoreCase(code))
                .findFirst();
    }

    public static List<CurrencyCode> getDataSet() {
        return Arrays.asList(CurrencyCode.values());
    }
}
