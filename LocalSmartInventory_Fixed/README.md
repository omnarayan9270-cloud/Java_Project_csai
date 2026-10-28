# LocalSmartInventory
Java 17 + Spring Boot + Maven + Spring Data JPA + H2 local database. Responsive blush-pink dashboard.

## Run
1. Install JDK 17 and Maven.
2. Open this folder in VS Code.
3. In terminal run `mvn clean spring-boot:run`.
4. Visit http://localhost:8080.

## Included
Product inventory CRUD/search, persistent H2 database, low stock, sales recording and automatic stock deduction, dashboard metrics, dead stock detection after 45 days, seasonal rule-based suggestions by shop niche, recorded-sales trending for 30 days, supplier directory, CSV export.

H2 console: http://localhost:8080/h2-console
JDBC URL: `jdbc:h2:file:./data/inventorydb`
User: `sa` ; Password: blank.

Demo suppliers are placeholders, not verified businesses. Seasonal recommendations are rule-based ideas, not live local market data. Trending products are based only on sales recorded in this shop.
