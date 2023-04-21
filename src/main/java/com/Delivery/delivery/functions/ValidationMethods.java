package com.Delivery.delivery.functions;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.Delivery.delivery.Exceptions.InvalidRegionException;
import com.Delivery.delivery.model.IpPerson;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ValidationMethods {
    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return (false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else
                return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static String imprimeCPF(String CPF) {
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }

    public static boolean validarTelefone(String telefone) {
        telefone = telefone.replaceAll("\\D", "");

        if (!(telefone.length() >= 10 && telefone.length() <= 11))
            return false;
        if (telefone.length() == 11 && Integer.parseInt(telefone.substring(2, 3)) != 9)
            return false;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(telefone.charAt(0) + "{" + telefone.length() + "}");
        java.util.regex.Matcher m = p.matcher(telefone);
        if (m.find())
            return false;
        Integer[] codigosDDD = {
                11, 12, 13, 14, 15, 16, 17, 18, 19,
                21, 22, 24, 27, 28, 31, 32, 33, 34,
                35, 37, 38, 41, 42, 43, 44, 45, 46,
                47, 48, 49, 51, 53, 54, 55, 61, 62,
                64, 63, 65, 66, 67, 68, 69, 71, 73,
                74, 75, 77, 79, 81, 82, 83, 84, 85,
                86, 87, 88, 89, 91, 92, 93, 94, 95,
                96, 97, 98, 99 };
        if (java.util.Arrays.asList(codigosDDD).indexOf(Integer.parseInt(telefone.substring(0, 2))) == -1)
            return false;
        Integer[] prefixos = { 2, 3, 4, 5, 7 };
        if (telefone.length() == 10
                && java.util.Arrays.asList(prefixos).indexOf(Integer.parseInt(telefone.substring(2, 3))) == -1)
            return false;
        return true;
    }

    public static CompletableFuture<Boolean> isValidCEPAsync(int CEP) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = "https://viacep.com.br/ws/" + CEP + "/json/";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                if (con.getResponseCode() != 200) {
                    return false;
                }
                ObjectMapper objectMapper = new ObjectMapper();
                InputStream inputStream = con.getInputStream();
                Map<String, Object> jsonMap = objectMapper.readValue(inputStream,
                        new TypeReference<Map<String, Object>>() {
                        });
                String uf = (String) jsonMap.get("uf");
                if (!uf.equals("DF")) {
                    return false;
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    private static CompletableFuture<Map<String, Object>> ipReceiver(String ip) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = "http://ip-api.com/json/" + ip;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                if (con.getResponseCode() != 200) {
                    return null;
                }
                ObjectMapper objectMapper = new ObjectMapper();
                InputStream inputStream = con.getInputStream();
                Map<String, Object> jsonMap = objectMapper.readValue(inputStream,
                        new TypeReference<Map<String, Object>>() {
                        });
                return jsonMap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public static CompletableFuture<IpPerson> getIpPerson(String ip) throws InvalidRegionException {
        return ipReceiver(ip).thenApplyAsync(ipData -> {
            if (ipData == null || !"success".equals(ipData.get("status"))) {
                return null;
            }

            String ipCode = (String) ipData.getOrDefault("query", "");
            String country = (String) ipData.getOrDefault("country", "");
            String region = (String) ipData.getOrDefault("region", "");
            if (!region.equals("DF")) {
                try {
                    throw new InvalidRegionException("Invalid region: " + region);
                } catch (InvalidRegionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            BigDecimal lat = null;
            BigDecimal lon = null;
            try {
                lat = new BigDecimal(String.valueOf(ipData.getOrDefault("lat", "")));
                lon = new BigDecimal(String.valueOf(ipData.getOrDefault("lon", "")));
            } catch (NumberFormatException e) {
                // Handle the case where lat and/or lon are not valid numbers
            }

            String city = (String) ipData.getOrDefault("city", "");
            String zip = (String) ipData.getOrDefault("zip", "");
            String timezone = (String) ipData.getOrDefault("timezone", "");
            String isp = (String) ipData.getOrDefault("isp", "");
            String org = (String) ipData.getOrDefault("org", "");
            String assinature = "Some value"; // Set a default value for the assinature field

            return new IpPerson(ipCode, country, region, city, zip, lat, lon, timezone, isp, org, assinature);
        });
    }

}
