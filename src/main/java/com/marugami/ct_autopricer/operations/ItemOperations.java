package com.marugami.ct_autopricer.operations;

import com.marugami.ct_autopricer.daos.CatalogItem;
import com.marugami.ct_autopricer.daos.ProductResponseWrapper;
import com.marugami.ct_autopricer.enums.PriceRange;
import com.marugami.ct_autopricer.webclient.CatalogItemFetcher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Component
public class ItemOperations {

    private final CatalogItemFetcher fetcher;

    public ItemOperations(CatalogItemFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public void updatePriceToCheapest(CatalogItem catalogItem) {
        Long blueprintId = catalogItem.getBlueprintId();
        boolean isFoil = catalogItem.getProperties().isMtgFoil();
        String language = catalogItem.getProperties().getMtgLanguage();
        Optional<ProductResponseWrapper.Product> cheapestProduct =
                fetcher.fetchCheapestMarketplaceProductByBlueprintId(blueprintId, isFoil, language);
        cheapestProduct.ifPresent(cheapest -> {
            int cheapestPriceCents = cheapest.getPrice_cents();
            int cheapestPriceCentWithoutTax = fixCheapestRemovingSellingTax(cheapestPriceCents);
            if (cheapestPriceCentWithoutTax != catalogItem.getPriceCents()) {
                Long myItemId = catalogItem.getId();
                Integer myQuantity = catalogItem.getQuantity();
                fetcher.updateProductPriceAndQuantity(myItemId, floatTransformer(cheapestPriceCentWithoutTax),
                        myQuantity);
            }
        });
    }

    private float floatTransformer(int cents) {
        return BigDecimal.valueOf(cents)
                .divide(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP)
                .floatValue();
    }

    private int fixCheapestRemovingSellingTax(int priceCent) {
        PriceRange range = PriceRange.fromPrice(priceCent);
        return switch (range) {
            case BELOW_34 -> priceCent - 9;
            case FROM_36_TO_310 -> priceCent - 10;
            case FROM_311_TO_511 -> priceCent - 11;
            case FROM_512_TO_714 -> priceCent - 14;
            case FROM_715_TO_1015 -> priceCent - 15;
            case FROM_1016_TO_1521 -> priceCent - 21;
            case FROM_1522_TO_2027 -> priceCent - 27;
            case FROM_2028_TO_3040 -> priceCent - 40;
            case FROM_3050_TO_4052 -> priceCent - 52;
            case ABOVE_4052 -> priceCent - 63;
        };
    }
}
