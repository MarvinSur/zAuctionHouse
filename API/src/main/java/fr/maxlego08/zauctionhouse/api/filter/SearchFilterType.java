package fr.maxlego08.zauctionhouse.api.filter;

/**
 * Defines the matching operators available for search queries.
 * <p>
 * Operators support optional surrounding spaces, e.g. {@code "seller = boss"} or {@code "seller=boss"}.
 */
public enum SearchFilterType {

    CONTAINS("~"),
    EQUALS("="),
    CONTAINS_IGNORE_CASE("~="),
    EQUALS_IGNORE_CASE("==");

    private final String operator;

    SearchFilterType(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    /**
     * Returns the filter types ordered so that longer operators are checked first.
     * This prevents {@code "=="} from being matched as {@code "="} + {@code "="}.
     */
    public static SearchFilterType[] orderedByLength() {
        return new SearchFilterType[]{CONTAINS_IGNORE_CASE, EQUALS_IGNORE_CASE, CONTAINS, EQUALS};
    }
}
