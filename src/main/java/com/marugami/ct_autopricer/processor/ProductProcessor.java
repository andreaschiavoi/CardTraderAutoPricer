package com.marugami.ct_autopricer.processor;

import com.marugami.ct_autopricer.daos.ProductResponseWrapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProcessor {

    public List<ProductResponseWrapper.Product> filterAndSortByHubAndPrice(ProductResponseWrapper response, String language) {
        if (response == null || response.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProductResponseWrapper.Product> allProducts = response.values().stream()
                .findFirst()
                .orElse(Collections.emptyList());

        return allProducts.stream()
                .filter(p -> p.getUser() != null && p.getUser().isCan_sell_via_hub())
                .filter(p -> language == null || language.equalsIgnoreCase(p.getProperties_hash().getMtg_language())) // filtro lingua
                .sorted(Comparator.comparingInt(p -> p.getPrice().getCents()))
                .collect(Collectors.toList());
    }
}
