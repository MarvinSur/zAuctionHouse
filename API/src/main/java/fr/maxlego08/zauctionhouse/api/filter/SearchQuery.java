package fr.maxlego08.zauctionhouse.api.filter;

/**
 * Represents a parsed search query with an optional field and filter type.
 * <p>
 * Spaces around the operator are allowed and trimmed automatically.
 * <p>
 * Examples:
 * <ul>
 *   <li>{@code "diamond"} → field=null, type=null, value="diamond" (default search on all fields)</li>
 *   <li>{@code "name ~ diamond"} → field=NAME, type=CONTAINS, value="diamond"</li>
 *   <li>{@code "seller = Notch"} → field=SELLER, type=EQUALS, value="Notch"</li>
 *   <li>{@code "seller == notch"} → field=SELLER, type=EQUALS_IGNORE_CASE, value="notch"</li>
 *   <li>{@code "material ~= sword"} → field=MATERIAL, type=CONTAINS_IGNORE_CASE, value="sword"</li>
 * </ul>
 *
 * @param field the target field, or null for default (all fields) search
 * @param type  the filter type, or null for default (CONTAINS) matching
 * @param value the search value
 */
public record SearchQuery(SearchField field, SearchFilterType type, String value) {

    /**
     * Parses a raw query string into a {@link SearchQuery}.
     * <p>
     * The format is: {@code field <operator> value} (spaces around operator are optional).
     * If no operator is found, the entire string is treated as a default search value.
     *
     * @param raw the raw query string
     * @return the parsed search query
     */
    public static SearchQuery parse(String raw) {
        if (raw == null || raw.isEmpty()) {
            return new SearchQuery(null, null, "");
        }

        // Try each operator, longest first to avoid "==" being matched as "="
        for (SearchFilterType filterType : SearchFilterType.orderedByLength()) {
            String operator = filterType.getOperator();
            int index = raw.indexOf(operator);
            if (index > 0) {
                String fieldKey = raw.substring(0, index).trim();
                SearchField field = SearchField.fromKey(fieldKey);
                if (field != null) {
                    String value = raw.substring(index + operator.length()).trim();
                    return new SearchQuery(field, filterType, value);
                }
            }
        }

        // No operator found — default search
        return new SearchQuery(null, null, raw);
    }

    /**
     * @return true if this is a default search (no specific field targeted)
     */
    public boolean isDefault() {
        return field == null;
    }
}
