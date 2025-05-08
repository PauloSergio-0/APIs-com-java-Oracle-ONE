package br.com.trabalhandoapis;

import br.com.trabalhandoapis.Models.ErroConvercaoAno;
import br.com.trabalhandoapis.Models.Titulo;
import br.com.trabalhandoapis.Models.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();

            System.out.println("Nome do filme: ");
            var nomeFilme = "&t=" + scanner.nextLine().trim();
            String endereco = "http://www.omdbapi.com/?apikey=308478da" + nomeFilme;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

    //        Titulo meuTitulo = gson.fromJson(json, Titulo.class);
            TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            Titulo meuTitulo = new Titulo(meuTituloOmdb);
            System.out.println(meuTitulo);
        } catch (NumberFormatException e ) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Verifique o endereço da url");

        } catch (ErroConvercaoAno e) {
            System.out.println(e.getMessage());
        }


    }
}
