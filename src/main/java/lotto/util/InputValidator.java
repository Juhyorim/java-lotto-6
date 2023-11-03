package lotto.util;

public class InputValidator {
    private static final String NOT_A_NUMBER_MESSAGE = "로또 금액은 숫자여야 합니다";
    private static final String MIN_PRICE_1000_MESSAGE = "로또 금액은 1000원 이상이어야 합니다";
    private static final String NUMBER_REGEX = "[0-9]+";

    public void validatePrice(String inputPrice) {
        if (isNumber(inputPrice) == false) {
            throw new IllegalArgumentException(NOT_A_NUMBER_MESSAGE);
        }

        int price = Integer.parseInt(inputPrice);

        if (isUnder1000(price)) {
            throw new IllegalArgumentException(MIN_PRICE_1000_MESSAGE);
        }
    }

    private boolean isUnder1000(int price) {
        if (price < 1000) {
            return true;
        }

        return false;
    }

    private boolean isNumber(String inputPrice) {
        if (inputPrice.matches(NUMBER_REGEX)) {
            return true;
        }

        return false;
    }
}
