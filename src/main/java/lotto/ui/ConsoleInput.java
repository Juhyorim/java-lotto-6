package lotto.ui;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import lotto.util.InputValidator;
import lotto.util.SplitNumber;

public class ConsoleInput implements Input {
    private InputValidator inputValidator;

    public ConsoleInput() {
        inputValidator = new InputValidator();
    }

    public int getPrice() {
        String inputPrice = Console.readLine();
        inputValidator.validatePrice(inputPrice);

        return parseNumber(inputPrice);
    }

    @Override
    public List<Integer> getWinningNumbers() {
        String inputWinningNumbers = Console.readLine();
        List<Integer> winningNumbers = SplitNumber.splitNumber(inputWinningNumbers);

        inputValidator.validateWinningNumbers(winningNumbers);

        return winningNumbers;
    }

    private static int parseNumber(String inputPrice) {
        return Integer.parseInt(inputPrice);
    }
}
