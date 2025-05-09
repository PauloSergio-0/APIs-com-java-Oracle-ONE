package br.com.trabalhandoapis;

import br.com.trabalhandoapis.Models.ErroConvercaoAno;
import br.com.trabalhandoapis.Models.Titulo;
import br.com.trabalhandoapis.Models.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    private static final ArrayList<Titulo> titulos = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        String busca = "";

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (true) {
            try {
                System.out.println("Nome do filme: ");
                busca = scanner.nextLine().trim();



                if(busca.equals("sair")){
                    break;
                }

                var nomeFilme = "&t=" + busca;

                String endereco = "http://www.omdbapi.com/?apikey=308478da" + nomeFilme;

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                System.out.println(json);



                //        Titulo meuTitulo = gson.fromJson(json, Titulo.class);
                TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                System.out.println(meuTitulo);


                titulos.add(meuTitulo);


            } catch(NumberFormatException e ){
                System.out.println(e.getMessage());
            } catch(IllegalArgumentException e){
                System.out.println("Verifique o endereço da url");
            } catch(ErroConvercaoAno e){
                System.out.println(e.getMessage());
            }

            FileWriter escrita = new FileWriter("Filmes.json");
            escrita.write(gson.toJson(titulos));
            escrita.close();
        }

        System.out.println("TEst");
        System.out.println(titulos);

    }
}
