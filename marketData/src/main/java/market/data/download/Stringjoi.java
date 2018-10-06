package market.data.download;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Stringjoi {

	public static void main(String[] args) {

//GOOGL,YHOO,MS,FB
		
		System.out.println(String.join(",", Arrays.asList("GOOGL","YHOO","FB","MS")));
	}

}
