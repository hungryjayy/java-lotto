package be.w2.lotto.domain.winningresult;

import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class ProfitRate {
    private final BigInteger profitRate;

    private ProfitRate(BigInteger profitRate) {
        this.profitRate = profitRate;
    }

    public static ProfitRate valueOf(List<WinningResult> winningResults, int purchaseAmount) {
        calculateProfitRate(winningResults, purchaseAmount);
        BigInteger profitRate = calculateProfitRate(winningResults, purchaseAmount);
        return new ProfitRate(profitRate);
    }

    public BigInteger getProfitRate() {
        return profitRate;
    }

    private static BigInteger calculateProfitRate(List<WinningResult> winningResults, int purchaseAmount) {
        BigInteger profitSum = winningResults.stream()
                .map(ProfitRate::calculateProfit)
                .reduce(BigInteger.ZERO, BigInteger::add);

        return profitSum.multiply(MULTIPLY_BY_PERCENTAGE).divide(BigInteger.valueOf(purchaseAmount));
    }

    private static BigInteger calculateProfit(WinningResult winningResult) {
        BigInteger reward = BigInteger.valueOf(winningResult.getReward());
        BigInteger count = BigInteger.valueOf(winningResult.getCount());
        return reward.multiply(count);
    }

    private static final BigInteger MULTIPLY_BY_PERCENTAGE = BigInteger.valueOf(100);
}
