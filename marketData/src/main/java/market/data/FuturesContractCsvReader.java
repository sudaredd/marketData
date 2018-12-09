package market.data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.nio.file.Files.list;

public class FuturesContractCsvReader {

    public List<FutureContract> futureContracts() throws IOException {
       return list(Paths.get("C:\\data\\contractData"))
                .filter(Files::isRegularFile)
                .filter(f -> !f.toFile().getName().equals("EUREX.csv"))
                .peek(f -> System.out.println("file name is:" + f))
                .flatMap(this::apply)
                .collect(Collectors.toList());
    }

    private FutureContract futureContract(String s) {
        String row[] = s.split(",");
        return new FutureContract(row[0], row[1], row[2], row[3], row[4]);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new FuturesContractCsvReader().futureContracts());
    }

    private Stream<? extends FutureContract> apply(Path path) {
        return getStreamLines(path).map(this::futureContract).collect(Collectors.toList()).stream();
    }

    private Stream<String> getStreamLines(Path path) {
        try {
            return Files.newBufferedReader(path, StandardCharsets.UTF_8).lines();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
