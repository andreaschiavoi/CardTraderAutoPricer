package com.marugami.ct_autopricer.enums;

public enum PriceRange {
    BELOW_34(0, 34),
    FROM_36_TO_310(36, 310),
    FROM_311_TO_511(311, 511),
    FROM_512_TO_714(512, 714),
    FROM_715_TO_1015(715, 1015),
    FROM_1016_TO_1521(1016, 1521),
    FROM_1522_TO_2027(1522, 2027),
    FROM_2028_TO_3040(2028, 3040),
    FROM_3050_TO_4052(3050, 4052),
    ABOVE_4052(4053, Integer.MAX_VALUE);

    private final int min;
    private final int max;

    PriceRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean includes(int priceCent) {
        return priceCent >= min && priceCent <= max;
    }

    public static PriceRange fromPrice(int priceCent) {
        for (PriceRange range : values()) {
            if (range.includes(priceCent)) {
                return range;
            }
        }
        throw new IllegalArgumentException("Unexpected price: " + priceCent);
    }
}
