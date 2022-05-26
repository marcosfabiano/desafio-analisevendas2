package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Sale> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			System.out.println();
			System.out.println("Total de vendas  por vendedor:");

			Comparator<String> comp = (s1, s2) -> s1.compareTo(s2);

			List<String> seller1 = list.stream()
					.map(p -> p.getSeller()).distinct()
					.sorted(comp)
					.collect(Collectors.toList());

			for (String seller : seller1) {
				double total = list.stream()
					    .filter(s -> s.getSeller().equals(seller))
						.map(s -> s.getTotal())
						.reduce(0.0, (x, y) -> x + y);
				System.out.printf(seller + " - R$ " + String.format("%.2f\n",total));
			}
	
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();
	}
}
