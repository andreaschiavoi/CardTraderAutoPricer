package com.marugami.ct_autopricer.webclient;

import com.marugami.ct_autopricer.constants.ApiEndpoints;
import com.marugami.ct_autopricer.daos.CatalogItem;
import com.marugami.ct_autopricer.daos.ProductResponseWrapper;
import com.marugami.ct_autopricer.processor.ProductProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CatalogItemFetcher {

    private final WebClient webClient;
    private final ProductProcessor processor;

    @Value("${api.token}")
    private String headerBearer;

    public CatalogItemFetcher(ProductProcessor processor) {
        this.processor = processor;
        this.webClient = WebClient.builder()
                .baseUrl(ApiEndpoints.BASE_URL)
                .build();
    }

    public List<CatalogItem> fetchMyCatalogItems() {
        try {
            return webClient.get()
                    .uri(ApiEndpoints.PRODUCTS_EXPORT)
                    .header("Authorization", "Bearer " + headerBearer)
                    .retrieve()
                    .bodyToFlux(CatalogItem.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("HTTP Error: " + e.getRawStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.err.println("Generic Error: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public Optional<ProductResponseWrapper.Product> fetchCheapestMarketplaceProductByBlueprintId(long blueprintId, boolean isFoil) {
        try {
            System.out.println("blueprint id for this item is: " + blueprintId);
            ProductResponseWrapper response = webClient.get()
                    .uri(uriBuilder -> {
                        var builder = uriBuilder
                                .path("/api/v2/marketplace/products")
                                .queryParam("blueprint_id", blueprintId);

                        if (isFoil) {
                            builder.queryParam("foil", true);
                        }

                        return builder.build();
                    })
                    .header("Authorization", "Bearer " + headerBearer)
                    .retrieve()
                    .bodyToMono(ProductResponseWrapper.class)
                    .block();

            List<ProductResponseWrapper.Product> filteredSorted = processor.filterAndSortByHubAndPrice(response);

            Optional<ProductResponseWrapper.Product> first = filteredSorted.stream().findFirst();
            System.out.println("cheapest product is: " + first.get().getPrice().getCents());
            return first;

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void updateProductPriceAndQuantity(long productId, float price, int quantity) {
        try {
            var requestBody = Map.of(
                    "price", price,
                    "quantity", quantity
            );

            webClient.put()
                    .uri("/api/v2/products/{id}", productId)
                    .header("Authorization", "Bearer " + headerBearer)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            System.out.println("Product updated successfully: id=" + productId);
        } catch (WebClientResponseException e) {
            System.err.println("HTTP Error: " + e.getRawStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
