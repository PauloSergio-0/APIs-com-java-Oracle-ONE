package br.com.trabalhandoapis.Models;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;

public class ErroConvercaoAno extends RuntimeException {
    private final String message;
    
    public ErroConvercaoAno(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
