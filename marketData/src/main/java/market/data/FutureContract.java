package market.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class FutureContract {
    private String tickerSymbol;
    private String exchange;
    private String exchangeAcronym;
    private String contractMonths;
    private String qualcCode;


}
