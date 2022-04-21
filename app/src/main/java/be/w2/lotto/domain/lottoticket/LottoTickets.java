package be.w2.lotto.domain.lottoticket;

import be.w2.lotto.domain.numbergenerator.AutoLottoNumberGenerator;
import be.w2.lotto.domain.numbergenerator.LottoNumberGeneratable;
import be.w2.lotto.domain.numbergenerator.ManualLottoNumberGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class LottoTickets {
    private final List<LottoTicket> lottoTickets;

    private LottoTickets(List<LottoTicket> lottoTickets) {
        this.lottoTickets = lottoTickets;
    }

    public static LottoTickets valueOf(int lottoPurchaseAmount, List<List<Integer>> manualLottoNumbers) {
        int lottoAmount = LottoTicketAmount.createLottoAmount(lottoPurchaseAmount);
        List<LottoTicket> lottoTickets = createLottoTickets(lottoAmount, manualLottoNumbers);
        return new LottoTickets(lottoTickets);
    }

    private static List<LottoTicket> createLottoTickets(int lottoAmount, List<List<Integer>> manualLottoNumbers) {
        int autoNumberCount = lottoAmount - manualLottoNumbers.size();
        return lottoNumberGeneratables.stream()
                .map(generatable -> generateLottoTickets(autoNumberCount, manualLottoNumbers, generatable))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private static List<LottoTicket> generateLottoTickets(
            int autoNumberCount,
            List<List<Integer>> manualLottoNumbers,
            LottoNumberGeneratable generatable
    ) {
        return generatable instanceof AutoLottoNumberGenerator ?
                generatable.generateLottoTickets(autoNumberCount, null) :
                generatable.generateLottoTickets(manualLottoNumbers.size(), manualLottoNumbers);
    }

    public List<LottoTicket> getLottoTickets() {
        return this.lottoTickets;
    }

    private static final List<LottoNumberGeneratable> lottoNumberGeneratables = List.of(
            new AutoLottoNumberGenerator(),
            new ManualLottoNumberGenerator()
    );
}
