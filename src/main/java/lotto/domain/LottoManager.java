package lotto.domain;

import java.util.List;
import java.util.Map;
import lotto.ui.ConsoleInput;
import lotto.ui.ConsoleOutput;
import lotto.ui.Input;
import lotto.ui.Output;

public class LottoManager {
    private Input input;
    private Output output;
    private LottoGenerator lottoGenerator;
    private WinningNumbersManager winningNumbersManager;

    public LottoManager() {
        this(new ConsoleInput(), new ConsoleOutput(), new LottoGenerator(), new WinningNumbersManager());
    }

    public LottoManager(Input input, Output output, LottoGenerator lottoGenerator,
                        WinningNumbersManager winningNumbersManager) {
        this.input = input;
        this.output = output;
        this.lottoGenerator = lottoGenerator;
        this.winningNumbersManager = winningNumbersManager;
    }

    public List<Lotto> buyLotto() {
        int price = getPrice();
        int numberOfLotto = getNumberOfLotto(price);
        List<Lotto> lottos = lottoGenerator.generateLottos(numberOfLotto);
        output.printPurchasedLotto(lottos);

        return lottos;
    }

    private int getNumberOfLotto(int price) {
        return price / 1000;
    }

    private int getPrice() {
        output.printLottoPriceRequest();
        return input.getPrice();
    }

    public void inputWinningNumbers() {
        List<Integer> winningNumbers = getWinningNumbers();
        winningNumbersManager.inputWinningNumbers(winningNumbers);

        Integer bonusNumber = getBonusNumber();
        winningNumbersManager.inputBonusNumber(bonusNumber);
    }

    private Integer getBonusNumber() {
        output.printBonusNumberRequest();
        Integer bonusNumber = null;

        while (bonusNumber == null) {
            try {
                Integer invalidBonusNumber = input.getBonusNumbers();
                winningNumbersManager.validateBonusNumber(invalidBonusNumber);
                bonusNumber = invalidBonusNumber;
            } catch (IllegalArgumentException e) {
                output.printError(e.getMessage());
            }
        }

        return 0;
    }

    private List<Integer> getWinningNumbers() {
        output.printWinningNumbersRequest();
        List<Integer> winningNumbers = null;

        while (winningNumbers == null) {
            try {
                winningNumbers = input.getWinningNumbers();
            } catch (IllegalArgumentException e) {
                output.printError(e.getMessage());
            }
        }

        return winningNumbers;
    }

    public void getWinningStatus(List<Lotto> lottos) {
        Map<WinningStatus, Integer> winningStatus = winningNumbersManager.getWinningStatus(lottos);
        double rateOfReturn = ReturnCalculator.calculate(winningStatus, lottos.size());

        output.printWinningStatus(winningStatus);
        output.printRateOfReturn(rateOfReturn);
    }
}
