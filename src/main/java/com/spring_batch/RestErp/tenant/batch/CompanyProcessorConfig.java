package com.spring_batch.RestErp.tenant.batch;

import java.time.LocalDateTime;

import com.spring_batch.RestErp.tenant.dto.CompanyRegistry;
import com.spring_batch.RestErp.tenant.dto.DimCompany;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyProcessorConfig {

    @Bean
    public ItemProcessor<CompanyRegistry, DimCompany> tenantProcessor() {
        return source -> {
            DimCompany target = new DimCompany();
            target.setCompanyId(source.getCompanyId());
            target.setCompanyName(source.getCompanyName());
            target.setCity(source.getCity());
            target.setCountry(source.getCountry());
            target.setCurrency(mapCurrency(source.getCurrency()));
            target.setEmployeeCount(source.getEmployeeCount());
            target.setEndSalaryMonthDay(source.getEndSalaryMonthDay());
            target.setSchemaName(source.getSchemaName());
            target.setActive(source.getActive());

            target.setEffectiveFrom(LocalDateTime.now());
            target.setEffectiveTo(null);
            target.setIsCurrent(true);

            return target;
        };
    }

    private String mapCurrency(Integer currency) {
        if (currency == null) {
            return null;
        }

        return switch (currency) {
            case 0 -> "AED";
            case 1 -> "AFN";
            case 2 -> "ALL";
            case 3 -> "AMD";
            case 4 -> "ANG";
            case 5 -> "AOA";
            case 6 -> "ARS";
            case 7 -> "AUD";
            case 8 -> "AWG";
            case 9 -> "AZN";
            case 10 -> "BAM";
            case 11 -> "BBD";
            case 12 -> "BDT";
            case 13 -> "BGN";
            case 14 -> "BHD";
            case 15 -> "BIF";
            case 16 -> "BMD";
            case 17 -> "BND";
            case 18 -> "BOB";
            case 19 -> "BRL";
            case 20 -> "BSD";
            case 21 -> "BTN";
            case 22 -> "BWP";
            case 23 -> "BYN";
            case 24 -> "BZD";
            case 25 -> "CAD";
            case 26 -> "CDF";
            case 27 -> "CHF";
            case 28 -> "CLP";
            case 29 -> "CNY";
            case 30 -> "COP";
            case 31 -> "CRC";
            case 32 -> "CUP";
            case 33 -> "CVE";
            case 34 -> "CZK";
            case 35 -> "DJF";
            case 36 -> "DKK";
            case 37 -> "DOP";
            case 38 -> "DZD";
            case 39 -> "EGP";
            case 40 -> "ERN";
            case 41 -> "ETB";
            case 42 -> "EUR";
            case 43 -> "FJD";
            case 44 -> "FKP";
            case 45 -> "FOK";
            case 46 -> "GBP";
            case 47 -> "GEL";
            case 48 -> "GGP";
            case 49 -> "GHS";
            case 50 -> "GIP";
            case 51 -> "GMD";
            case 52 -> "GNF";
            case 53 -> "GTQ";
            case 54 -> "GYD";
            case 55 -> "HKD";
            case 56 -> "HNL";
            case 57 -> "HRK";
            case 58 -> "HTG";
            case 59 -> "HUF";
            case 60 -> "IDR";
            case 61 -> "ILS";
            case 62 -> "IMP";
            case 63 -> "INR";
            case 64 -> "IQD";
            case 65 -> "IRR";
            case 66 -> "ISK";
            case 67 -> "JMD";
            case 68 -> "JOD";
            case 69 -> "JPY";
            case 70 -> "KES";
            case 71 -> "KGS";
            case 72 -> "KHR";
            case 73 -> "KID";
            case 74 -> "KMF";
            case 75 -> "KRW";
            case 76 -> "KWD";
            case 77 -> "KYD";
            case 78 -> "KZT";
            case 79 -> "LAK";
            case 80 -> "LBP";
            case 81 -> "LKR";
            case 82 -> "LRD";
            case 83 -> "LSL";
            case 84 -> "LYD";
            case 85 -> "MAD";
            case 86 -> "MDL";
            case 87 -> "MGA";
            case 88 -> "MKD";
            case 89 -> "MMK";
            case 90 -> "MNT";
            case 91 -> "MOP";
            case 92 -> "MRU";
            case 93 -> "MUR";
            case 94 -> "MVR";
            case 95 -> "MWK";
            case 96 -> "MXN";
            case 97 -> "MYR";
            case 98 -> "MZN";
            case 99 -> "NAD";
            case 100 -> "NGN";
            case 101 -> "NIO";
            case 102 -> "NOK";
            case 103 -> "NPR";
            case 104 -> "NZD";
            case 105 -> "OMR";
            case 106 -> "PAB";
            case 107 -> "PEN";
            case 108 -> "PGK";
            case 109 -> "PHP";
            case 110 -> "PKR";
            case 111 -> "PLN";
            case 112 -> "PYG";
            case 113 -> "QAR";
            case 114 -> "RON";
            case 115 -> "RSD";
            case 116 -> "RUB";
            case 117 -> "RWF";
            case 118 -> "SAR";
            case 119 -> "SBD";
            case 120 -> "SCR";
            case 121 -> "SDG";
            case 122 -> "SEK";
            case 123 -> "SGD";
            case 124 -> "SHP";
            case 125 -> "SLE";
            case 126 -> "SOS";
            case 127 -> "SRD";
            case 128 -> "SSP";
            case 129 -> "STN";
            case 130 -> "SYP";
            case 131 -> "SZL";
            case 132 -> "THB";
            case 133 -> "TJS";
            case 134 -> "TMT";
            case 135 -> "TND";
            case 136 -> "TOP";
            case 137 -> "TRY";
            case 138 -> "TTD";
            case 139 -> "TVD";
            case 140 -> "TWD";
            case 141 -> "TZS";
            case 142 -> "UAH";
            case 143 -> "UGX";
            case 144 -> "USD";
            case 145 -> "UYU";
            case 146 -> "UZS";
            case 147 -> "VES";
            case 148 -> "VND";
            case 149 -> "VUV";
            case 150 -> "WST";
            case 151 -> "XAF";
            case 152 -> "XCD";
            case 153 -> "XOF";
            case 154 -> "XPF";
            case 155 -> "YER";
            case 156 -> "ZAR";
            case 157 -> "ZMW";
            case 158 -> "ZWL";
            default -> "UNKNOWN";
        };
    }
}