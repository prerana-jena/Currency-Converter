import java.util.*;

// =========================
// Currency Class
// =========================
class Currency {

    private String code;
    private String country;
    private double exchangeRate;

    public Currency(String code,
                    String country,
                    double exchangeRate) {

        this.code = code;
        this.country = country;
        this.exchangeRate = exchangeRate;
    }

    public String getCode() {
        return code;
    }

    public String getCountry() {
        return country;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {

        return code + " - " + country;
    }
}

// =========================
// Interface
// =========================
interface Converter {

    double convert(String from,
                   String to,
                   double amount);
}

// =========================
// Custom Exception
// =========================
class InvalidCurrencyException
        extends Exception {

    public InvalidCurrencyException(
            String message) {

        super(message);
    }
}

// =========================
// Currency Converter Class
// =========================
class CurrencyConverter
        implements Converter {

    private Map<String, Currency> currencies;

    // Constructor
    public CurrencyConverter() {

        currencies = new HashMap<>();

        initializeCurrencies();
    }

    // Initialize currencies
    private void initializeCurrencies() {

        addCurrency(
                new Currency(
                        "USD",
                        "US Dollar",
                        1.0
                )
        );

        addCurrency(
                new Currency(
                        "INR",
                        "Indian Rupee",
                        83.0
                )
        );

        addCurrency(
                new Currency(
                        "EUR",
                        "Euro",
                        0.92
                )
        );

        addCurrency(
                new Currency(
                        "GBP",
                        "British Pound",
                        0.79
                )
        );

        addCurrency(
                new Currency(
                        "JPY",
                        "Japanese Yen",
                        155.0
                )
        );

        addCurrency(
                new Currency(
                        "AUD",
                        "Australian Dollar",
                        1.51
                )
        );
    }

    // Add Currency
    public void addCurrency(
            Currency currency) {

        currencies.put(
                currency.getCode(),
                currency
        );
    }

    // Display currencies
    public void displayCurrencies() {

        System.out.println(
                "\n========== AVAILABLE CURRENCIES =========="
        );

        for (Currency currency
                : currencies.values()) {

            System.out.println(currency);
        }
    }

    // Convert Currency
    @Override
    public double convert(
            String from,
            String to,
            double amount) {

        Currency fromCurrency =
                currencies.get(from);

        Currency toCurrency =
                currencies.get(to);

        // Convert source to USD
        double usdAmount =
                amount
                        / fromCurrency.getExchangeRate();

        // Convert USD to target
        return usdAmount
                * toCurrency.getExchangeRate();
    }

    // Validate Currency
    public void validateCurrency(
            String code)
            throws InvalidCurrencyException {

        if (!currencies.containsKey(code)) {

            throw new InvalidCurrencyException(
                    "Currency "
                            + code
                            + " does not exist!"
            );
        }
    }

    // Update exchange rate
    public void updateRate(
            String code,
            double newRate)
            throws InvalidCurrencyException {

        validateCurrency(code);

        currencies.get(code)
                .setExchangeRate(newRate);
    }
}

// =========================
// Main Class
// =========================
public class AdvancedCurrencyConverter {

    // Validate amount
    public static double getAmount(
            Scanner sc) {

        while (true) {

            try {

                System.out.print(
                        "\nEnter Amount: "
                );

                double amount =
                        sc.nextDouble();

                if (amount <= 0) {

                    throw new IllegalArgumentException(
                            "Amount must be positive!"
                    );
                }

                return amount;

            } catch (Exception e) {

                System.out.println(
                        "Invalid Amount! Try Again."
                );

                sc.nextLine();
            }
        }
    }

    // Main Method
    public static void main(String[] args) {

        Scanner sc =
                new Scanner(System.in);

        CurrencyConverter converter =
                new CurrencyConverter();

        boolean running = true;

        System.out.println(
                "======================================"
        );

        System.out.println(
                "     ADVANCED CURRENCY CONVERTER"
        );

        System.out.println(
                "======================================"
        );

        while (running) {

            System.out.println(
                    "\n1. Convert Currency"
            );

            System.out.println(
                    "2. View Supported Currencies"
            );

            System.out.println(
                    "3. Update Exchange Rate"
            );

            System.out.println(
                    "4. Exit"
            );

            System.out.print(
                    "\nChoose Option: "
            );

            int choice =
                    sc.nextInt();

            sc.nextLine();

            try {

                switch (choice) {

                    case 1:

                        converter.displayCurrencies();

                        System.out.print(
                                "\nEnter Source Currency: "
                        );

                        String from =
                                sc.nextLine()
                                        .toUpperCase();

                        converter.validateCurrency(from);

                        System.out.print(
                                "Enter Target Currency: "
                        );

                        String to =
                                sc.nextLine()
                                        .toUpperCase();

                        converter.validateCurrency(to);

                        double amount =
                                getAmount(sc);

                        double result =
                                converter.convert(
                                        from,
                                        to,
                                        amount
                                );

                        System.out.printf(
                                "\n%.2f %s = %.2f %s\n",
                                amount,
                                from,
                                result,
                                to
                        );

                        break;

                    case 2:

                        converter.displayCurrencies();

                        break;

                    case 3:

                        converter.displayCurrencies();

                        System.out.print(
                                "\nEnter Currency Code: "
                        );

                        String code =
                                sc.nextLine()
                                        .toUpperCase();

                        System.out.print(
                                "Enter New Exchange Rate: "
                        );

                        double newRate =
                                sc.nextDouble();

                        converter.updateRate(
                                code,
                                newRate
                        );

                        System.out.println(
                                "\nExchange Rate Updated Successfully!"
                        );

                        break;

                    case 4:

                        running = false;

                        System.out.println(
                                "\nThank You For Using Currency Converter!"
                        );

                        break;

                    default:

                        System.out.println(
                                "\nInvalid Choice!"
                        );
                }

            } catch (InvalidCurrencyException e) {

                System.out.println(
                        "\nError: "
                                + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "\nUnexpected Error!"
                );

                sc.nextLine();
            }
        }

        sc.close();
    }
}