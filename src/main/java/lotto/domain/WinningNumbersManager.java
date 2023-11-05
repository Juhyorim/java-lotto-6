package lotto.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinningNumbersManager {
    private static final String WINNING_NUMBERS_SIZE_6_REQUIRED = "당첨 번호는 6개여야합니다";
    private static final String WINNING_NUMBERS_RANGE_ERROR = "당첨 번호는 1~45 사이의 숫자여야합니다";
    private static final String BONUS_NUMBER_NOT_OVERLAP_WITH_WINNING_NUMBERS = "보너스 번호는 당첨 번호와 중복되면 안됩니다";
    private static final String BONUS_NUMBERS_RANGE_ERROR = "보너스 번호는 1~45 사이의 숫자여야합니다";

    private List<Integer> winningNumbers;
    private int bonusNumber;

    void inputWinningNumbers(List<Integer> winningNumbers) {
        this.winningNumbers = winningNumbers;
    }

    public void validateBonusNumber(Integer inputBonusNumber) {
        validateNumberRange(inputBonusNumber);

        for (Integer number : winningNumbers) {
            if (number.equals(inputBonusNumber)) {
                throw new IllegalArgumentException(BONUS_NUMBER_NOT_OVERLAP_WITH_WINNING_NUMBERS);
            }
        }
    }

    private void validateNumberRange(int number) {
        if (number < 1 || number > 45) {
            throw new IllegalArgumentException(BONUS_NUMBERS_RANGE_ERROR);
        }
    }

    public void inputBonusNumber(Integer bonusNumber) {
        this.bonusNumber = bonusNumber;
    }

    public Map<WinningStatus, Integer> getWinningStatus(List<Lotto> lottos) {
        Map<WinningStatus, Integer> winningStatus = getInitStatus();

        for (Lotto lotto : lottos) {
            WinningStatus status = getWinningStatus(lotto);
            if (status != null) {
                winningStatus.put(status, winningStatus.get(status) + 1);
            }
        }

        return winningStatus;
    }

    private WinningStatus getWinningStatus(Lotto lotto) {
        int matchCount = lotto.getMatchCount(winningNumbers);
        boolean isBonusMatch = false;

        if (matchCount == 5 && lotto.isMatch(bonusNumber)) {
            isBonusMatch = true;
        }

        return WinningStatus.valueOfMatchCount(matchCount, isBonusMatch);
    }

    private Map<WinningStatus, Integer> getInitStatus() {
        Map<WinningStatus, Integer> winningStatus = new HashMap<>();
        for (WinningStatus status : WinningStatus.values()) {
            winningStatus.put(status, 0);
        }

        return winningStatus;
    }

    public void validateWinningNumbers(List<Integer> winningNumbers) {
        if (winningNumbers.size() != 6) {
            throw new IllegalArgumentException(WINNING_NUMBERS_SIZE_6_REQUIRED);
        }

        validateNumberRange(winningNumbers);

        if (hasDuplicateNumber(winningNumbers)) {
            throw new IllegalArgumentException(WINNING_NUMBERS_RANGE_ERROR);
        }
    }

    private void validateNumberRange(List<Integer> winningNumbers) {
        for (Integer number : winningNumbers) {
            validateNumberRange(number);
        }
    }

    private boolean hasDuplicateNumber(List<Integer> winningNumbers) {
        long removeDuplicatedSize = winningNumbers.stream().distinct().count();
        if (winningNumbers.size() != removeDuplicatedSize) {
            return true;
        }

        return false;
    }
}
