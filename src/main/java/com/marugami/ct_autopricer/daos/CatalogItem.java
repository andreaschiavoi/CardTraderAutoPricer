package com.marugami.ct_autopricer.daos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatalogItem {

    private Long id;

    @JsonProperty("blueprint_id")
    private Long blueprintId;

    @JsonProperty("name_en")
    private String nameEn;

    private String description;

    private Integer quantity;

    @JsonProperty("price_cents")
    private Integer priceCents;

    @JsonProperty("price_currency")
    private String priceCurrency;

    @JsonProperty("bundle_size")
    private Integer bundleSize;

    @JsonProperty("user_data_field")
    private String userDataField;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("game_id")
    private Integer gameId;

    private String tag;

    private boolean graded;

    private List<Object> uploadedImages;

    private Expansion expansion;

    @JsonProperty("properties_hash")
    private PropertiesHash properties;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Expansion {
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PropertiesHash {
        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getMtgCardColors() {
            return mtgCardColors;
        }

        public void setMtgCardColors(String mtgCardColors) {
            this.mtgCardColors = mtgCardColors;
        }

        public String getCollectorNumber() {
            return collectorNumber;
        }

        public void setCollectorNumber(String collectorNumber) {
            this.collectorNumber = collectorNumber;
        }

        public boolean isTournamentLegal() {
            return tournamentLegal;
        }

        public void setTournamentLegal(boolean tournamentLegal) {
            this.tournamentLegal = tournamentLegal;
        }

        public String getCmc() {
            return cmc;
        }

        public void setCmc(String cmc) {
            this.cmc = cmc;
        }

        public boolean isSigned() {
            return signed;
        }

        public void setSigned(boolean signed) {
            this.signed = signed;
        }

        public boolean isMtgFoil() {
            return mtgFoil;
        }

        public void setMtgFoil(boolean mtgFoil) {
            this.mtgFoil = mtgFoil;
        }

        public String getMtgRarity() {
            return mtgRarity;
        }

        public void setMtgRarity(String mtgRarity) {
            this.mtgRarity = mtgRarity;
        }

        public String getMtgLanguage() {
            return mtgLanguage;
        }

        public void setMtgLanguage(String mtgLanguage) {
            this.mtgLanguage = mtgLanguage;
        }

        public boolean isAltered() {
            return altered;
        }

        public void setAltered(boolean altered) {
            this.altered = altered;
        }

        private String condition;

        @JsonProperty("mtg_card_colors")
        private String mtgCardColors;

        @JsonProperty("collector_number")
        private String collectorNumber;

        @JsonProperty("tournament_legal")
        private boolean tournamentLegal;

        private String cmc;

        private boolean signed;

        @JsonProperty("mtg_foil")
        private boolean mtgFoil;

        @JsonProperty("mtg_rarity")
        private String mtgRarity;

        @JsonProperty("mtg_language")
        private String mtgLanguage;

        private boolean altered;
    }
}
