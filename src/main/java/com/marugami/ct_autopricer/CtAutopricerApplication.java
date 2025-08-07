package com.marugami.ct_autopricer;

import com.marugami.ct_autopricer.daos.CatalogItem;
import com.marugami.ct_autopricer.operations.ItemOperations;
import com.marugami.ct_autopricer.webclient.CatalogItemFetcher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CtAutopricerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CtAutopricerApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(CatalogItemFetcher fetcher, ItemOperations itemOperations) {
		return args -> {
			List<CatalogItem> items = fetcher.fetchMyCatalogItems();
			System.out.println("Catalog items fetched: " + items.size());
			System.out.println("my product price is: " + items.get(0).getPriceCents());
			items.forEach(System.out::println);
			items.forEach(itemOperations::updatePriceToCheapest);
			System.exit(0);
		};
    }

}
