# 4.0.0.5 (unreleased)

# 4.0.0.4 

- **Added** ZelAuction migration - migrate data from ZelAuction plugin to zAuctionHouse V4 using `/ah admin migrate zelauction confirm`
- **Added** Search system - players can search items by name, material, lore, or seller directly from the auction house GUI or via `/ah search <query>`
- **Added** `ZAUCTIONHOUSE_SEARCH` button - opens a chat-based search input with support for advanced filter operators
- **Added** `ZAUCTIONHOUSE_CLEAR_SEARCH` button - clears the active search filter, only visible when a search is active
- **Added** `/ah search <query>` command - search items directly from chat without opening the GUI first
- **Added** Advanced search filters with operators: `~` (contains), `=` (exact), `~=` (contains, ignore case), `==` (exact, ignore case)
- **Added** Searchable fields: `name`, `material`, `lore`, `seller` (e.g., `seller = Notch`, `name ~ Diamond`)
- **Added** `/ah admin forceopen <player> <inventory> [page]` - Open any inventory for a player at a specific page. Supports all inventory names (e.g., `auction`, `admin-selling-items`, `history`, `admin-logs`, etc.) with tab completion. Page defaults to 1
- **Added** `reset-search-on-open` config option - When enabled (default: `true`), the search filter is cleared every time a player opens the auction house, matching the existing `reset-category-on-open` behavior

### Search system

Players can search for items in the auction house using the search button or the `/ah search` command. The default search checks item name, material, lore, and seller name (case-insensitive substring).

**Advanced filters:**

```
field operator value
```

| Operator | Description |
|----------|-------------|
| `~` | Contains (case-sensitive) |
| `=` | Exact match (case-sensitive) |
| `~=` | Contains (ignore case) |
| `==` | Exact match (ignore case) |

| Field | Description |
|-------|-------------|
| `name` | Item display name |
| `material` | Item material type |
| `lore` | Item lore text |
| `seller` | Seller player name |

**Examples:**
```
seller = Notch
name ~ Diamond
material ~= sword
lore ~ Sharpness
```

# 4.0.0.3

- **Added** `ZAUCTIONHOUSE_SELL_LIMIT` button - displays remaining sell slots visually using a list of inventory slots, configurable per item type (auction, bid, rent)
- **Added** `%zauctionhouse_max_items_<type>%` placeholder - returns the maximum number of items a player can list for a specific type (auction, bid, rent)
- **Optimized** item lore placeholder resolution, placeholders are now pre-detected at config load and only resolved when referenced, significantly reducing CPU and memory usage per item render
- **Fixed** `/ah history` stuck on "Loading..." when `action.purchased-item.give-item: true` is enabled
- **Fixed** default config values
- **Fixed** default table prefix to `zauctionhousev4` (fixes compatibility with zAuctionHouse V3)

### `ZAUCTIONHOUSE_SELL_LIMIT` button

```yaml
sell-limit:
  type: ZAUCTIONHOUSE_SELL_LIMIT
  types:
    - auction
  slots:
    - 0-9
  item:
    material: LIME_STAINED_GLASS_PANE
    name: '&aAvailable slot'
```

### `%zauctionhouse_max_items_<type>%` placeholder

Returns the maximum number of items a player can list based on their permissions for the given type.

- `%zauctionhouse_max_items_auction%`
- `%zauctionhouse_max_items_bid%`
- `%zauctionhouse_max_items_rent%`

# 4.0.0.2

- **Added** `zauctionhouse_category` permissible for zMenu - allows conditional button visibility based on the player's currently selected category (defaults to `main`)
- **Added** `ZAUCTIONHOUSE_CATEGORY_SWITCHER` button - combines category cycling (left/right click) with dynamic lore showing enable/disable state per category
- **Added** `%zauctionhouse_category_id%` placeholder - returns the player's currently selected category ID
- **Added** `ZAUCTIONHOUSE_HISTORY_INVENTORY` button - opens the sales history inventory directly from any UI
- **Added** `force-amount-one` option in `config.yml` (`item-lore` section) - forces displayed item amount to 1 in the auction inventory while preserving the real amount internally
- **Changed** `AuctionEconomy` API - economy methods (`get`, `has`, `deposit`, `withdraw`) now accept `UUID` instead of `OfflinePlayer` for better compatibility
- **Updated** CurrenciesAPI dependency from 1.0.12 to 1.0.13
- **Fixed** category display name fallback - now uses the configured "all" category name instead of a hardcoded `"All"` string
- **Fixed** empty slot crash - list buttons (`ListedItems`, `ExpiredItems`, `PurchasedItems`, `SellingItems`) no longer crash when `emptySlot` is `-1` and the list is empty
- **Fixed** history loading slot - `HistoryItemsButton` now properly handles `loadingSlot` set to `-1` without throwing an error
- **Fixed** `LoadingSlotLoader` - added validation for invalid slot values with a clear error message

### `zauctionhouse_category` permissible

```yaml
requirements:
  - type: zauctionhouse_category
    category: "weapons"
```

### `ZAUCTIONHOUSE_CATEGORY_SWITCHER` button

```yaml
category-switcher:
  type: ZAUCTIONHOUSE_CATEGORY_SWITCHER
  slot: 49
  enable-text: "&a● %category%"
  disable-text: "&7○ %category%"
  categories:
    - "main"
    - "weapons"
    - "armor"
    - "tools"
    - "blocks"
    - "consumables"
    - "resources"
    - "enchanted-books"
    - "misc"
  item:
    material: COMPASS
    name: "&6Categories &7(&f%category%&7)"
    lore:
      - ""
      - "%main%"
      - "%weapons%"
      - "%armor%"
      - "%tools%"
      - "%blocks%"
      - "%consumables%"
      - "%resources%"
      - "%enchanted-books%"
      - "%misc%"
      - ""
      - "&7Left-click &8» &fNext"
      - "&7Right-click &8» &fPrevious"
```

### `force-amount-one` option

```yaml
item-lore:
  # Forces the displayed item amount to 1 in the auction inventory.
  # The real amount is preserved internally and given to the buyer on purchase.
  # Useful to keep a clean, uniform display.
  force-amount-one: false
```

# 4.0.0.1

- **Added** Thai as a supported language
- **Fixed** support for Minecraft **1.20.4**
- **Fixed** the `/zauctionhouse` command - it is no longer the default main command (this can be changed in `config.yml`)
- **Fixed** message system errors that could appear without reason
- **Added** the `reset-category-on-open` option, allowing categories to reset when reopening the inventory
- **Added** `EXCELLENTEECONOMY` economy support