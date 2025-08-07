package com.marugami.ct_autopricer.daos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductResponseWrapper extends java.util.HashMap<String, List<ProductResponseWrapper.Product>> {

    @Data
    public static class Product {
        private long id;
        private long blueprint_id;
        private String name_en;
        private Expansion expansion;
        private int price_cents;
        private String price_currency;
        private int quantity;
        private String description;
        private PropertiesHash properties_hash;
        private boolean on_vacation;
        private User user;
        private Price price;
    }

    @Data
    public static class Expansion {
        private String code;
        private long id;
        private String name_en;
    }

    @Data
    public static class PropertiesHash {
        private String condition;
        private String mtg_card_colors;
        private String collector_number;
        private boolean tournament_legal;
        private String cmc;
        private boolean signed;
        private boolean mtg_foil;
        private String mtg_rarity;
        private String mtg_language;
        private boolean altered;
    }

    @Data
    public static class User {
        private String country_code;
        private boolean too_many_request_for_cancel_as_seller;
        private String user_type;
        private boolean can_sell_sealed_with_ct_zero;
        private Long max_sellable_in24h_quantity;
        private long id;
        private String username;
        private boolean can_sell_via_hub;
    }

    @Data
    public static class Price {
        private int cents;
        private String currency;
        private String currency_symbol;
        private String formatted;
    }
}
