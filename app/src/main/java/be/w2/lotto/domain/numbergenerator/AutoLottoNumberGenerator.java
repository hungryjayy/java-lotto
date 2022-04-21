package be.w2.lotto.domain.numbergenerator;

import be.w2.lotto.domain.lottonumber.LottoNumber;
import be.w2.lotto.domain.lottoticket.AutoLottoTicket;
import be.w2.lotto.domain.lottoticket.LottoTicket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static be.w2.lotto.domain.lottonumber.LottoNumber.LOTTO_NUMBER_LOWERBOUND;
import static be.w2.lotto.domain.lottonumber.LottoNumber.LOTTO_NUMBER_UPPERBOUND;
import static be.w2.lotto.domain.lottoticket.LottoTicket.LOTTO_TICKET_SIZE;

public class AutoLottoNumberGenerator implements LottoNumberGeneratable {
    private static final List<Integer> entireLottoNumbers = IntStream
            .rangeClosed(LOTTO_NUMBER_LOWERBOUND, LOTTO_NUMBER_UPPERBOUND)
            .boxed()
            .collect(Collectors.toList());

    public AutoLottoNumberGenerator() {}

    @Override
    public List<LottoTicket> generateLottoTickets(int numberCount, List<List<Integer>> autoLottoTickets) {
        List<LottoTicket> lottoTickets = new ArrayList<>();
        for(int i = 0; i < numberCount; i++) {
            lottoTickets.add(generateLottoTicket());
        }
        return lottoTickets;
    }

    private LottoTicket generateLottoTicket() {
        Collections.shuffle(entireLottoNumbers);
        return AutoLottoTicket.from(
                entireLottoNumbers.stream()
                        .limit(LOTTO_TICKET_SIZE)
                        .sorted()
                        .map(LottoNumber::from)
                        .collect(Collectors.toList())
        );
    }
}
